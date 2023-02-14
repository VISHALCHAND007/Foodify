package com.example.foodify.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RestaurantDao {
    @Insert
    fun insertRestaurant(entity: androidx.room.Entity)

    @Delete
    fun deleteRestaurant(entity: androidx.room.Entity)

    @Query ("Select * FROM RestaurantEntity")
    fun getAllRestaurant() : List<Entity>

    @Query ("Select * FROM RestaurantEntity WHERE :restaurantId = restaurantId")
    fun checkRestaurant(restaurantId: String) : Entity
}