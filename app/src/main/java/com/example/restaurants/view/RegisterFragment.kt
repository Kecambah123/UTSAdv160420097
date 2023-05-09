package com.example.restaurants.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.restaurants.R
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {

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
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnCreate = view.findViewById<Button>(R.id.btnCreate)
        val nameEd = view.findViewById<TextInputEditText>(R.id.nameEd)
        val pwdEd = view.findViewById<TextInputEditText>(R.id.pwdEd)
        val repeatEd = view.findViewById<TextInputEditText>(R.id.repeatEd)
        btnCreate.setOnClickListener {
            val name = nameEd.text.toString()
            val pwd = pwdEd.text.toString()
            val repeat = repeatEd.text.toString()
            if(((name == "") && (pwd == "") && (repeat == "")) || ((name == "") || (pwd == "") || (repeat == ""))){
                Toast.makeText(getActivity(), "Please input all the fields!", Toast.LENGTH_SHORT).show()
            }
            else if(pwd != repeat){
                Toast.makeText(getActivity(), "Password and repeat password doesn't match!", Toast.LENGTH_SHORT).show()
            }
            else if(pwd == repeat){
                val queue = Volley.newRequestQueue(activity)
                val url = "https://wheli.site/adv/register.php"
                val stringRequest = object : StringRequest(Request.Method.POST,url,
                    Response.Listener<String> {
                        Log.d("volley_result",it)
                        val obj = JSONObject(it)
                        if(obj.getString("result") == "OK") {
                            Toast.makeText(getActivity(), "Registration Successful!", Toast.LENGTH_SHORT).show()

                            val action = RegisterFragmentDirections.actionLoginFragment()
                            Navigation.findNavController(view).navigate(action)
                        }
                        else{
                            Toast.makeText(getActivity(),"Username and Password already registered!",Toast.LENGTH_SHORT).show()
                        }
                    }, Response.ErrorListener{
                        Log.d("volley_error",it.message.toString())
                    }
                ){
                    override fun getParams(): MutableMap<String,String>{
                        val params = HashMap<String,String>()
                        params["name"] = name
                        params["password"] = pwd
                        return params
                    }
                }
                queue.add(stringRequest)
            }
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}