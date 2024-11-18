package com.example.domain.repository

import com.example.domain.model.Recipe
import com.example.domain.model.RecipeDetails

interface SearchRepository {

    suspend fun getRecipes(s:String): Result<List<Recipe>>

    suspend fun getRecipeDetails(id: String): Result<RecipeDetails>

}