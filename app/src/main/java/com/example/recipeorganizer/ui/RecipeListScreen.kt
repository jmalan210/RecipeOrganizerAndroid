package com.example.recipeorganizer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import com.example.recipeorganizer.data.RecipeLibrary
import com.example.recipeorganizer.data.RecipeRepository
import com.example.recipeorganizer.model.Recipe

//manages the Recipe List screen.  Displays all recipes in alphabetical order.  User can click individual recipe cards to open and close.
@Composable
fun RecipeListScreen(onNavigate: (String) -> Unit, recLib: RecipeLibrary, recRepo: RecipeRepository, onEditRecipe: (Recipe) -> Unit, savedRecipeId: String? = null) {

    val recipeList = recLib.returnAllRecipes().sortedBy{recipe-> recipe.title.lowercase()}
    val selectedRecipe: MutableState<Recipe?> = remember(savedRecipeId) { mutableStateOf(savedRecipeId?.let{recLib.findRecipeById(it)}) }
    val listState = rememberLazyListState()
    val savedRecipeIndex= recipeList.indexOfFirst{it.id ==savedRecipeId}
    LaunchedEffect(savedRecipeId) {
        if (savedRecipeIndex != -1) {
            listState.animateScrollToItem(savedRecipeIndex + 1)
        }}
    Column {
        Button(onClick = { onNavigate("home") },
            Modifier.align(Alignment.Start).padding(16.dp)) {
            Icon(
                imageVector = Icons.Filled.Home,
                contentDescription = "Return Home"
            )}
        LazyColumn(
            modifier = Modifier.padding(16.dp).fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            state = listState
//        horizontalAlignment = Alignment.CenterHorizontally
        ) {


            item { Text("All Recipes", Modifier.fillMaxWidth(),style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center ) }


            items(recipeList) { recipe ->
                val currentRecipe = selectedRecipe.value
                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clickable{
                            if (currentRecipe != null && currentRecipe.id == recipe.id) {
                                selectedRecipe.value = null
                            } else {
                                selectedRecipe.value = recipe
                            }}) {

                    Text(recipe.title, fontWeight = FontWeight.Bold, modifier = Modifier.padding(10.dp) )

                }
                val recipeValue = selectedRecipe.value
                if (recipeValue != null && recipe.id == recipeValue.id) {
                    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            RecipeDetails(recipeValue, onEditRecipe, onDeleteRecipe =
                                {recLib.removeRecipe(it.id)
                                recRepo.saveLibrary()
                                selectedRecipe.value = null})

                        }
                    }
                }
            }

            item {


            }
        }
    }}
