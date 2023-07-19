package mpdev.springboot.aoc2020.solutions.day14

import mpdev.springboot.aoc2020.utils.AocException
import java.lang.StringBuilder

class Decoder(input: List<String>, part1Or2: Int = 1) {

    var data: List<Packet>
    var mem: MutableMap<Long,Long> = mutableMapOf()

    init {
        data = processInput(input, part1Or2)
    }

    fun updateMemory() {
        if (data.first().ver == "V1")
            data.forEach { p ->
                p.values.forEach { (addr, value) ->
                    mem[addr] = value and p.andMask or p.orMask
                }
            }
        else
            data.forEach { p ->
                p.values.forEach { (addr, value) ->
                    getAddresses(addr, p.orMask, p.addressMask).forEach { modifiedAddr -> mem[modifiedAddr] = value }
                }
            }
    }

    fun getAddresses(address: Long, orMask: Long, addrMask: Long): List<Long> {
        var addressList = mutableListOf(address or orMask)
        var bit = 1L
        while (bit < 0x1000000000) {
            if (addrMask and bit != 0L) {
                val newAddrList = mutableListOf<Long>()
                addressList.forEach { addr -> newAddrList.add(addr); newAddrList.add(addr xor bit) }
                addressList = newAddrList
            }
            bit *= 2
        }
        return addressList.sorted()
    }

    private fun processInput(input: List<String>, part1Or2: Int): List<Packet> {
        val packetList = mutableListOf<Packet>()
        var packet = Packet()
        var firstLine = true
        /*
          mask = XXXXXXXXXXXXXXXXXXXXXXXXXXXXX1XXXX0X
          mem[8] = 11
          mem[7] = 101
          mem[8] = 0
         */
        input.forEach { line ->
            if (line.startsWith("mask")) {
                if (!firstLine)
                    packetList.add(packet)
                firstLine = false
                val (andMask, orMask, addrMask) =
                    maskFromString(line.substring(line.lastIndexOf(' ') + 1), part1Or2)
                packet = Packet(andMask, orMask, addrMask, ver = "V$part1Or2")
            }
            if (line.startsWith("mem")) {
                val match = Regex("""mem\[(\d+)] = (\d+)""").find(line)
                try {
                    val (address, contents) = match!!.destructured
                    packet.values.add(Pair(address.toLong(), contents.toLong()))
                } catch (e: Exception) {
                    throw AocException("bad input line $line")
                }
            }
        }
        packetList.add(packet)
        return packetList
    }

    private fun maskFromString(s: String, part1Or2: Int): Triple<Long,Long,Long> {
        var bit = 1L
        var andMask = 0xFFFFFFFFFL
        var orMask = 0L
        var addrMask = 0L
        s.toCharArray().reversed().forEach { c ->
            when {
                c == '1'                  -> orMask = orMask or bit
                c == '0' && part1Or2 == 1 -> andMask = andMask and bit.inv()
                c == 'X' && part1Or2 == 2 -> addrMask = addrMask or bit
            }
            bit *= 2
        }
        return Triple(andMask, orMask, addrMask)
    }
}

data class Packet(val andMask: Long = 0L, val orMask: Long = 0L, val addressMask: Long = 0L,
                  val ver: String = "V1", val values: MutableList<Pair<Long,Long>> = mutableListOf()) {
    override fun toString() = StringBuilder().also { s ->
        s.append("version: $ver\n")
        s.append("andMask: ${Integer.toBinaryString(andMask.toInt()).padStart(36, '0')}\n")
        s.append("orMask : ${Integer.toBinaryString(orMask.toInt()).padStart(36, '0')}\n")
        s.append("addrMsk: ${Integer.toBinaryString(addressMask.toInt()).padStart(36, '0')}\n")
        values.forEach { v -> s.append("mem[${v.first}]: ${v.second}\n") }
    }.toString()
}
