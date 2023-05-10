package com.example.restaurants.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.restaurants.R
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject


/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {


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
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnAccount = view.findViewById<Button>(R.id.btnAccount)
        val btnSign = view.findViewById<Button>(R.id.btnSign)
        btnAccount.setOnClickListener {
            val action = LoginFragmentDirections.actionRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }
        val usernameEd = view.findViewById<TextInputEditText>(R.id.usernameEd)
        val passwordEd = view.findViewById<TextInputEditText>(R.id.passwordEd)
        btnSign.setOnClickListener{
            val username = usernameEd.text.toString()
            val password = passwordEd.text.toString()
            val queue = Volley.newRequestQueue(activity)
            val url = "https://wheli.site/adv/login.php"
            val stringRequest = object : StringRequest(
                Request.Method.POST,url,
                Response.Listener<String> {
                    Log.d("volley_result",it)
                    val obj = JSONObject(it)
                    if(obj.getString("result") == "success") {
                        Toast.makeText(getActivity(), "Login Successful!", Toast.LENGTH_SHORT).show()
                        val id = obj.getInt("user_id")
                        Global.user_id = id

                        val action = LoginFragmentDirections.actionRestoFragment()
                        Navigation.findNavController(view).navigate(action)
                    }
                    else{
                        Toast.makeText(getActivity(),"Username or Password is wrong!", Toast.LENGTH_SHORT).show()
                    }
                }, Response.ErrorListener{
                    Log.d("volley_error",it.message.toString())
                }
            ){
                override fun getParams(): MutableMap<String,String>{
                    val params = HashMap<String,String>()
                    params["name"] = username
                    params["password"] = password
                    return params
                }
            }
            queue.add(stringRequest)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}