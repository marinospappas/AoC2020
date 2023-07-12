package mpdev.springboot.aoc2020.solutions.day04

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import mpdev.springboot.aoc2020.solutions.day04.ValidationGroups.*

data class Passport(
    @JsonProperty("byr")
    @field:NotBlank(message = "EX100: Birth Year cannot be empty", groups = [Part1::class, Part2::class])
    @field:Min(value = 1920, message = "EX201: Birth Year must be 1920 or later", groups = [Part2::class])
    @field:Max(value = 2002, message = "EX202: Birth Year must be 2002 or earlier", groups = [Part2::class])
    var birthYear: String? = null,

    @JsonProperty("iyr")
    @field:NotBlank(message = "EX101: Issue Year cannot be empty", groups = [Part1::class, Part2::class])
    @field:Min(value = 2010, message = "EX203: Issue Year must be 2010 or later", groups = [Part2::class])
    @field:Max(value = 2020, message = "EX204: Issue Year must be 2020 or earlier", groups = [Part2::class])
    var issueYear: String? = null,

    @JsonProperty("eyr")
    @field:NotBlank(message = "EX102: Expiry Year cannot be empty", groups = [Part1::class, Part2::class])
    @field:Min(value = 2020, message = "EX205: Expiry Year must be 2020 or later", groups = [Part2::class])
    @field:Max(value = 2030, message = "EX206: Expiry Year must be 2030 or earlier", groups = [Part2::class])
    var expirationYear: String? = null,

    @JsonProperty("hgt")
    @field:NotBlank(message = "EX103: Height cannot be empty", groups = [Part1::class, Part2::class])
    @field:Pattern(regexp = """\d+cm|\d+in""", message = "EX207: Height must be a number followed by one of: cm in", groups = [Part2::class])
    @field:ValidHeight(message = "EX209: Height must be between 150 and 193cm (59-76in) or above", groups = [Part2::class])
    var height: String? = null,

    @JsonProperty("hcl")
    @field:NotBlank(message = "EX104: Hair Color cannot be empty", groups = [Part1::class, Part2::class])
    @field:Pattern(regexp = """#[0-9a-f]{6}""", message = "EX210: Hair Color must be # followed by 6-digit hex number", groups = [Part2::class])
    var hairColor: String? = null,

    @JsonProperty("ecl")
    @field:NotBlank(message = "EX105: Eye Color cannot be empty", groups = [Part1::class, Part2::class])
    @field:Pattern(regexp = """amb|blu|brn|gry|grn|hzl|oth""", message = "EX211: Eye Color must be one of: amb blu brn gry grn hzl oth", groups = [Part2::class])
    var eyeColor: String? = null,

    @JsonProperty("pid")
    @field:NotBlank(message = "EX106: Passport ID cannot be empty", groups = [Part1::class, Part2::class])
    @field:Pattern(regexp = """[0-9]{9}""", message = "EX212: Passport ID must be 9-digit number", groups = [Part2::class])
    var passportID: String? = null,

    @JsonProperty("cid")
    val countryID: String? = null
)