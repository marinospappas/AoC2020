package mpdev.springboot.aoc2020.solutions.day21

import mpdev.springboot.aoc2020.utils.AocException

class FoodStore(input: List<String>) {

    var foodsList: List<Food>
    val allergens= mutableMapOf<String,MutableSet<String>>()
    private var allIngredients: Set<String>

    init {
        foodsList = processInput(input)
        foodsList.map { it.allergens }.flatten().distinct().forEach { allergens[it] = mutableSetOf() }
        allIngredients = foodsList.map { it.ingredients }.flatten().toSet()
    }

    fun findAllergensFromFoods() {
        allergens.keys.forEach { allergen ->
            // for each allergen find all the foods that contain it
            // and then find the common ingredients that are contained in all these foods
            val foodsThatContainThisAllergen = foodsList.filter { it.allergens.contains(allergen) }
            val commonIngredients = foodsThatContainThisAllergen.map { it.ingredients }
                .fold(allIngredients) { acc, ingredients -> acc.intersect(ingredients) }
            allergens[allergen] = commonIngredients.toMutableSet()
        }
    }

    fun identifyAllergenIngredients() {
        if (allergens.values.none { it.size == 1 })
            throw AocException("could not identify any allergens")
        while (allergens.values.any { it.size > 1 }) {
            // find any allergens that come from a known ingredient
            // and remove these ingredients from the allergens that have more than one ingredient on their list
            val identifiedAllergenIngredients = allergens.values.filter { it.size == 1 }.flatten()
            identifiedAllergenIngredients.forEach { ingredient ->
                allergens.values.filter { it.size > 1 }.forEach { listOfIngredients ->
                    listOfIngredients.remove(ingredient)
                }
            }
        }
    }

    fun identifyNonAllergenIngredients(): List<String> {
        val allergenIngredients = allergens.values.map { it.first() }.toSet()
        return foodsList.map { it.ingredients - allergenIngredients }.flatten()
    }

    ///////////////////////////////////////////

    private fun processInput(input: List<String>): List<Food> {
        val processedList = mutableListOf<Food>()
        input.forEach { line ->
            try {
                // mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
                val (ingredients, allergens) = Regex("""(.+) \(contains (.+)\)""").find(line)!!.destructured
                val ingredientsList = ingredients.split(" ").toSet()
                val allergensList = allergens.replace(" ", "").split(",").toSet()
                processedList.add(Food(ingredientsList, allergensList))
            }
            catch (e: Exception) {
                e.printStackTrace()
                throw AocException("bad input line $line")
            }
        }
        return processedList
    }
}

data class Food(val ingredients: Set<String>, val allergens: Set<String>)