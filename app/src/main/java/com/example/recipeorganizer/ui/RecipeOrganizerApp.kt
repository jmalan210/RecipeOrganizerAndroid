package com.example.recipeorganizer.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.recipeorganizer.data.RecipeLibrary
import com.example.recipeorganizer.data.RecipeRepository
import com.example.recipeorganizer.model.Recipe


//the "brains" of the GUI. All navigation is routed through this function.
@Composable
fun RecipeOrganizerApp(recLib: RecipeLibrary, recRepo: RecipeRepository) {
    val currentScreen = remember{mutableStateOf("home")}
    val recipeToEdit = remember{mutableStateOf<Recipe?>(null)}
    val savedRecipeId = remember{mutableStateOf<String?>(null)}


        when (currentScreen.value) {
            "home" -> HomeScreen{newScreen: String -> if(newScreen == "addRecipe") {recipeToEdit.value = null}
                if(newScreen == "recipeList") {savedRecipeId.value = null}
                currentScreen.value = newScreen}
            "addRecipe" -> AddRecipeScreen({newScreen: String -> currentScreen.value = newScreen}, recLib, recRepo, recipeToEdit.value, onRecipeSaved = {id:String -> savedRecipeId.value = id
                currentScreen.value = "recipeList"})
            "recipeList" -> RecipeListScreen({newScreen: String -> currentScreen.value = newScreen}, recLib, recRepo,  onEditRecipe = {recipe: Recipe -> recipeToEdit.value = recipe; currentScreen.value = "addRecipe"}, savedRecipeId = savedRecipeId.value)
            "search" -> SearchScreen({newScreen: String -> currentScreen.value = newScreen}, recLib)
            else -> HomeScreen{newScreen: String -> currentScreen.value = newScreen}
        }

    }
