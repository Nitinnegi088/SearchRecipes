package com.example.search.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.common.navigation.FeatureApi
import com.example.common.navigation.NavigationRoute
import com.example.common.navigation.NavigationSubGraphRoute
import com.example.search.ui.screens.ListDetailScreen.RecipeDetails
import com.example.search.ui.screens.ListDetailScreen.RecipeDetailsScreen
import com.example.search.ui.screens.ListDetailScreen.RecipeDetailsViewModel
import com.example.search.ui.screens.ListScreen.RecipeList
import com.example.search.ui.screens.ListScreen.RecipeListScreen
import com.example.search.ui.screens.ListScreen.RecipeListViewModel

interface SearchFeatureApi : FeatureApi

class SearchFeatureApiImpl : SearchFeatureApi {
    override fun registerGraph(
        navGraphBuilder: androidx.navigation.NavGraphBuilder,
        navHostController: androidx.navigation.NavHostController
    ) {
        navGraphBuilder.navigation(
            route = NavigationSubGraphRoute.Search.route,
            startDestination = NavigationRoute.RecipeList.route
        ) {

            composable(route = NavigationRoute.RecipeList.route) {
                val viewModel = hiltViewModel<RecipeListViewModel>()
                RecipeListScreen(
                    viewModel = viewModel,
                    navHostController = navHostController
                ) { mealId ->
                    viewModel.onEvent(RecipeList.Event.GoToRecipeDetails(mealId))
                }

            }

            composable(route = NavigationRoute.RecipeDetails.route) {
                val viewModel = hiltViewModel<RecipeDetailsViewModel>()
                val mealId = it.arguments?.getString("id")
                LaunchedEffect(key1 = mealId) {
                    mealId?.let { mId ->
                        viewModel.onEvent(RecipeDetails.Event.FetchRecipeDetails(mId))
                    }
                }
                RecipeDetailsScreen(
                    viewModel = viewModel,
                    onNavigationClick = {
                        viewModel.onEvent(RecipeDetails.Event.GoToRecipeListScreen)
                    },
                     navHostController = navHostController
                )
            }
        }
    }
}