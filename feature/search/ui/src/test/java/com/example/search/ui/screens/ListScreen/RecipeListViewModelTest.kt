package com.example.search.ui.screens.ListScreen

import com.example.common.utils.NetworkResult
import com.example.common.utils.UiText
import com.example.domain.model.Recipe
import com.example.domain.model.RecipeDetails
import com.example.domain.use_cases.GetAllRecipeUseCase
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RecipeListViewModelTest{

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun before(){
    }

    @After
    fun after(){
    }

    private val getRecipeDetailsUseCase : GetAllRecipeUseCase = mock()

    @Test
    fun test_meals_success() = runTest{
        `when`(getRecipeDetailsUseCase.invoke("Chicken"))
            .thenReturn(flowOf(NetworkResult.Success(data = getRecipeResponse())))
        val viewModel = RecipeListViewModel(getRecipeDetailsUseCase)
        viewModel.onEvent(RecipeList.Event.SearchRecipe("Chicken"))
        assertEquals(getRecipeResponse(),viewModel.uiState.value.data)
}

    @Test
    fun test_meals_failed() = runTest{
        `when`(getRecipeDetailsUseCase.invoke("Chicken"))
            .thenReturn(flowOf(NetworkResult.Error(message = "error")))
        val viewModel = RecipeListViewModel(getRecipeDetailsUseCase)
        viewModel.onEvent(RecipeList.Event.SearchRecipe("Chicken"))
        assertEquals(UiText.RemoteString("error"),viewModel.uiState.value.error)
}

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_meals_navigate_recipe_details() = runTest{
        val viewModel = RecipeListViewModel(getRecipeDetailsUseCase)
        viewModel.onEvent(RecipeList.Event.GoToRecipeDetails("id"))
        val list = mutableListOf<RecipeList.Navigation>()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.navigation.collectLatest {
                list.add(it)
            }
        }
        assert(list.first() is RecipeList.Navigation.GoToRecipeDetails)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()) :
    TestWatcher() {
    override fun starting(description: Description?) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        Dispatchers.resetMain()
    }
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