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
import com.example.restaurants.model.Comment
import com.example.restaurants.model.Restaurant
import com.example.restaurants.util.loadImage

class CommentAdapter(val comment:ArrayList<Comment>): RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {
    class CommentViewHolder(var view: View): RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.comment_list_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val txtUser = holder.view.findViewById<TextView>(R.id.txtUser)
        val txtUserComment = holder.view.findViewById<TextView>(R.id.txtUserComment)
        val txtCommentCount = holder.view.findViewById<TextView>(R.id.txtCommentCount)
        txtUser.text = comment[position].name
        txtUserComment.text = comment[position].comment
    }

    override fun getItemCount(): Int {
        return comment.size
    }

    fun updateCommentList(newCommentList: ArrayList<Comment>){
        comment.clear()
        comment.addAll(newCommentList)
        notifyDataSetChanged()
    }
}