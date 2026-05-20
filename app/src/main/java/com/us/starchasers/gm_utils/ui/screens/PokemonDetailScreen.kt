package com.us.starchasers.gm_utils.ui.screens

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.us.starchasers.gm_utils.R
import com.us.starchasers.gm_utils.data.models.PokemonDetailViewModel
import com.us.starchasers.gm_utils.data.remote.responses.Pokemon
import com.us.starchasers.gm_utils.data.remote.responses.Type
import com.us.starchasers.gm_utils.util.Resource
import com.us.starchasers.gm_utils.util.Tools.capitalize
import com.us.starchasers.gm_utils.util.Tools.parseTypeToColor
import com.us.starchasers.gm_utils.util.Tools.parseStatToAbbr
import com.us.starchasers.gm_utils.util.Tools.parseStatToColor
import kotlin.math.round

data class PokemonDetailScreen(val color: Int, val name: String): Screen {
    @Composable
    override fun Content() {
        PokemonDetailHeader(Color(color), name)
    }

    @Composable
    fun PokemonDetailHeader(
        dominantColor: Color,
        pokemonName: String,
        topPadding: Dp = 20.dp,
        pokemonImageSize: Dp = 200.dp,
        modifier: Modifier = Modifier,
        viewModel: PokemonDetailViewModel = hiltViewModel()
    ) {
        val pokemonInfo = produceState(initialValue = Resource.Loading()) {
            value = viewModel.getPokemonInfo(pokemonName)
        }.value
        Box(modifier = Modifier
            .fillMaxSize()
            .background(dominantColor)
            .padding(bottom = 16.dp)
        ) {
            PokemonDetailTopSection(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .align(Alignment.TopCenter)
            )
            PokemonDetailStateWrapper(
                pokemonInfo = pokemonInfo,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = topPadding + pokemonImageSize / 2f,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
                    .shadow(10.dp, RoundedCornerShape(10.dp))
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
                    .align(Alignment.BottomCenter),
                loadingModifier = Modifier
                    .size(100.dp)
                    .align(Alignment.Center)
                    .padding(
                        top = topPadding + pokemonImageSize / 2f,
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            )
            Box(
                contentAlignment = Alignment.TopCenter,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                if(pokemonInfo is Resource.Success) {
                    pokemonInfo.data?.sprites?.let {
                        val request = ImageRequest.Builder(LocalContext.current)
                            .data(it.front_default)
                            .crossfade(true)
                            .build()
                        SubcomposeAsyncImage(
                            model = request,
                            contentDescription = pokemonInfo.data.name,
                            modifier = Modifier
                                .size(pokemonImageSize)
                                .offset(y = topPadding),
                        )

                    }
                }

            }
        }
    }

    @Composable
    fun PokemonDetailTopSection (modifier: Modifier = Modifier) {
        val navigator = LocalNavigator.currentOrThrow

        Box(
            contentAlignment = Alignment.TopStart,
            modifier = modifier
                .background(
                    Brush.verticalGradient(
                        listOf<Color>(
                            Color.Black,
                            Color.Transparent
                        )
                    )
                )
                .height(30.dp)
            ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "back arrow",
                tint = Color.White,
                modifier = Modifier
                    .size(36.dp)
                    .offset(16.dp, 16.dp)
                    .clickable {
                        navigator.pop() // pop back to the list
                    }
            )
        }
    }

