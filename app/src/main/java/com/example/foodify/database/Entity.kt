package com.example.foodify.database

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

@androidx.room.Entity(tableName = "RestaurantEntity")
data class Entity(
    @PrimaryKey val restaurantId: String,
    @ColumnInfo (name = "image") val restaurantImg: String,
    @ColumnInfo (name = "name") val restaurantName: String,
    @ColumnInfo (name = "price") val restaurantPrice: String,
    @ColumnInfo (name = "rating") val restaurantRating: String
)