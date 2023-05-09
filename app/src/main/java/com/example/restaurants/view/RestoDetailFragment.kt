package com.example.restaurants.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurants.R
import com.example.restaurants.util.loadImage
import com.example.restaurants.viewmodel.DetailViewModel
import com.google.android.material.textfield.TextInputEditText
import java.util.concurrent.TimeUnit

/**
 * A simple [Fragment] subclass.
 * Use the [RestoDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RestoDetailFragment : Fragment() {

    private lateinit var detailModel: DetailViewModel
    private val foodAdapter = FoodAdapter(arrayListOf())
    private val drinkAdapter = DrinkAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null){
            val id = RestoDetailFragmentArgs.fromBundle(requireArguments()).id
            detailModel = ViewModelProvider(this).get(DetailViewModel::class.java)
            detailModel.fetch(id)
            detailModel.food(id)
            detailModel.drink(id)

            val foodView = view.findViewById<RecyclerView>(R.id.foodView)
            val drinkView = view.findViewById<RecyclerView>(R.id.drinkView)
            foodView.layoutManager = LinearLayoutManager(context)
            drinkView.layoutManager = LinearLayoutManager(context)
            foodView.adapter = foodAdapter
            drinkView.adapter = drinkAdapter

            observeDetailModel()
        }
    }

    fun observeDetailModel(){
        val txtResto: TextView = requireView().findViewById(R.id.txtResto)
        val txtRating: TextView = requireView().findViewById(R.id.txtRating)
        val txtCountRating: TextView = requireView().findViewById(R.id.txtCountRating)
        val txtAddress: TextView = requireView().findViewById(R.id.txtAddress)
        val txtPhoneNum: TextView = requireView().findViewById(R.id.txtPhoneNum)
        val backgroundView: ImageView = requireView().findViewById(R.id.backgroundView)
        val btnComments: ImageButton = requireView().findViewById(R.id.btnComments)
        val btnFavorite: ImageButton = requireView().findViewById(R.id.btnFavorite)
        val btnRating: ImageButton = requireView().findViewById(R.id.btnRating)
        val progresBar: ProgressBar = requireView().findViewById(R.id.progressBar2)

        detailModel.resto.observe(viewLifecycleOwner, Observer{
            txtResto.setText(it.resto_name)
            txtRating.setText(it.totalrating)
            val count = it.countrating
            txtCountRating.setText("($count)")
            txtAddress.setText(it.address)
            val phoneNum = it.phone_num
            txtPhoneNum.setText("Phone Number: $phoneNum")
            backgroundView.loadImage(it.background_img, progresBar)

            btnComments.setOnClickListener {

            }
            btnFavorite.setOnClickListener{

            }
            btnRating.setOnClickListener{

            }
        })
        detailModel.foods.observe(viewLifecycleOwner, Observer{
            foodAdapter.updateFoodList(it)
        })
        detailModel.drinks.observe(viewLifecycleOwner, Observer{
            drinkAdapter.updateDrinkList(it)
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
        return inflater.inflate(R.layout.fragment_resto_detail, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RestoDetailFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RestoDetailFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}