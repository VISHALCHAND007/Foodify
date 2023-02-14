package com.example.foodify.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Entity::class], exportSchema = false, version = 1)
abstract class RestaurantDatabase : RoomDatabase() {
    abstract fun restaurantDao(): RestaurantDao
}