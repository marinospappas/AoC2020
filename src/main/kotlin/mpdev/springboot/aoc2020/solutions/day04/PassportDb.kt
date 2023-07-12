package mpdev.springboot.aoc2020.solutions.day04

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.validation.ConstraintViolation
import jakarta.validation.Validation.buildDefaultValidatorFactory
import jakarta.validation.Validator
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PassportDb(input: List<String>) {

    companion object {
        private val log: Logger = LoggerFactory.getLogger(this::class.java)
    }

    var data: MutableList<Passport> = mutableListOf()

    private var passportValidator: Validator = buildDefaultValidatorFactory().validator

    init {
        val objectMapper = ObjectMapper()
        var inputStr = ""
        input.forEach { line ->
            if (line.isNotEmpty())
                inputStr = if (inputStr.isEmpty()) line else "$inputStr $line"
            else  {
                data.add(objectMapper.readValue(inputStr.toJson(), Passport::class.java))
                inputStr = ""
            }
        }
        data.add(objectMapper.readValue(inputStr.toJson(), Passport::class.java))
        log.info("passport DB size: {}", data.size)
    }

    private fun String.toJson() = "{ \""+ replace(" ", "\", \"").replace(":", "\":\"") + "\" }"

    fun validatePassport(passport: Passport, validationGroup: Class<*>): Set<ConstraintViolation<Passport?>> {
        val validationResult = passportValidator.validate(passport, validationGroup)
        validationResult.forEach { error -> log.info("passport {} ERROR {} [{}]", passport.passportID, error.message, error.invalidValue) }
        return validationResult
    }
}

// validation groups for part 1 and part 2 respectively
class ValidationGroups {
    interface Part1
    interface Part2
}
