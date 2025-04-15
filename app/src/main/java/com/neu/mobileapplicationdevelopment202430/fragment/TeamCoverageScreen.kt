package com.neu.mobileapplicationdevelopment202430.fragment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.neu.mobileapplicationdevelopment202430.R
import com.neu.mobileapplicationdevelopment202430.pokemon.TeamViewModel
import com.neu.mobileapplicationdevelopment202430.pokemon.TeamViewModelFactory
import com.neu.mobileapplicationdevelopment202430.room.PokemonDatabase
import com.neu.mobileapplicationdevelopment202430.utils.getTypeColor
import com.neu.mobileapplicationdevelopment202430.utils.typeColors

@Composable
fun TeamCoverageScreen() {
    val context = LocalContext.current
    val db = PokemonDatabase.getDatabase(context)
    val dao = db.teamPokemonDao()
    val factory = TeamViewModelFactory(dao)
    val viewModel: TeamViewModel = viewModel(factory = factory)
    val team by viewModel.team.collectAsState(initial = emptyList())

    val allTypes = typeColors.keys.toList()

    val ownedTypes = team.flatMap { listOf(it.type1, it.type2 ?: "") }
        .filter { it.isNotBlank() }
        .map { it.lowercase() }
        .toSet()

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp)
                .testTag("coverageHeaders"),
        ) {
            Text(
                text = "My Team Types",
                modifier = Modifier
                    .weight(1f)
                    .testTag("myTeamTypesTitle"),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Text(
                text = "All Types",
                modifier = Modifier
                    .weight(1f)
                    .testTag("allTypesTitle"),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }

        LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .testTag("coverageList"),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
            items(allTypes.size) { index ->
                val type = allTypes[index]
                val typeKey = type.lowercase()
                val hasType = typeKey in ownedTypes

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("coverageRow"),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(
                                color = if (hasType) getTypeColor(typeKey) else Color.LightGray,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .testTag("teamBox_$typeKey"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (hasType) type.replaceFirstChar { it.uppercase() } else "Missing",
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(40.dp)
                            .background(
                                color = getTypeColor(typeKey),
                                shape = RoundedCornerShape(6.dp)
                            )
                            .testTag("referenceBox_$typeKey"),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = type.replaceFirstChar { it.uppercase() },
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}
