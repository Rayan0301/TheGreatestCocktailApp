package fr.isen.Rayan.thegreatestcocktailapp

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import fr.isen.Rayan.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

data class BottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            TheGreatestCocktailAppTheme {
                val navController = rememberNavController()

                val items = listOf(
                    BottomNavItem("categories", "Categories", Icons.Default.List),
                    BottomNavItem("favorites", "Favorites", Icons.Default.Favorite),
                    BottomNavItem("random", "Random", Icons.Default.Shuffle)
                )

                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                Scaffold(
                    bottomBar = {
                        NavigationBar {
                            items.forEach { item ->
                                NavigationBarItem(
                                    selected = currentRoute == item.route,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo("categories") { inclusive = false }
                                            launchSingleTop = true
                                        }
                                    },
                                    icon = {
                                        Icon(
                                            imageVector = item.icon,
                                            contentDescription = item.label
                                        )
                                    },
                                    label = { Text(item.label) }
                                )
                            }
                        }
                    }
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        startDestination = "categories",
                        modifier = Modifier.padding(paddingValues)
                    ) {
                        composable("categories") {
                            CategoriesScreen(
                                onCategoryClick = { category ->
                                    navController.navigate("cocktail_list/${Uri.encode(category)}")
                                }
                            )
                        }

                        composable(
                            route = "cocktail_list/{category}",
                            arguments = listOf(
                                navArgument("category") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val category =
                                backStackEntry.arguments?.getString("category") ?: "Cocktail"

                            CocktailListScreen(
                                categoryName = category,
                                onCocktailClick = { cocktailId ->
                                    navController.navigate("detail/${Uri.encode(cocktailId)}")
                                }
                            )
                        }

                        composable(
                            route = "detail/{cocktailId}",
                            arguments = listOf(
                                navArgument("cocktailId") { type = NavType.StringType }
                            )
                        ) { backStackEntry ->
                            val cocktailId =
                                backStackEntry.arguments?.getString("cocktailId") ?: ""

                            DetailCocktailScreen(cocktailId = cocktailId)
                        }

                        composable("favorites") {
                            FavoritesScreen(
                                onCocktailClick = { cocktailId ->
                                    navController.navigate("detail/${Uri.encode(cocktailId)}")
                                }
                            )
                        }

                        composable("random") {
                            RandomCocktailScreen()
                        }
                    }
                }
            }
        }
    }
}