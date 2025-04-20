package com.example.gamedrive.api

import com.google.gson.annotations.SerializedName

data class Game(
    @SerializedName("Id") val id: Int?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Description") val description: String?,
    @SerializedName("Image") val image: String?,
    @SerializedName("Categories") val categories: ArrayList<Category>?
    )

data class Category(
    @SerializedName("Id") val id: Int?,
    @SerializedName("Name") val name: String?
)