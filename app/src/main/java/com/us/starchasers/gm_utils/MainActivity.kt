package com.us.starchasers.gm_utils

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import com.us.starchasers.gm_utils.ui.screens.HomeScreen
import com.us.starchasers.gm_utils.ui.screens.PokemonListScreen
import com.us.starchasers.gm_utils.ui.theme.GMUtilsTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GMUtilsTheme {
                Navigator(screen = PokemonListScreen())
                /*
                                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                                    Greeting(
                                        name = "Android",
                                        modifier = Modifier.padding(innerPadding)
                                    )
                                }
                */
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GMUtilsTheme {
        Greeting("Android")
    }
}