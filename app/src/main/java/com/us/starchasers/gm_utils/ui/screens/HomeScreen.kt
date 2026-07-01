package com.us.starchasers.gm_utils.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.us.starchasers.gm_utils.R
import com.us.starchasers.gm_utils.data.models.MiniApp
import com.us.starchasers.gm_utils.ui.components.HomeScreenButton
import com.us.starchasers.gm_utils.ui.components.PageScaffold
import com.us.starchasers.gm_utils.ui.theme.GMUtilsTheme

class HomeScreen: Screen {

    @Composable
    override fun Content() {
        val itemsList = listOf<MiniApp>(
            MiniApp(name = stringResource(R.string.dice_roller), icon = R.drawable.ic_rolling_dice, screen = DiceRollerScreen()),
        )
        val state = rememberLazyGridState()
        PageScaffold(title = stringResource(R.string.app_title)) {
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
