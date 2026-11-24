package com.example.recipeorganizer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.recipeorganizer.model.Recipe

//a function to display all the recipe information. Put in a central location because it is used more than once.
@Composable
fun RecipeDetails(recipe: Recipe, onEditRecipe: (Recipe) -> Unit, onDeleteRecipe: (Recipe) -> Unit) {
    var showDeleteDialog = remember{ mutableStateOf(false) }
    Text(recipe.description)

    Text(buildAnnotatedString { withStyle(SpanStyle(fontWeight = FontWeight.Bold)){append("Servings: ")}
        append(recipe.servings.toString())
    })
    Text(buildAnnotatedString {withStyle(SpanStyle(fontWeight = FontWeight.Bold)) { append("Total Time: ") }
        append(recipe.convertTotalTime())
    })
    Text("Ingredients:", fontWeight = FontWeight.Bold)
    recipe.ingredients.forEach { ingredient ->
        Text("${ingredient.quantity} ${if (ingredient.unit == "None" || ingredient.unit == null){""} else (ingredient.unit + " ")}${ingredient.name}")
    }
    Text("Instructions:", fontWeight = FontWeight.Bold)
    recipe.instructions.forEach { instruction ->
        Text(instruction.step)
    }
    Text("Tags:", fontWeight = FontWeight.Bold)
    recipe.tags.forEach { tag ->
        Text(tag.name)
    }
    Row(modifier = Modifier.fillMaxWidth(), Arrangement.spacedBy(8.dp, Alignment.End)) {
        Button(
            onClick = {

               showDeleteDialog.value = true

            }, modifier = Modifier.size(48.dp), contentPadding = PaddingValues(0.dp),
            shape = CircleShape
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Filled.Close,
                contentDescription = "Delete Recipe"
            )
        }

        Button(
            onClick = { onEditRecipe(recipe) },
            modifier = Modifier.size(48.dp),
            contentPadding = PaddingValues(0.dp),
            shape = CircleShape
        ) {
            Icon(
                modifier = Modifier.size(24.dp),
                imageVector = Icons.Filled.Edit,
                contentDescription = "Edit Recipe"
            )
        }
    }

    if (showDeleteDialog.value) {
        AlertDialog(onDismissRequest =
            { showDeleteDialog.value = false },
            text = { Text("Delete ${recipe.title}? This cannot be undone!") },
            confirmButton =
                { TextButton(onClick = {showDeleteDialog.value = false
            onDeleteRecipe(recipe)}, content = {Text("Delete")}) },
            dismissButton =
                {TextButton(onClick = {showDeleteDialog.value = false}, content = {Text("Cancel")}) })
    }

}