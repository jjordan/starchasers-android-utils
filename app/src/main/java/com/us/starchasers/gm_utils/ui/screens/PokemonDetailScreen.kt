package com.us.starchasers.gm_utils.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

data class PokemonDetailScreen(val color: Int, val name: String): Screen {
    @Composable
    override fun Content() {
        Text("Pokemon List")
    }

}