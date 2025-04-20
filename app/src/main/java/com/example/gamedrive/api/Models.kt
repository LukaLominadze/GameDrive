package com.example.gamedrive.api

data class Game(
    val id: Int?,
    val title: String?,
    val description: String?,
    val image: String?,
    val categories: ArrayList<String>?
    )