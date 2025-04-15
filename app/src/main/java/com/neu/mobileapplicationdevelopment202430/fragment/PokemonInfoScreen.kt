package com.neu.mobileapplicationdevelopment202430.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.neu.mobileapplicationdevelopment202430.pokemon.PokemonItem
import com.neu.mobileapplicationdevelopment202430.utils.getTypeColor

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PokemonInfoScreen(pokemon: PokemonItem) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .testTag("pokemon_card_${pokemon.id}"),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column (
            modifier = Modifier
                    .background(Color(0xFFFAFDF3))
                    .testTag("pokemon_card_column_${pokemon.id}"),

            ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .testTag("pokemon_card_image_container_${pokemon.id}"),
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
                        .testTag("pokemon_card_image_${pokemon.id}")
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
                    .testTag("pokemon_card_name_${pokemon.id}")
            )

            Text(
                text = pokemon.species,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp)
                    .testTag("pokemon_card_species_${pokemon.id}")
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
                    .testTag("pokemon_card_types_row_${pokemon.id}"),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .testTag("pokemon_card_types_${pokemon.id}"),
                    text = "Types:",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Black
                )

                Text(
                    modifier = Modifier
                        .testTag("pokemon_card_types_type1_${pokemon.id}"),
                    text = pokemon.type1,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal,
                    color = getTypeColor(pokemon.type1)
                )


                pokemon.type2?.let {
                    Text(
                        modifier = Modifier
                            .testTag("pokemon_card_types_type2_${pokemon.id}"),
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
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .testTag("pokemon_card_abilities_row_${pokemon.id}"),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Text(
                        modifier = Modifier
                            .testTag("pokemon_card_abilities_${pokemon.id}"),
                        text = "Abilities:",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )


                    Text(
                        modifier = Modifier
                            .testTag("pokemon_card_abilities_array_${pokemon.id}"),
                        text = pokemon.abilities,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )

                }

            Text(
                text = pokemon.description,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 16.dp)
                    .testTag("pokemon_card_description_${pokemon.id}"),
            )
        }
    }
}


