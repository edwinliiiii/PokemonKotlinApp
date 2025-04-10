package com.neu.mobileapplicationdevelopment202430.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import com.neu.mobileapplicationdevelopment202430.utils.getTypeColor

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokemonInfoScreen(pokemon: PokemonItem, navController: NavController) {
    Card (
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column (
            modifier = Modifier.background(Color(0xFFFAFDF3))
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                onClick = { navController.popBackStack() }) {
                Text(
                    text = "Back",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                )
            }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                GlideImage(
                    model = pokemon.sprite,
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(16.dp))
                )
            }

            Text(
                text = pokemon.name,
                fontSize = 32.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Types:",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    text = pokemon.type1,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = getTypeColor(pokemon.type1)
                )


                pokemon.type2?.let {
                    Text(
                        text = pokemon.type2,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = getTypeColor(pokemon.type2)
                    )
                }

            }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        text = "Abilities:",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )


                    Text(
                        text = pokemon.abilities,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )

                }

            Text(
                text = pokemon.species,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp)
            )

            Text(
                text = pokemon.description,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp)
            )
        }
    }
}


