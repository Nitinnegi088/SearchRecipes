package com.example.search.data.Repository

import com.example.common.utils.Constants.AN_UNEXPECTED_ERROR_OCCURRED
import com.example.common.utils.Constants.NO_DATA_FOUND
import com.example.common.utils.Constants.SERVER_IS_DOWN_OR_UNREACHABLE
import com.example.search.data.mapper.toDomain
import com.example.search.data.model.RecipeData
import com.example.search.data.model.RecipeDetailResponse
import com.example.search.data.model.RecipeResponse
import com.example.search.data.remote.SearchApiService
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

class SearchRepoImplTest{

    private val searchApiService: SearchApiService = mock()

    @Test
    fun test_meals_success() = runTest {
        `when`(searchApiService.getRecipes("Chicken"))
            .thenReturn(Response.success(200,getRecipeResponse()))
        val repo = SearchRepoImpl(searchApiService)
        val response = repo.getRecipes("Chicken")
        assertEquals(getRecipeResponse().meals?.toDomain(),response.getOrThrow())
    }

    @Test
    fun test_null_meals_from_backend() = runTest{
        `when`(searchApiService.getRecipes("Chicken"))
            .thenReturn(Response.success(200, RecipeResponse()))
        val repo = SearchRepoImpl(searchApiService)
        val response = repo.getRecipes("Chicken")
        val message = AN_UNEXPECTED_ERROR_OCCURRED
        assertEquals(message,response.exceptionOrNull()?.message)
    }

    @Test
    fun test_meals_response_failed_from_backend() = runTest {
        `when`(searchApiService.getRecipes("Chicken"))
            .thenReturn(Response.error(404, ResponseBody.create(null,"")))
        val repo = SearchRepoImpl(searchApiService)
        val response = repo.getRecipes("Chicken")
        val message = SERVER_IS_DOWN_OR_UNREACHABLE
        assertEquals(message,response.exceptionOrNull()?.message)
    }

    @Test
    fun test_meals_response_throw_exception() = runTest {
        `when`(searchApiService.getRecipes("Chicken"))
            .thenThrow(RuntimeException("error"))
        val repo = SearchRepoImpl(searchApiService)
        val response = repo.getRecipes("Chicken")
        val message = "error"
        assertEquals(message, response.exceptionOrNull()?.message)
    }

    @Test
    fun test_meals_details_success() = runTest {
        `when`(searchApiService.getRecipeDetails("id"))
            .thenReturn(Response.success(200,getRecipeDetails()))
        val repo = SearchRepoImpl(searchApiService)
        val response = repo.getRecipeDetails("id")
        assertEquals(getRecipeDetails().meals?.first()?.toDomain(),response.getOrThrow())
    }

    @Test
    fun test_meals_details_success_with_empty_list() = runTest {
        `when`(searchApiService.getRecipeDetails("id"))
            .thenReturn(Response.success(200, RecipeDetailResponse(meals = emptyList())))
        val repo = SearchRepoImpl(searchApiService)
        val response = repo.getRecipeDetails("id")
        assertEquals(NO_DATA_FOUND, response.exceptionOrNull()?.message)
    }

    @Test
    fun test_meals_details_success_with_nulls() = runTest {
        `when`(searchApiService.getRecipeDetails("id")).thenReturn(
            Response.success(200, RecipeDetailResponse())
        )
        val repo = SearchRepoImpl(searchApiService)
        val response = repo.getRecipeDetails("id")
        assertEquals(AN_UNEXPECTED_ERROR_OCCURRED, response.exceptionOrNull()?.message)
    }

    @Test
    fun test_meals_details_failed_from_backend() = runTest {
        `when`(searchApiService.getRecipeDetails("id"))
            .thenReturn(Response.error(404, ResponseBody.create(null, "")))
        val repo = SearchRepoImpl(searchApiService)
        val response = repo.getRecipeDetails("id")
        assertEquals(SERVER_IS_DOWN_OR_UNREACHABLE, response.exceptionOrNull()?.message)
    }

    @Test
    fun test_meals_details_throw_exception_from_backend() = runTest {
        `when`(searchApiService.getRecipeDetails("id"))
            .thenThrow(RuntimeException("error"))
        val repo = SearchRepoImpl(searchApiService)
        val response = repo.getRecipeDetails("id")
        assertEquals("error", response.exceptionOrNull()?.message)
    }

    private fun getRecipeResponse(): RecipeResponse {
        return RecipeResponse(
            meals = listOf(
                RecipeData(
                    dateModified = null,
                    idMeal = "idMeal",
                    strArea = "India",
                    strCategory = "category",
                    strYoutube = "strYoutube",
                    strTags = "tag1,tag2",
                    strMeal = "Chicken",
                    strSource = "strSource",
                    strMealThumb = "strMealThumb",
                    strInstructions = "strInstructions",
                    strCreativeCommonsConfirmed = null,
                    strIngredient1 = null,
                    strIngredient2 = null,
                    strIngredient3 = null,
                    strIngredient4 = null,
                    strIngredient5 = null,
                    strIngredient6 = null,
                    strIngredient7 = null,
                    strIngredient8 = null,
                    strIngredient9 = null,
                    strIngredient10 = null,
                    strIngredient11 = null,
                    strIngredient12 = null,
                    strIngredient13 = null,
                    strIngredient14 = null,
                    strIngredient15 = null,
                    strIngredient16 = null,
                    strIngredient17 = null,
                    strIngredient18 = null,
                    strIngredient19 = null,
                    strIngredient20 = null,
                    strMeasure1 = null,
                    strMeasure2 = null,
                    strMeasure3 = null,
                    strMeasure4 = null,
                    strMeasure5 = null,
                    strMeasure6 = null,
                    strMeasure7 = null,
                    strMeasure8 = null,
                    strMeasure9 = null,
                    strMeasure10 = null,
                    strMeasure11 = null,
                    strMeasure12 = null,
                    strMeasure13 = null,
                    strMeasure14 = null,
                    strMeasure15 = null,
                    strMeasure16 = null,
                    strMeasure17 = null,
                    strMeasure18 = null,
                    strMeasure19 = null,
                    strMeasure20 = null,
                    strDrinkAlternate = null,
                    strImageSource = "empty"
                )
            )
        )
    }

    private fun getRecipeDetails(): RecipeDetailResponse {
        return RecipeDetailResponse(
            meals = listOf(
                getRecipeResponse().meals?.first()!!
            )
        )
    }
}