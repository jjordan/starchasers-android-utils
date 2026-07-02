package com.us.starchasers.gm_utils.util

import java.util.Locale

object Tools {

    fun capitalize(string: String): String {
        val result = string.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.ROOT
            ) else it.toString()
        }
        return result
    }
}