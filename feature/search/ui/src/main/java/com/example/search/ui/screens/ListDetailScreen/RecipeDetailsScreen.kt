package com.example.search.ui.screens.ListDetailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.common.utils.UiText
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailsScreen(
    viewModel: RecipeDetailsViewModel,
    navHostController: NavHostController,
    onNavigationClick: () -> Unit
) {

    val uiState = viewModel.uiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(key1 = viewModel.navigation) {
        viewModel.navigation.flowWithLifecycle(lifecycleOwner.lifecycle)
            .collectLatest { navigation ->
                when (navigation) {
                    RecipeDetails.Navigation.GoToRecipeListScreen -> navHostController.popBackStack()
                }
            }
    }

    Scaffold(topBar = {
        TopAppBar(title = {
            Text(
                text = uiState.value.data?.strMeal.toString(),
                style = MaterialTheme.typography.bodyLarge
            )
        }, navigationIcon = {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null,
                modifier = Modifier.clickable {
                    onNavigationClick.invoke()
                })
        }, actions = {
        })
    }) {
        if (uiState.value.isLoading) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        if (uiState.value.error !is UiText.Idle) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize(), contentAlignment = Alignment.Center
            ) {
                Text(text = uiState.value.error.getString())
            }
        }

        uiState.value.data?.let { recipeDetails ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    model = recipeDetails.strMealThumb,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = recipeDetails.strInstructions,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    recipeDetails.ingredientsPair.forEach {
                        if (it.first.isNotBlank() || it.second.isNotBlank()) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = getIngredientsImageUrl(it.first),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(60.dp)
                                        .background(color = Color.White, shape = CircleShape)
                                        .clip(CircleShape)
                                )

                                Text(text = it.second, style = MaterialTheme.typography.bodyMedium)
                            }
                        }

                    }

                }

            }


        }


    }


}

fun getIngredientsImageUrl(name: String) =
    "https://www.themealdb.com/images/ingredients/${name}.png"