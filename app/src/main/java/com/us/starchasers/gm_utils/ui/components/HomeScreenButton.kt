package com.us.starchasers.gm_utils.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.us.starchasers.gm_utils.R
import com.us.starchasers.gm_utils.ui.screens.HomeScreen
import com.us.starchasers.gm_utils.ui.theme.DarkBorderColor
import com.us.starchasers.gm_utils.ui.theme.GMUtilsTheme
import timber.log.Timber

const val TAG = "Starchasers GM Utils"
@Composable
fun HomeScreenButton(name: String, icon: Int, subscreen: Screen, modifier: Modifier = Modifier) {
    val navigator = LocalNavigator.currentOrThrow
    Box(
        modifier = modifier
            .size(120.dp)
            .padding(6.dp)
            .background(MaterialTheme.colorScheme.background)
            .clip(RoundedCornerShape(10.dp)) // TODO: Figure out how to dynamically change border colors:
            .border(width = 2.dp, color = DarkBorderColor, shape = RoundedCornerShape(size = 10.dp))
            .shadow(3.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                Timber.tag(TAG).d("HomeScreenButton: clicked button ${subscreen.key}")
                navigator.push(subscreen)
            }
        ,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Text(
                    text = name,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                ) {
                Icon(
                    painterResource(id = icon),
                    contentDescription = "Rollable Dice", // TODO: Fix description
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.CenterVertically),
                    tint = Color.Unspecified
                )
            }
        }
    }

}

@Preview(showBackground = true, name = "5-inch Device Portrait", widthDp = 360, heightDp = 640)
@Composable
fun PreviewHomeScreenButton (modifier: Modifier = Modifier) {
    GMUtilsTheme {
        Column(
            modifier = modifier
                .fillMaxSize()
        ) {
            Row() {
                HomeScreenButton(
                    name = "Dice Roller",
                    icon = R.drawable.ic_rolling_dice,
                    subscreen = HomeScreen(),
                    modifier = modifier
                )
                HomeScreenButton(
                    name = "NPC Generator",
                    icon = R.drawable.ic_character_generator,
                    subscreen = HomeScreen(),
                    modifier = modifier
                )
                HomeScreenButton(
                    name = "NPC List",
                    icon = R.drawable.ic_minions,
                    subscreen = HomeScreen(),
                    modifier = modifier
                )
            }
        }
    }
}
