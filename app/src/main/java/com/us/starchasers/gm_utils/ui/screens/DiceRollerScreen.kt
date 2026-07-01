package com.us.starchasers.gm_utils.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import com.us.starchasers.gm_utils.R
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.us.starchasers.gm_utils.ui.components.PageScaffold
import com.us.starchasers.gm_utils.ui.theme.DarkBorderColor
import com.us.starchasers.gm_utils.ui.theme.GMUtilsTheme
import com.us.starchasers.gm_utils.ui.theme.LightBorderColor
import kotlinx.collections.immutable.toPersistentList
import com.seo4d696b75.compose.material3.picker.NumberPicker

class DiceRollerScreen: Screen {

    @Composable
    override fun Content() {
        var showDialog by remember { mutableStateOf(false) }
        var diceType by remember { mutableStateOf("") }
        var numberOfDice by remember { mutableIntStateOf(1) }
       

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = "Number of dice: ") },
                text = {
                    // The input field inside the modal
                    NumberPicker(
                        value = numberOfDice,
                        range = (1..20).toPersistentList(),
                        onValueChange = { numberOfDice = it },
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        // Handle the value and close
                        println("User entered: $numberOfDice for $diceType")
                        showDialog = false
                    }) {
                        Text("Roll")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancel")
                    }
                }
            )
        }

        PageScaffold(title = stringResource(R.string.dice_roller)) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Row(modifier = Modifier.weight(5f),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Box(
                        modifier = Modifier.border(
                            width = 3.dp,
                            shape = RoundedCornerShape(10.dp),
                            color = DarkBorderColor
                        )
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.LightGray)
                            .height(640.dp)
                            .width(320.dp)
                            .padding(all = 16.dp)
                    ) {
                        Text("Tray") // dice roll output goes here
                    }
                }
                Row(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            showDialog = true
                            diceType = "attack"
                        }
                    ) {
                        Row() {
                            Icon(
                                painterResource(id = R.drawable.ic_dice_eight_faces),
                                contentDescription = "Attack Dice", // TODO: Fix description
                                modifier = Modifier
                                    .size(96.dp)
                                    .border(
                                        width = 3.dp,
                                        shape = CircleShape,
                                        color = LightBorderColor
                                    )
                                    .align(Alignment.CenterVertically),
                                tint = Color.Unspecified
                            )
                        }
                        Row() {
                            Text(
                                text = stringResource(R.string.attack_roll),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable {
                            showDialog = true
                            diceType = "defense"
                        }
                    ) {
                        Row() {
                            Icon(
                                painterResource(id = R.drawable.ic_dice_six_faces),
                                contentDescription = "Defense Dice", // TODO: Fix description
                                modifier = Modifier
                                    .size(96.dp)
                                    .border(
                                        width = 3.dp,
                                        shape = CircleShape,
                                        color = LightBorderColor
                                    )
                                    .align(Alignment.CenterVertically),
                                tint = Color.Unspecified
                            )
                        }
                        Row() {
                            Text(
                                text = stringResource(R.string.defense_roll),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                                )
                        }
                    }
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