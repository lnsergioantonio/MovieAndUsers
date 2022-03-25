package com.example.movieandusers.ext

fun String.isPhone(): Boolean {
    val p = "^(\\d{10})|(([\\(]?([0-9]{3})[\\)]?)?[ \\.\\-]?([0-9]{3})[ \\.\\-]([0-9]{4}))\$".toRegex()
    return matches(p)
}

/**
 * Extension method to check if String is Email.
 */
fun String.isEmail(): Boolean {
    val p = "^(\\w)+(\\.\\w+)*@(\\w)+((\\.\\w+)+)\$".toRegex()
    return matches(p)
}