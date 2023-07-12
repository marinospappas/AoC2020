package mpdev.springboot.aoc2020.solutions.day04

import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [HeightValidator::class])
@MustBeDocumented
@Suppress("unused")
annotation class ValidHeight (
    val message: String = "Invalid Height",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class HeightValidator: ConstraintValidator<ValidHeight, String> {

    override fun isValid(height: String?, context: ConstraintValidatorContext?): Boolean {
        if (height == null || height.length < 3 || !height.substring(0,height.length-2).all{ it.isDigit() })
            return true
        val heightValue = height.substring(0, height.length-2).toInt()
        val heightUnit = height.substring(height.length-2, height.length)
        return heightUnit == "cm" && heightValue in 150..193
                || heightUnit == "in" && heightValue in 59..76
    }
}
