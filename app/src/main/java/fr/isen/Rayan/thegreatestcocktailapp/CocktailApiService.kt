package fr.isen.Rayan.thegreatestcocktailapp

import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {

    @GET("list.php?c=list")
    suspend fun getCategories(): CategoryResponse

    @GET("filter.php")
    suspend fun getDrinksByCategory(
        @Query("c") category: String
    ): DrinksResponse

    @GET("lookup.php")
    suspend fun getCocktailById(
        @Query("i") id: String
    ): CocktailDetailResponse

    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailDetailResponse
}