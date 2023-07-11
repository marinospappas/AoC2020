package mpdev.springboot.aoc2020.solutions.day04

import javax.validation.Constraint
import javax.validation.Payload
import kotlin.annotation.AnnotationTarget.*
import kotlin.reflect.KClass

@Target(FIELD, FUNCTION, VALUE_PARAMETER, ANNOTATION_CLASS, TYPE_PARAMETER, TYPE)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [HeightValidator::class])
@MustBeDocumented
annotation class ValidHeight (
    val message: String = "End date must be after begin date and both must be in the future",
    val field: String = "height",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
)
