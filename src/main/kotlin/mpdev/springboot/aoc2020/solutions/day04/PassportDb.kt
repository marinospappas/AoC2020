package mpdev.springboot.aoc2020.solutions.day04

import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.validation.ConstraintViolation
import javax.validation.Validation.buildDefaultValidatorFactory
import javax.validation.Validator

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
                println(inputStr)
                println(inputStr.toJson())
                data.add(objectMapper.readValue(inputStr.toJson(), Passport::class.java))
                inputStr = ""
            }
        }
        data.add(objectMapper.readValue(inputStr.toJson(), Passport::class.java))
    }

    private fun String.toJson() = "{ \""+ replace(" ", "\", \"").replace(":", "\":\"") + "\" }"

    // heightValidator is instantiated explicitly here as custom constraint would not run...
    val heightValidator = HeightValidator()

    fun validatePassport(passport: Passport, validationGroup: Class<*>): Set<ConstraintViolation<Passport?>> {
        // hack to trigger the invalid height exception
        if (!heightValidator.isValid(passport.height, null))
            passport.invalidHeight = "INVALID"
        val validationResult = passportValidator.validate(passport, validationGroup)
        validationResult.forEach { error -> log.info("passport {} ERROR {} [{}]", passport.passportID, error.message, error.invalidValue) }
        return validationResult
    }
}
