package com.example.restaurants.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurants.R
import com.example.restaurants.model.Drink
import com.example.restaurants.model.Food
import com.example.restaurants.util.loadImage

class DrinkAdapter(val drinks:ArrayList<Drink>): RecyclerView.Adapter<DrinkAdapter.DrinkViewHolder>() {
    class DrinkViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrinkViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.drink_list_item, parent, false)
        return DrinkViewHolder(view)
    }

    override fun onBindViewHolder(holder: DrinkViewHolder, position: Int) {
        val txtDrinkName = holder.view.findViewById<TextView>(R.id.txtDrinkName)
        val txtDrinkPrice = holder.view.findViewById<TextView>(R.id.txtDrinkPrice)
        txtDrinkName.text = drinks[position].drink_name
        val price = drinks[position].price
        txtDrinkPrice.text = "Rp. $price"

        var drinkImg = holder.view.findViewById<ImageView>(R.id.drinkImg)
        var progress = holder.view.findViewById<ProgressBar>(R.id.progressBar4)
        drinkImg.loadImage(drinks[position].drink_img, progress)
    }

    override fun getItemCount(): Int {
        return drinks.size
    }

    fun updateDrinkList(newDrinkList: ArrayList<Drink>){
        drinks.clear()
        drinks.addAll(newDrinkList)
        notifyDataSetChanged()
    }
}