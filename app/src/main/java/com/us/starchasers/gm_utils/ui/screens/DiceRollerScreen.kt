package com.us.starchasers.gm_utils.ui.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.us.starchasers.gm_utils.ui.components.PageScaffold
import com.us.starchasers.gm_utils.ui.theme.GMUtilsTheme

class DiceRollerScreen: Screen {

    @Composable
    override fun Content() {
        PageScaffold(title = "Dice Roller") {
            Text("Dice Roller Content", fontSize = 16.sp)
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