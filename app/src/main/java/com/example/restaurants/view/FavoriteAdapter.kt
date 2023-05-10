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
import com.example.restaurants.model.Favorite
import com.example.restaurants.model.Restaurant
import com.example.restaurants.util.loadImage

class FavoriteAdapter(val favorites:ArrayList<Favorite>): RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    class FavoriteViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.restaurant_list_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val txtTitle = holder.view.findViewById<TextView>(R.id.txtTitle)
        val txtAverage = holder.view.findViewById<TextView>(R.id.txtAverage)
        val txtCount = holder.view.findViewById<TextView>(R.id.txtCount)
        txtTitle.text = favorites[position].resto_name
        txtAverage.text = favorites[position].totalrating
        val count = favorites[position].countrating
        txtCount.text = "($count)"

        val btnDetail = holder.view.findViewById<Button>(R.id.btnDetail)
        btnDetail.setOnClickListener{
            val action = FavoriteFragmentDirections.actionFavoriteDetail(favorites[position].id.toString())
            Navigation.findNavController(it).navigate(action)
        }

        var imageView = holder.view.findViewById<ImageView>(R.id.imageView)
        var progress = holder.view.findViewById<ProgressBar>(R.id.progressBar)
        imageView.loadImage(favorites[position].resto_img, progress)
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    fun updateFavoriteList(newFavoriteList: ArrayList<Favorite>){
        favorites.clear()
        favorites.addAll(newFavoriteList)
        notifyDataSetChanged()
    }
}