package com.example.domain.use_cases

import com.example.domain.model.RecipeDetails
import com.example.domain.repository.SearchRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetRecipeDetailsUseCaseTest{

    private val searchRepository: SearchRepository = mock()

    @Test
    fun test_meals_details_success() = runTest {
        `when`(searchRepository.getRecipeDetails("Chicken"))
            .thenReturn(Result.success(getRecipeDetails()))
        val useCase = GetRecipeDetailsUseCase(searchRepository)
        val response = useCase.invoke("Chicken")
        assertEquals(getRecipeDetails(),response.last().data)
    }

    @Test
    fun test_meals_details_failed() = runTest {
        `when`(searchRepository.getRecipeDetails("Chicken"))
            .thenReturn(Result.failure(RuntimeException("error")))
        val useCase = GetRecipeDetailsUseCase(searchRepository)
        val response = useCase.invoke("Chicken")
        assertEquals("error", response.last().message)
    }

    @Test
    fun test_meals_details_exception() = runTest {
        `when`(searchRepository.getRecipeDetails("Chicken"))
            .thenThrow(RuntimeException("error"))
        val useCase = GetRecipeDetailsUseCase(searchRepository)
        val response = useCase.invoke("Chicken")
        assertEquals("error", response.last().message)
    }

    private fun getRecipeDetails(): RecipeDetails {
        return RecipeDetails(
            idMeal = "idMeal",
            strArea = "India",
            strCategory = "category",
            strYoutube = "strYoutube",
            strTags = "tag1,tag2",
            strMeal = "Chicken",
            strMealThumb = "strMealThumb",
            strInstructions = "strInstructions",
            ingredientsPair = listOf(Pair("Ingredients", "Measure"))
        )
    }
}