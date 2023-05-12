package com.example.restaurants.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.restaurants.model.Favorite
import com.example.restaurants.model.Restaurant
import com.example.restaurants.view.Global
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class ListViewModel(application: Application): AndroidViewModel(application) {
    val restaurants = MutableLiveData<ArrayList<Restaurant>>()
    val favorites = MutableLiveData<ArrayList<Favorite>>()

    val TAG = "volleyTag"
    private var queue: RequestQueue? = null

    fun refresh(){
        queue = Volley.newRequestQueue(getApplication())
        val url = "https://wheli.site/adv/restaurant.json"

        val stringRequest = JsonObjectRequest(
            Request.Method.GET, url, null, Response.Listener
            {response ->
                val jsonArray = response.getJSONArray("Restaurant")
                val restoList = ArrayList<Restaurant>()

                for (i in 0 until jsonArray.length()) {
                    val restoObject = jsonArray.getJSONObject(i)

                    val resto =
                        Restaurant(
                            restoObject.getString("id"),
                            restoObject.getString("resto_name"),
                            restoObject.getString("address"),
                            restoObject.getString("phone_num"),
                            restoObject.getString("resto_img"),
                            restoObject.getString("background_img"),
                            restoObject.getString("totalrating"),
                            restoObject.getString("countrating")
                        )
                    restoList.add(resto)
                }
                restaurants.value = restoList

                Log.d("showvoley", restoList.toString())
            },
            Response.ErrorListener{error->
                Log.d("showvoley", "Error fetching restaurant data: $error")
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