package com.example.recipeorganizer.data

import android.content.Context
import com.example.recipeorganizer.model.Recipe
import kotlinx.serialization.json.Json
import java.io.File


class RecipeRepository(val lib:RecipeLibrary, private val context: Context) {
    private val file = File(context.filesDir, "recipes.json")
    //converts library to JSON
    fun libToJson(): String {
        val library = lib.returnAllRecipes()
        val jsonString = Json.encodeToString(library)
        return jsonString
    }

    //writes JSON to file and creates directory "data" if it does not exist
    fun jsonToFile(json: String) {

            file.writeText(json)


    }

    //connects libToJson and jsonToFile so the program only needs to call one function to save
    fun saveLibrary() {
        jsonToFile(libToJson())
    }

    //takes string from file and converts it back to JSON
    fun jsonToLib(file:File): List<Recipe> {
        val string =file.readText()
        val restoredList = Json.decodeFromString<List<Recipe>>(string)
        return restoredList

    }

    fun listToLib(list: List<Recipe>) {
        for (recipe in list) {
            if (lib.findRecipeById(recipe.id) == null)
            {lib.addRecipe(recipe)}
        }
    }

    //connects jsonToLib and listToLib for one call to load repository
    fun loadLib() {

        if (file.exists()) {

                listToLib(jsonToLib(file))
            }
        }

    }




