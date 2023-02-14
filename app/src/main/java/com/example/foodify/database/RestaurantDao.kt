package com.example.foodify.database

import androidx.room.*
import com.example.foodify.model.RestaurantListModel

@Dao
interface RestaurantDao {
    @Insert
    fun insertRestaurant(entity: Entity)

    @Delete
    fun deleteRestaurant(entity: Entity)

    @Query("Select * FROM RestaurantEntity")
    fun getAllRestaurant(): List<Entity>

    @Query("Select * FROM RestaurantEntity WHERE :restaurantId = restaurantId")
    fun checkRestaurant(restaurantId: String): Entity
}