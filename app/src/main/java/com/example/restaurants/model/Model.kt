package com.example.restaurants.model

data class Restaurant(
    val id:String?,
    val resto_name: String?,
    val address:String?,
    val phone_num:String?,
    val resto_img:String,
    val background_img:String,
    val totalrating:String?,
    val countrating:String?
)

data class Food(
    val id:String?,
    val food_name:String?,
    val description:String?,
    val price:String?,
    val food_img:String,
    val restaurant_id:String,
)

data class Drink(
    val id:String?,
    val drink_name:String?,
    val price:String?,
    val drink_img:String,
    val restaurant_id:String,
)

data class Comment(
    val name:String?,
    val comment:String?,
)

data class CommentNum(
    val comment_num:String,
)