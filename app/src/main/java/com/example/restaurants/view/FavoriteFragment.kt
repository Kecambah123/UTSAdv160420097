package com.example.restaurants.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurants.R
import com.example.restaurants.viewmodel.ListViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [FavoriteFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoriteFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val favoriteAdapter = FavoriteAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        val favView = view.findViewById<RecyclerView>(R.id.favView)

        if(Global.user_id == 0){
            favView.visibility = View.GONE
        }
        else{
            viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
            favView.visibility = View.VISIBLE
            viewModel.favorite()
            favView.layoutManager = LinearLayoutManager(context)
            favView.adapter = favoriteAdapter

            observeViewModel()
        }
    }

    fun observeViewModel(){
        viewModel.favorites.observe(viewLifecycleOwner, Observer{
            favoriteAdapter.updateFavoriteList(it)
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavoriteFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}