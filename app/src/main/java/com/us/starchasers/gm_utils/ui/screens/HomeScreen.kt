package com.us.starchasers.gm_utils.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.us.starchasers.gm_utils.R
import com.us.starchasers.gm_utils.data.models.MiniApp
import com.us.starchasers.gm_utils.ui.components.HomeScreenButton
import com.us.starchasers.gm_utils.ui.components.PageScaffold
import com.us.starchasers.gm_utils.ui.theme.GMUtilsTheme
import com.us.starchasers.gm_utils.ui.theme.LightBorderColor

class HomeScreen: Screen {

    @Composable
    override fun Content() {
        val itemsList = listOf<MiniApp>(
            MiniApp(name = "Dice Roller", icon = R.drawable.ic_rolling_dice, screen = DiceRollerScreen()),
        )
        val state = rememberLazyGridState()
        PageScaffold(title = "Starchasers GM Tools") {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(120.dp),
                state = state,
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(count = itemsList.size) { index ->
                    val app = itemsList[index]
                    HomeScreenButton(
                        name = app.name,
                        icon = app.icon,
                        subscreen = app.screen
                    )
                }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun PreviewContent (modifier: Modifier = Modifier) {
        GMUtilsTheme {
            Content()
        }
    }

}
