package com.example.foodify.database

import androidx.room.Database

@Database (entities = [Entity::class], version = 1)
abstract class RestaurantDatabase {
    abstract fun restaurantDao() : RestaurantDao
}