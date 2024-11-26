package com.example.search.data.Repository

import com.example.common.utils.Constants.AN_UNEXPECTED_ERROR_OCCURRED
import com.example.common.utils.Constants.NO_DATA_FOUND
import com.example.common.utils.Constants.SERVER_IS_DOWN_OR_UNREACHABLE
import com.example.domain.model.Recipe
import com.example.domain.model.RecipeDetails
import com.example.domain.repository.SearchRepository
import com.example.search.data.mapper.toDomain
import com.example.search.data.remote.SearchApiService

class SearchRepoImpl(
    private val searchApiService: SearchApiService
) : SearchRepository {
    override suspend fun getRecipes(s: String): Result<List<Recipe>> {
        return try {
            val response = searchApiService.getRecipes(s)
            if (response.isSuccessful) {
                response.body()?.meals?.let {
                    Result.success(it.toDomain())
                } ?: run { Result.failure(Exception(AN_UNEXPECTED_ERROR_OCCURRED)) }
            } else {
                Result.failure(Exception(SERVER_IS_DOWN_OR_UNREACHABLE))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getRecipeDetails(id: String): Result<RecipeDetails> {
        return try {
            val response = searchApiService.getRecipeDetails(id)
            if (response.isSuccessful) {
                response.body()?.meals?.let {
                    if (it.isNotEmpty()) {
                        Result.success(it.first().toDomain())
                    } else {
                        Result.failure(Exception(NO_DATA_FOUND))
                    }
                } ?: run {
                    Result.failure(Exception(AN_UNEXPECTED_ERROR_OCCURRED))
                }
            } else {
                Result.failure(Exception(SERVER_IS_DOWN_OR_UNREACHABLE))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}