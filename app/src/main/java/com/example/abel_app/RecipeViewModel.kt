package com.example.abel_app.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.Flow
import com.example.abel_app.models.Recipe

class RecipeViewModel : ViewModel() {

    private val _recipes = MutableStateFlow<List<Recipe>>(emptyList())
    val recipes: StateFlow<List<Recipe>> = _recipes

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query

    init {
        // Sample recipe data
        _recipes.value = listOf(
            Recipe(1, "Pasta", "Delicious pasta with tomato sauce."),
            Recipe(2, "Pizza", "Cheesy pizza with pepperoni."),
            Recipe(3, "Salad", "Healthy green salad with avocado.")
        )
    }

    fun setQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun getFilteredRecipes(): Flow<List<Recipe>> {
        return combine(_recipes, _query) { recipes, query ->
            if (query.length >= 3) {
                recipes.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.description.contains(query, ignoreCase = true)
                }
            } else {
                recipes
            }
        }
    }
}