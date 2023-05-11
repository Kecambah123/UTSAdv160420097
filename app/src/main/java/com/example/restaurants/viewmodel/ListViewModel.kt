package com.example.restaurants.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.restaurants.model.Favorite
import com.example.restaurants.model.Restaurant
import com.example.restaurants.view.Global
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class ListViewModel(application: Application): AndroidViewModel(application) {
    val restaurants = MutableLiveData<ArrayList<Restaurant>>()
    val favorites = MutableLiveData<ArrayList<Favorite>>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh() {

        queue = Volley.newRequestQueue(getApplication())
        val url = "https://wheli.site/adv/restaurant.php"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Restaurant>>() { }.type
                val result = Gson().fromJson<ArrayList<Restaurant>>(it, sType)
                restaurants.value = result

                Log.d("showvoley", result.toString())
            },
            {
                Log.d("showvoley", it.toString())
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    fun favorite(){
        val user_id = Global.user_id
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://wheli.site/adv/myfavorite.php?user_id=$user_id"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            {
                val sType = object : TypeToken<ArrayList<Favorite>>() { }.type
                val result = Gson().fromJson<ArrayList<Favorite>>(it, sType)
                favorites.value = result

                Log.d("showvoley", result.toString())
            },
            {
                Log.d("showvoley", it.toString())
            })
        stringRequest.tag = TAG
        queue?.add(stringRequest)
    }

    override fun onCleared() {
        super.onCleared()
        queue?.cancelAll(TAG)
    }
}