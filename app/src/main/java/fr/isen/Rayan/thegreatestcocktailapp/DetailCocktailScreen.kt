package fr.isen.Rayan.thegreatestcocktailapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailCocktailScreen() {

    val gradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF7F00FF), // violet
            Color(0xFFE100FF)  // rose
        )
    )

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(gradient)
                .padding(padding)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(60.dp))

                // ===== IMAGE CERCLE =====
                Image(
                    painter = painterResource(R.drawable.legmi_cocktail),
                    contentDescription = "Cocktail",
                    modifier = Modifier
                        .size(250.dp)
                        .clip(CircleShape)
                        .background(Color.White)
                        .padding(6.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // ===== CARD CONTENU =====
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Legmi",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )

                        Text("Cocktail • Alcoholic")
                        Text("Glass: Highball glass")

                        Divider()

                        Text("Ingredients", fontWeight = FontWeight.Bold)
                        Text("• na5la\n• alcool\n• ras kalb\n• kamya")

                        Divider()

                        Text("Recipe", fontWeight = FontWeight.Bold)
                        Text("echiii555aaa")
                    }
                }
            }
        }
    }
}
