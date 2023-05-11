package com.example.restaurants.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.restaurants.R
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 * Use the [RatingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RatingFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resto_name = RatingFragmentArgs.fromBundle(requireArguments()).restoName

        val ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
        val txtHow = view.findViewById<TextView>(R.id.txtHow)
        txtHow.setText("How good is $resto_name?")

        ratingBar.setOnRatingBarChangeListener{ _, rating, _ ->
            if(rating < 1.0){
                ratingBar.rating = 1f
            }else if(rating > 5.0){
                ratingBar.rating = 5f
            }
        }

        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener{
            val ratingValue = ratingBar.rating.toInt()
            val resto_id = RatingFragmentArgs.fromBundle(requireArguments()).restoId

            val queue = Volley.newRequestQueue(activity)
            val url = "https://wheli.site/adv/insertrating.php"
            val stringRequest = object : StringRequest(Request.Method.POST,url,
                Response.Listener<String> {
                    Log.d("volley_result",it)
                    val obj = JSONObject(it)
                    if(obj.getString("result") == "OK") {
                        Toast.makeText(getActivity(), "Rating added successfully!", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(getActivity(),"Rating value is the same as previous one!", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener{
                    Log.d("volley_error",it.message.toString())
                }
            ){
                override fun getParams(): MutableMap<String,String>{
                    val params = HashMap<String,String>()
                    params["user_id"] = Global.user_id.toString()
                    params["resto_id"] = resto_id
                    params["rating"] = ratingValue.toString()
                    return params
                }
            }
            queue.add(stringRequest)
        }
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
        return inflater.inflate(R.layout.fragment_rating, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RatingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RatingFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}