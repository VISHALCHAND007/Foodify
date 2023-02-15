package com.example.foodify.model

data class OrderHistoryModel(
    val restaurantName: String,
    val orderedDate: String,
    val listMenuItems: java.util.ArrayList<MenuItemModel>
    )