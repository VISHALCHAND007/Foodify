package com.example.foodify.model

data class RestaurantListModel(
    val restaurantId: String,
    val restaurantName: String,
    val restaurantRating: String,
    val costForOne: String,
    val restaurantImg: String
)