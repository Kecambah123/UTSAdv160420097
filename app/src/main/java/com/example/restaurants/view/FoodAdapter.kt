package com.example.restaurants.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurants.R
import com.example.restaurants.model.Food
import com.example.restaurants.model.Restaurant
import com.example.restaurants.util.loadImage

class FoodAdapter(val foods:ArrayList<Food>): RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {
    class FoodViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.food_list_item, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val txtFoodName = holder.view.findViewById<TextView>(R.id.txtFoodName)
        val txtDescription = holder.view.findViewById<TextView>(R.id.txtDescription)
        val txtPrice = holder.view.findViewById<TextView>(R.id.txtPrice)
        txtFoodName.text = foods[position].food_name
        txtDescription.text = foods[position].description
        val price = foods[position].price
        txtPrice.text = "Rp. $price"

        var foodImg = holder.view.findViewById<ImageView>(R.id.foodImg)
        var progress = holder.view.findViewById<ProgressBar>(R.id.progressBar3)
        foodImg.loadImage(foods[position].food_img, progress)
    }

    override fun getItemCount(): Int {
        return foods.size
    }

    fun updateFoodList(newFoodList: ArrayList<Food>){
        foods.clear()
        foods.addAll(newFoodList)
        notifyDataSetChanged()
    }
}