package com.example.search.ui.screens.ListDetailScreen

import com.example.common.utils.NetworkResult
import com.example.common.utils.UiText
import com.example.domain.use_cases.GetRecipeDetailsUseCase
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
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RecipeDetailsViewModelTest{

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun before(){

    }

    @After
    fun after(){

    }

    private val getRecipeDetailsUseCase: GetRecipeDetailsUseCase = mock()

    @Test
    fun test_meals_success() = runTest {
        `when`(getRecipeDetailsUseCase.invoke("id"))
            .thenReturn(flowOf(NetworkResult.Success(data = getRecipeDetails())))
        val viewModel = RecipeDetailsViewModel(getRecipeDetailsUseCase)
        viewModel.onEvent(RecipeDetails.Event.FetchRecipeDetails("id"))
        assertEquals(getRecipeDetails(), viewModel.uiState.value.data)
    }

    @Test
    fun test_meals_failed() = runTest {
        `when`(getRecipeDetailsUseCase.invoke("id"))
            .thenReturn(flowOf(NetworkResult.Error("error")))
        val viewModel = RecipeDetailsViewModel(getRecipeDetailsUseCase)
        viewModel.onEvent(RecipeDetails.Event.FetchRecipeDetails("id"))
        assertEquals(UiText.RemoteString("error"), viewModel.uiState.value.error)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun test_meals_navigate_recipe_list_screen() = runTest {
        val viewModel = RecipeDetailsViewModel(getRecipeDetailsUseCase)
        viewModel.onEvent(RecipeDetails.Event.GoToRecipeListScreen)
        val list = mutableListOf<RecipeDetails.Navigation>()
        backgroundScope.launch(UnconfinedTestDispatcher()) {
            viewModel.navigation.collectLatest {
                list.add(it)
            }
        }
        assert(list.first() is RecipeDetails.Navigation.GoToRecipeListScreen)
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

private fun getRecipeDetails(): com.example.domain.model.RecipeDetails {
    return com.example.domain.model.RecipeDetails(
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