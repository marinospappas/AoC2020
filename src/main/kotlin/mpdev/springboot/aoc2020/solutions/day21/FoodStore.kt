package mpdev.springboot.aoc2020.solutions.day21

import mpdev.springboot.aoc2020.utils.AocException
import mpdev.springboot.aoc2020.utils.Dictionary

class FoodStore(input: List<String>) {

    companion object {
        val dict = Dictionary()
    }

    var foodsList: List<Food>
    val allergens= mutableMapOf<Int,MutableSet<Int>>()
    private var allIngredients: Set<Int>

    init {
        foodsList = processInput(input)
        foodsList.map { it.allergens }.flatten().distinct().forEach { allergens[it] = mutableSetOf() }
        allIngredients = foodsList.map { it.ingredients }.flatten().toSet()
    }

    fun findAllergensFromFoods() {
        allergens.keys.forEach { allergen ->
            // for each allergen find all the foods that contain it
            // and then find the common ingredients that are contained in all these foods
            // these are the candidates that contain that allergen
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
            // find any allergens that come from a known ingredient (the allergens that are contained in only one ingredient)
            // and remove these ingredients from the allergens that have more than one ingredient on their list
            val identifiedAllergenIngredients = allergens.values.filter { it.size == 1 }.flatten()
            allergens.values.filter { it.size > 1 }.forEach { listOfIngredients ->
                listOfIngredients.removeAll(identifiedAllergenIngredients)
            }
        }
    }

    fun identifyNonAllergenIngredients(): List<Int> {
        val allergenIngredients = allergens.values.map { it.first() }.toSet()
        return foodsList.map { it.ingredients - allergenIngredients }.flatten()
    }

    fun allergensSorted() = allergens.toSortedMap(compareBy<Int> { dict.keyFromMappedValue(it) })

    fun getItemName(mappedValue: Int) = dict.keyFromMappedValue(mappedValue)

    ///////////////////////////////////////////

    private fun processInput(input: List<String>): List<Food> {
        val processedList = mutableListOf<Food>()
        input.forEach { line ->
            try {
                // mxmxvkd kfcds sqjhc nhms (contains dairy, fish)
                val (ingredients, allergens) = Regex("""(.+) \(contains (.+)\)""").find(line)!!.destructured
                val ingredientsList = ingredients.split(" ")
                    .map { dict.get(it) }.toSet()
                val allergensList = allergens.replace(" ", "").split(",")
                    .map { dict.get(it) }.toSet()
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

data class Food(val ingredients: Set<Int>, val allergens: Set<Int>) {
    override fun toString() =
        "Food: Ingredients=${ingredients.map{FoodStore.dict.keyFromMappedValue(it)}}, " +
                "Allergens=${allergens.map{FoodStore.dict.keyFromMappedValue(it)}}"
}