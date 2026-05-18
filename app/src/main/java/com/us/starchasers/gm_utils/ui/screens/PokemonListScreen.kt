package com.us.starchasers.gm_utils.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.us.starchasers.gm_utils.R

class PokemonListScreen: Screen {

    @Composable
    override fun Content() {
        PokemonList()
    }


    @Composable
    fun PokemonList() {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            Column {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.ic_international_pok_mon_logo),
                    contentDescription = "Pokemon Logo",
                    Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                )
                SearchBar(
                    hint = "Search...",
                    modifier = Modifier.fillMaxWidth().padding(16.dp)
                ) {
                    // do nothing yet
                }
            }
        }
    }


    @Composable
    fun SearchBar(
        modifier: Modifier = Modifier,
        hint: String = "",
        onSearch: (String) -> Unit = {}
    ) {
        var text by remember {
            mutableStateOf("")
        }
        var isHintDisplayed by remember {
            mutableStateOf( hint != "")
        }
        Box(modifier = modifier) {
            BasicTextField(
                value = text,
                onValueChange = {
                    text = it
                    onSearch(text)
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(color = Color.Black),
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(5.dp, CircleShape)
                    .background(Color.White, CircleShape)
                    .padding(horizontal = 20.dp, vertical = 12.dp)
                    .onFocusChanged() { focusState ->
                        isHintDisplayed = !focusState.isFocused
                    }
            )
            if(isHintDisplayed) {
                Text(text = hint,
                    color = Color.LightGray,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 12.dp)

                )
            }
        }
    }
}