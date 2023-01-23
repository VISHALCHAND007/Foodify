package com.example.foodify.utilities

class ApiUrl {
    val HOST_URL = "http://13.235.250.119/v2/"
    val LOGIN = HOST_URL + "login/fetch_result/"
    val REGISTER_USER = HOST_URL + "register/fetch_user/"
    val FORGOT_PASSWORD_SEND_OTP = HOST_URL + "forgot_password/fetch_result/"
    val VERIFY_OTP = HOST_URL + "reset_password/fetch_result/"
    val FETCH_RESTAURANTS = HOST_URL + "restaurants/fetch_result/"
    val RESTAURANT_DETAILS = HOST_URL + "restaurants/fetch_result/"
    val PLACE_ORDER = HOST_URL + "place_order_fetch_result/"
    val ORDER_HISTORY = HOST_URL + "orders/fetch_result/"
}