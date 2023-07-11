package mpdev.springboot.aoc2020.solutions.day04

import org.springframework.web.context.annotation.ApplicationScope
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

@ApplicationScope
class HeightValidator: ConstraintValidator<ValidHeight, String> {

    override fun isValid(height: String?, context: ConstraintValidatorContext?): Boolean {
        if (height == null || height.length < 3)
            return true
        val heightValue: Int
        try {
            heightValue =  height.substring(0, height.length-2).toInt()
        }
        catch (e: Exception) {
            return true
        }
        val heightUnit = height.substring(height.length-2, height.length)
        return heightUnit == "cm" && heightValue in 150..193
                || heightUnit == "in" && heightValue in 59..76
    }
}
