package fr.isen.Rayan.thegreatestcocktailapp

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RandomCocktailScreen() {
    val context = LocalContext.current

    var cocktail by remember { mutableStateOf<CocktailDetail?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            isLoading = true
            errorMessage = null
            val response = NetworkManager.api.getRandomCocktail()
            cocktail = response.drinks.firstOrNull()
            isFavorite = cocktail?.idDrink?.let { FavoritesManager.isFavorite(context, it) } ?: false
            isLoading = false
        } catch (e: Exception) {
            errorMessage = e.message ?: "Unknown error"
            isLoading = false
        }
    }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF7F00FF),
            Color(0xFFE100FF)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Random Cocktail",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(
                        onClick = {
                            val currentCocktail = cocktail
                            if (currentCocktail != null) {
                                val drinkItem = DrinkItem(
                                    idDrink = currentCocktail.idDrink,
                                    strDrink = currentCocktail.strDrink,
                                    strDrinkThumb = currentCocktail.strDrinkThumb
                                )

                                if (isFavorite) {
                                    FavoritesManager.removeFavorite(context, currentCocktail.idDrink)
                                    isFavorite = false
                                    Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
                                } else {
                                    FavoritesManager.addFavorite(context, drinkItem)
                                    isFavorite = true
                                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(padding)
        ) {
            when {
                isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color.White)
                    }
                }

                errorMessage != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Error: $errorMessage", color = Color.White)
                    }
                }

                cocktail == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("No cocktail found", color = Color.White)
                    }
                }

                else -> {
                    val ingredients = listOf(
                        cocktail?.strIngredient1,
                        cocktail?.strIngredient2,
                        cocktail?.strIngredient3,
                        cocktail?.strIngredient4,
                        cocktail?.strIngredient5
                    ).filter { !it.isNullOrBlank() }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(20.dp))

                        AsyncImage(
                            model = cocktail?.strDrinkThumb,
                            contentDescription = cocktail?.strDrink,
                            modifier = Modifier
                                .size(240.dp)
                                .clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.height(20.dp))

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(24.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White)
                        ) {
                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                Text(
                                    text = cocktail?.strDrink ?: "Unknown cocktail",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = "${cocktail?.strCategory ?: "Unknown"} • ${cocktail?.strAlcoholic ?: "Unknown"}",
                                    style = MaterialTheme.typography.bodyLarge
                                )

                                Text(
                                    text = "Glass: ${cocktail?.strGlass ?: "Unknown"}",
                                    style = MaterialTheme.typography.bodyMedium
                                )

                                Divider()

                                Text(
                                    text = "Ingredients",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                ingredients.forEach { ingredient ->
                                    Text("• $ingredient")
                                }

                                Divider()

                                Text(
                                    text = "Recipe",
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.titleMedium
                                )

                                Text(cocktail?.strInstructions ?: "No instructions available.")
                            }
                        }

                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }
        }
    }
}