package com.example.domain.use_cases

import com.example.domain.model.Recipe
import com.example.domain.repository.SearchRepository
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetAllRecipeUseCaseTest{

    private val searchRepository : SearchRepository = mock()

    @Test
    fun test_meals_success() = runTest{
        `when`(searchRepository.getRecipes("Chicken"))
            .thenReturn(Result.success(getRecipeResponse()))
        val useCase = GetAllRecipeUseCase(searchRepository)
        val response = useCase.invoke("Chicken")
        assertEquals(getRecipeResponse(),response.last().data)
    }

    @Test
    fun test_meals_failed() = runTest {
        `when`(searchRepository.getRecipes("Chicken"))
            .thenReturn(Result.failure(RuntimeException("error")))
        val useCase = GetAllRecipeUseCase(searchRepository)
        val response = useCase.invoke("Chicken")
        assertEquals("error", response.last().message)
    }

    @Test
    fun test_meals_exception() = runTest {
        `when`(searchRepository.getRecipes("Chicken"))
            .thenThrow(RuntimeException("error"))
        val useCase = GetAllRecipeUseCase(searchRepository)
        val response = useCase.invoke("Chicken")
        assertEquals("error", response.last().message)
    }

    private fun getRecipeResponse(): List<Recipe> {
        return listOf(
            Recipe(
                idMeal = "idMeal",
                strArea = "India",
                strCategory = "category",
                strYoutube = "strYoutube",
                strTags = "tag1,tag2",
                strMeal = "Chicken",
                strMealThumb = "strMealThumb",
                strInstructions = "12",
            ),
            Recipe(
                idMeal = "idMeal",
                strArea = "India",
                strCategory = "category",
                strYoutube = "strYoutube",
                strTags = "tag1,tag2",
                strMeal = "Chicken",
                strMealThumb = "strMealThumb",
                strInstructions = "123",
            )
        )
    }
}