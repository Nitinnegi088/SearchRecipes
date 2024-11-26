package com.example.search.data.mapper

import com.example.search.data.model.RecipeData
import com.example.search.data.model.RecipeResponse
import junit.framework.TestCase.assertEquals
import org.junit.Test

class MapperKtTest{

    @Test
    fun test_meals_happy_path(){
        val domainList = getRecipeResponse().meals?.toDomain()
        domainList?.let {
            assertEquals(1, it.size)
            val recipe = domainList[0]
            assertEquals("idMeal", recipe.idMeal)
            assertEquals("India", recipe.strArea)
            assertEquals("Chicken", recipe.strMeal)
            assertEquals("strMealThumb", recipe.strMealThumb)
            assertEquals("category", recipe.strCategory)
            assertEquals("tag1,tag2", recipe.strTags)
            assertEquals("strYoutube", recipe.strYoutube)
            assertEquals("strInstructions", recipe.strInstructions)
        }
    }

    @Test
    fun test_meals_null_path() {
        val domainList = getNullRecipeResponse().meals?.toDomain()
        domainList?.let {
            val recipe = domainList[0]
            assertEquals("", recipe.idMeal)
            assertEquals("", recipe.strArea)
            assertEquals("", recipe.strMeal)
            assertEquals("", recipe.strMealThumb)
            assertEquals("", recipe.strCategory)
            assertEquals("", recipe.strTags)
            assertEquals("", recipe.strYoutube)
            assertEquals("", recipe.strInstructions)
        }
    }

    @Test
    fun test_meals_empty_path() {
        val recipeDataList = emptyList<RecipeData>()
        val domainList = recipeDataList.toDomain()
        assertEquals(0, domainList.size)
    }

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

private fun getNullRecipeResponse(): RecipeResponse {
    return RecipeResponse(
        meals = listOf(
            RecipeData(
                dateModified = null,
                idMeal = null,
                strArea = null,
                strCategory = null,
                strYoutube = null,
                strTags = null,
                strMeal = null,
                strSource = null,
                strMealThumb = null,
                strInstructions = null,
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
                strImageSource = null
            )
        )
    )
}
