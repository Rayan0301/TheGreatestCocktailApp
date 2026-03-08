package fr.isen.Rayan.thegreatestcocktailapp

import com.google.gson.annotations.SerializedName

data class CategoryItem(
    @SerializedName("strCategory")
    val strCategory: String
)

data class CategoryResponse(
    @SerializedName("drinks")
    val drinks: List<CategoryItem>
)

data class DrinkItem(
    @SerializedName("idDrink")
    val idDrink: String,

    @SerializedName("strDrink")
    val strDrink: String,

    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String
)

data class DrinksResponse(
    @SerializedName("drinks")
    val drinks: List<DrinkItem>
)

data class CocktailDetail(
    @SerializedName("idDrink")
    val idDrink: String,

    @SerializedName("strDrink")
    val strDrink: String,

    @SerializedName("strDrinkThumb")
    val strDrinkThumb: String,

    @SerializedName("strCategory")
    val strCategory: String?,

    @SerializedName("strAlcoholic")
    val strAlcoholic: String?,

    @SerializedName("strGlass")
    val strGlass: String?,

    @SerializedName("strInstructions")
    val strInstructions: String?,

    @SerializedName("strIngredient1")
    val strIngredient1: String?,

    @SerializedName("strIngredient2")
    val strIngredient2: String?,

    @SerializedName("strIngredient3")
    val strIngredient3: String?,

    @SerializedName("strIngredient4")
    val strIngredient4: String?,

    @SerializedName("strIngredient5")
    val strIngredient5: String?
)

data class CocktailDetailResponse(
    @SerializedName("drinks")
    val drinks: List<CocktailDetail>
)