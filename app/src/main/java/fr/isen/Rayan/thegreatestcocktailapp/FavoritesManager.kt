package fr.isen.Rayan.thegreatestcocktailapp

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object FavoritesManager {
    private const val PREFS_NAME = "cocktail_prefs"
    private const val FAVORITES_KEY = "favorites"

    private val gson = Gson()

    fun getFavorites(context: Context): List<DrinkItem> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(FAVORITES_KEY, null) ?: return emptyList()

        val type = object : TypeToken<List<DrinkItem>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveFavorites(context: Context, favorites: List<DrinkItem>) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = gson.toJson(favorites)
        prefs.edit().putString(FAVORITES_KEY, json).apply()
    }

    fun addFavorite(context: Context, drink: DrinkItem) {
        val favorites = getFavorites(context).toMutableList()

        if (favorites.none { it.idDrink == drink.idDrink }) {
            favorites.add(drink)
            saveFavorites(context, favorites)
        }
    }

    fun removeFavorite(context: Context, drinkId: String) {
        val favorites = getFavorites(context).filter { it.idDrink != drinkId }
        saveFavorites(context, favorites)
    }

    fun isFavorite(context: Context, drinkId: String): Boolean {
        return getFavorites(context).any { it.idDrink == drinkId }
    }
}