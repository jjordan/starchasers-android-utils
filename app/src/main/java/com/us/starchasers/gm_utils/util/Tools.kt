package com.us.starchasers.gm_utils.util

import androidx.compose.ui.graphics.Color
import com.us.starchasers.gm_utils.data.remote.responses.Type
import com.us.starchasers.gm_utils.data.remote.responses.StatX
import com.us.starchasers.gm_utils.data.remote.responses.StatXX
import com.us.starchasers.gm_utils.ui.theme.AtkColor
import com.us.starchasers.gm_utils.ui.theme.DefColor
import com.us.starchasers.gm_utils.ui.theme.HPColor
import com.us.starchasers.gm_utils.ui.theme.SpAtkColor
import com.us.starchasers.gm_utils.ui.theme.SpDefColor
import com.us.starchasers.gm_utils.ui.theme.SpdColor
import com.us.starchasers.gm_utils.ui.theme.TypeBug
import com.us.starchasers.gm_utils.ui.theme.TypeDark
import com.us.starchasers.gm_utils.ui.theme.TypeDragon
import com.us.starchasers.gm_utils.ui.theme.TypeElectric
import com.us.starchasers.gm_utils.ui.theme.TypeFairy
import com.us.starchasers.gm_utils.ui.theme.TypeFighting
import com.us.starchasers.gm_utils.ui.theme.TypeFire
import com.us.starchasers.gm_utils.ui.theme.TypeFlying
import com.us.starchasers.gm_utils.ui.theme.TypeGhost
import com.us.starchasers.gm_utils.ui.theme.TypeGrass
import com.us.starchasers.gm_utils.ui.theme.TypeGround
import com.us.starchasers.gm_utils.ui.theme.TypeIce
import com.us.starchasers.gm_utils.ui.theme.TypeNormal
import com.us.starchasers.gm_utils.ui.theme.TypePoison
import com.us.starchasers.gm_utils.ui.theme.TypePsychic
import com.us.starchasers.gm_utils.ui.theme.TypeRock
import com.us.starchasers.gm_utils.ui.theme.TypeSteel
import com.us.starchasers.gm_utils.ui.theme.TypeWater
import java.util.Locale
import java.util.Locale.getDefault

object Tools {


    fun parseTypeToColor(type: Type): Color {
        return when(type.type.name.lowercase(Locale.ROOT)) {
            "normal" -> TypeNormal
            "fire" -> TypeFire
            "water" -> TypeWater
            "electric" -> TypeElectric
            "grass" -> TypeGrass
            "ice" -> TypeIce
            "fighting" -> TypeFighting
            "poison" -> TypePoison
            "ground" -> TypeGround
            "flying" -> TypeFlying
            "psychic" -> TypePsychic
            "bug" -> TypeBug
            "rock" -> TypeRock
            "ghost" -> TypeGhost
            "dragon" -> TypeDragon
            "dark" -> TypeDark
            "steel" -> TypeSteel
            "fairy" -> TypeFairy
            else -> Color.Black
        }
    }

    fun parseStatToColor(stat: StatXX): Color {
        return when(stat.stat.name.lowercase(getDefault())) {
            "hp" -> HPColor
            "attack" -> AtkColor
            "defense" -> DefColor
            "special-attack" -> SpAtkColor
            "special-defense" -> SpDefColor
            "speed" -> SpdColor
            else -> Color.White
        }
    }

    fun parseStatToAbbr(stat: StatXX): String {
        return when(stat.stat.name.lowercase(getDefault())) {
            "hp" -> "HP"
            "attack" -> "Atk"
            "defense" -> "Def"
            "special-attack" -> "SpAtk"
            "special-defense" -> "SpDef"
            "speed" -> "Spd"
            else -> ""
        }
    }

    fun capitalize(string: String): String {
        val result = string.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        return result
    }
}