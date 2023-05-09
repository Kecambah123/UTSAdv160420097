package com.example.restaurants.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.restaurants.model.Drink
import com.example.restaurants.model.Food
import com.example.restaurants.model.Restaurant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DetailViewModel(application: Application): AndroidViewModel(application) {
    val resto = MutableLiveData<Restaurant>()
    val foods = MutableLiveData<ArrayList<Food>>()
    val drinks = MutableLiveData<ArrayList<Drink>>()

    val TAG = "volleytag"
    private var queue: RequestQueue? = null

    fun fetch(id: String){

        queue = Volley.newRequestQueue(getApplication())
        val url = "http://wheli.site/adv/restaurant.php?id=$id"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<Restaurant>() { }.type
                val result = Gson().fromJson<Restaurant>(it, sType)
                resto.value = result

                Log.d("showvolley", result.toString())
            },
            {
                Log.d("showvolley", it.toString())
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun food(id: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://wheli.site/adv/food.php?id=$id"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Food>>() { }.type
                val result = Gson().fromJson<ArrayList<Food>>(it, sType)
                foods.value = result

                Log.d("showvolley", result.toString())
            },
            {
                Log.d("showvolley", it.toString())
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun drink(id: String){
        queue = Volley.newRequestQueue(getApplication())
        val url = "http://wheli.site/adv/drink.php?id=$id"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Drink>>() { }.type
                val result = Gson().fromJson<ArrayList<Drink>>(it, sType)
                drinks.value = result

                Log.d("showvolley", result.toString())
            },
            {
                Log.d("showvolley", it.toString())
            })

        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }
}