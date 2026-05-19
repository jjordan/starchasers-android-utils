package com.us.starchasers.gm_utils.ui.screens

import android.content.res.Resources
import android.graphics.drawable.BitmapDrawable
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toDrawable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.toBitmap
import coil3.util.CoilUtils
import com.us.starchasers.gm_utils.R
import com.us.starchasers.gm_utils.data.models.PokedexListEntry
import com.us.starchasers.gm_utils.data.models.PokemonListViewModel
import com.us.starchasers.gm_utils.ui.theme.RobotoFontFamily
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.asDrawable
import coil3.request.SuccessResult

class PokemonListScreen: Screen {

    @Composable
    override fun Content() {
        PokemonHeader()
    }


    @Composable
    fun PokemonHeader(
        viewModel: PokemonListViewModel = hiltViewModel(),
        ) {
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
                    viewModel.searchPokemonList(it)
                }
                Spacer(modifier = Modifier.height(16.dp))
                PokemonList()
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
                        isHintDisplayed = !focusState.isFocused && text.isNotEmpty()
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


    @Composable
    fun PokemonList(
        viewModel: PokemonListViewModel = hiltViewModel(),
    ) {
        val pokemonList by remember { viewModel.pokemonList }
        val endReached by remember { viewModel.endReached }
        val loadError by remember { viewModel.loadError }
        val isLoading by remember { viewModel.isLoading }
        val isSearching by remember { viewModel.isSearching }
        println("in PokemonList")
        LazyColumn(contentPadding = PaddingValues(16.dp)) {
            val itemCount = if(pokemonList.size % 2 == 0) {
                pokemonList.size / 2
            } else {
                pokemonList.size / 2 + 1
            }
            items(itemCount) {
                if (it >= itemCount - 1 && !endReached && !isLoading && !isSearching) {
                    viewModel.loadPokemonPaginated()
                }
                PokedexRow(it, pokemonList)
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()) {
            if(isLoading) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            if(loadError.isNotEmpty()) {
                RetrySection(error = loadError) {
                    viewModel.loadPokemonPaginated()
                }
            }
        }
    }


    @Composable
    fun PokedexEntry(
        entry: PokedexListEntry, // need to do navigation to specific pokemon
        modifier: Modifier = Modifier,
        viewModel: PokemonListViewModel = hiltViewModel(), // how to pass through hilt?
    ) {
        val defaultDominantColor = MaterialTheme.colorScheme.surface
        var dominantColor by remember {
            mutableStateOf(defaultDominantColor)
        }
        val navigator = LocalNavigator.currentOrThrow

        Box(
            contentAlignment = Alignment.Center,
            modifier = modifier
                .shadow(5.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .aspectRatio(1f)
                .background(
                    Brush.verticalGradient(
                        listOf<Color>(
                            dominantColor,
                            defaultDominantColor
                        )
                    )
                )
                .clickable {
                    // navigate via Voyager to detail screen
                    // pass dominant color and pokemon name
                    navigator.push(
                        PokemonDetailScreen(
                            color = dominantColor.toArgb(),
                            name = entry.pokemonName
                        )
                    )
                }
        ) {
            Column {
                // Coil library?
                val request = ImageRequest.Builder(LocalContext.current)
                    .data(entry.imageURL)
                    .build()
                SubcomposeAsyncImage(
                    model = request,
                    contentDescription = "pokemon ${entry.pokemonName}'s image",
                    onSuccess = { success ->
                            viewModel.calcDominantColor(
                                success.result.image.asDrawable( resources = Resources.getSystem())
                            ) { color ->
                                dominantColor = color
                            }
                    },
                    modifier = Modifier.size(120.dp)
                        .align(Alignment.CenterHorizontally),
                    loading = {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.scale(0.5f)
                        )
                    }
                )
                Text(
                    text = entry.pokemonName,
                    fontFamily = RobotoFontFamily,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

    @Composable
    fun PokedexRow(
        rowIndex: Int,
        entries: List<PokedexListEntry>,
    ) {
        Column {
            Row {
                PokedexEntry(entries[rowIndex * 2], modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width( 16.dp ))
                if (entries.size >= rowIndex * 2 + 2) {
                    PokedexEntry(entries[rowIndex * 2 + 1], modifier = Modifier.weight(1f))
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

    @Composable
    fun RetrySection(
        error: String,
        onRetry: () -> Unit,
    ) {
        Column() {
            Text(error, color = Color.Red, fontSize = 18.sp, textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = {
                    onRetry()
                },
                modifier = Modifier.align(Alignment.CenterHorizontally )
            ) {
                Text("Retry")
            }
        }
    }
}