    @Composable
    fun PokemonDetailStateWrapper (
        pokemonInfo: Resource<Pokemon>,
        modifier: Modifier = Modifier,
        loadingModifier: Modifier = Modifier,
    ) {
        when(pokemonInfo) {
            is Resource.Success -> {
                PokemonDetailSection(
                    pokemonInfo = pokemonInfo.data!!,
                    modifier = modifier
                        .offset(y = (-20).dp)
                )
            }
            is Resource.Error -> {
                Text(
                    text = pokemonInfo.message!!,
                    color = Color.Red,
                    modifier = modifier
                )
            }
            is Resource.Loading -> {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.primary,
                    modifier = loadingModifier
                )
            }
        }
    }

    @Composable
    fun PokemonDetailSection(
        pokemonInfo: Pokemon,
        modifier: Modifier = Modifier
    ) {
        val scrollState = rememberScrollState()
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .offset(y = 100.dp)
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "#${pokemonInfo.id} ${capitalize(pokemonInfo.name)}",
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )
            PokemonTypeSection(types = pokemonInfo.types)
            PokemonDetailDataSection(
                pokemonWeight = pokemonInfo.weight,
                pokemonHeight = pokemonInfo.height,
                )
            PokemonBaseStats(pokemonInfo)
        }
    }

    @Composable
    fun PokemonTypeSection(
        types: List<Type>,
        modifier: Modifier = Modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            for(type in types) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                        .clip(CircleShape)
                        .background(parseTypeToColor(type))
                        .height(35.dp)
                ) {
                    Text(
                        text = capitalize( type.type.name),
                        color = Color.White,
                        fontSize = 18.sp,
                        )
                }
            }
        }
    }

    @Composable
    fun PokemonDetailDataSection(
        pokemonWeight: Int,
        pokemonHeight: Int,
        sectionHeight: Dp = 80.dp,
        modifier: Modifier = Modifier
    ) {
        val pokemonWeightInKg = remember {
            round(pokemonWeight * 100f) / 1000f
        }
        val pokemonHeightInM = remember {
            round(pokemonHeight * 100f) / 1000f
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            PokemonDetailDataItem(
                dataValue =  pokemonWeightInKg,
                dataUnit = "kg",
                dataDescription = "Pokemon Weight",
                dataIcon = painterResource(id = R.drawable.ic_weight),
                modifier = Modifier.weight(1f)
            )
            Spacer(
                modifier = Modifier
                    .size(1.dp, sectionHeight)
                    .background(Color.LightGray)
            )
            PokemonDetailDataItem(
                dataValue =  pokemonHeightInM,
                dataUnit = "m",
                dataDescription = "Pokemon Height",
                dataIcon = painterResource(id = R.drawable.ic_height),
                modifier = Modifier.weight(1f)
            )
        }
    }


    @Composable
    fun PokemonDetailDataItem(
        dataValue: Float,
        dataUnit: String,
        dataIcon: Painter,
        dataDescription: String,
        modifier: Modifier = Modifier
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
        ) {
            Icon(painter = dataIcon, contentDescription = dataDescription, tint = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$dataValue$dataUnit",
                color = MaterialTheme.colorScheme.onSurface
                )
        }
    }

    @Composable
    fun PokemonStat(
        statName: String,
        statValue: Int,
        statMaxValue: Int,
        statColor: Color,
        height: Dp = 28.dp,
        animDuration: Int = 1000,
        animDelay: Int = 0,
        modifier: Modifier = Modifier
    ) {
        var animationPlayed by remember {
            mutableStateOf(false)
        }
        val currentPercent = animateFloatAsState(
            targetValue = if (animationPlayed) {
                statValue / statMaxValue.toFloat()
            } else {
                0f
            },
            animationSpec = tween(animDuration, animDelay)
        )
        LaunchedEffect(key1 = true) {
            animationPlayed = true
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
                .clip(CircleShape)
                .background(
                    if (isSystemInDarkTheme()) {
                        Color(0xFF505050)
                    } else {
                        Color.LightGray
                    }
                )
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(currentPercent.value)
                    .clip(CircleShape)
                    .background(statColor)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = statName,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = (currentPercent.value * statMaxValue).toInt().toString(),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    @Composable
    fun PokemonBaseStats(
        pokemonInfo: Pokemon,
        animDelayPerItem: Int = 100,
        modifier: Modifier = Modifier
    ) {
        val maxBaseStat = remember {
            pokemonInfo.stats.maxOf { it.base_stat }
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) { 
            Text(
                text = "Base Stats:",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            
            for(i in pokemonInfo.stats.indices) {
                val stat = pokemonInfo.stats[i]
                PokemonStat(
                    statName = parseStatToAbbr(stat),
                    statValue = stat.base_stat,
                    statMaxValue = maxBaseStat,
                    statColor = parseStatToColor(stat),
                    animDelay = i * animDelayPerItem,
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}