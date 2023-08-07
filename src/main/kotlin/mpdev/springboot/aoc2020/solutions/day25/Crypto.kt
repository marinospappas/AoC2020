package mpdev.springboot.aoc2020.solutions.day25

class Crypto(input: List<String>) {

    companion object {
        const val INITIAL_VALUE = 1L
        const val SUBJECT_NUMBER = 7L
        const val DIVIDER = 20201227L
    }

    val clientPublicKey = input[0].toLong()
    val serverPublicKey = input[1].toLong()

    fun createKey(subjectNumber: Long, loopSize: Long) =
        (1L .. loopSize).fold(INITIAL_VALUE) { acc, _ -> transformNumber(acc, subjectNumber) }

    fun getLoopSize(key: Long): Long {
        var loopSize = 0L
        var result = INITIAL_VALUE
        while(result != key) {
            result = transformNumber(result, SUBJECT_NUMBER)
            ++loopSize
        }
        return loopSize
    }

    private fun transformNumber(number: Long, subjectNumber: Long) = (number * subjectNumber) % DIVIDER
}