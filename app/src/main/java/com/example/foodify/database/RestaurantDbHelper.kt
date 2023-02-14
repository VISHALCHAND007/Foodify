package com.example.foodify.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Room
import com.example.foodify.model.RestaurantListModel
import com.example.foodify.utils.Constants

class RestaurantDbHelper(private val mContext: Context, private val entity: Entity, private val mode: Int) : AsyncTask<Void, Void, Boolean>() {

    val INSERT = 1
    val DELETE = 2
    val CHECK_EXIST = 3
    val GET_ALL_RESTAURANTS = 4

    @Deprecated("Deprecated in Java")
    override fun doInBackground(vararg params: Void?): Boolean {
        val db = Room.databaseBuilder(mContext, RestaurantDatabase::class.java, Constants().DATABASE_NAME)
            .build()

        when(mode) {
            1 -> {
                //insert
                db.restaurantDao().insertRestaurant(entity)
                return true
            }
            2 -> {
                //delete
                db.restaurantDao().deleteRestaurant(entity)
                return true
            }
            3 -> {
                //check exist
                val entity = db.restaurantDao().checkRestaurant(entity.restaurantId)
                return entity != null
            }
            4 -> {
                //get all restaurants
                val restaurantList = db.restaurantDao().getAllRestaurant()
                return restaurantList != null
            }
        }

        return false
    }
    class FetchProducts (private val mContext: Context): AsyncTask<Void, Void, List<Entity>>() {
        override fun doInBackground(vararg params: Void?): List<Entity> {
            val db = Room.databaseBuilder(mContext, RestaurantDatabase::class.java, Constants().DATABASE_NAME).build()

            return db.restaurantDao().getAllRestaurant()

        }

    }
}