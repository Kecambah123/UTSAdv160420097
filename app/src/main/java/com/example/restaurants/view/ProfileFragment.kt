package com.example.restaurants.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.restaurants.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtGreet = view.findViewById<TextView>(R.id.txtGreet)
        val btnLogout = view.findViewById<FloatingActionButton>(R.id.fabLogout)
        val txtLogin = view.findViewById<TextView>(R.id.txtLogin)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val txtEditAccount = view.findViewById<TextView>(R.id.txtEditAccount)
        val txtEditName = view.findViewById<TextInputLayout>(R.id.txtEditName)
        val txtEditPwd = view.findViewById<TextInputLayout>(R.id.txtEditPwd)
        val edName = view.findViewById<TextInputEditText>(R.id.edName)
        val edPwd = view.findViewById<TextInputEditText>(R.id.edPwd)
        val btnUpdate = view.findViewById<Button>(R.id.btnUpdate)

        if(Global.user_id == 0){
            txtGreet.visibility = View.GONE
            btnLogout.visibility = View.GONE
            txtEditAccount.visibility = View.GONE
            txtEditName.visibility = View.GONE
            txtEditPwd.visibility = View.GONE
            edName.visibility = View.GONE
            edPwd.visibility = View.GONE
            btnUpdate.visibility = View.GONE
            txtLogin.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE

            btnLogin.setOnClickListener{
                val action = ProfileFragmentDirections.actionProfileLogin()
                Navigation.findNavController(it).navigate(action)
            }
        }else{
            txtGreet.visibility = View.VISIBLE
            btnLogout.visibility = View.VISIBLE
            txtEditAccount.visibility = View.VISIBLE
            txtEditName.visibility = View.VISIBLE
            txtEditPwd.visibility = View.VISIBLE
            edName.visibility = View.VISIBLE
            edPwd.visibility = View.VISIBLE
            btnUpdate.visibility = View.VISIBLE
            txtLogin.visibility = View.GONE
            btnLogin.visibility = View.GONE

            val queue = Volley.newRequestQueue(activity)
            val url = "https://wheli.site/adv/getprofile.php"
            var stringRequest = object : StringRequest(
                Request.Method.POST, url, Response.Listener<String>
                {
                    Log.d("volleyresult", it)
                    //Convert the whole JSON string into JSON object
                    val obj = JSONObject(it)
                    //Check the "result" value
                    if(obj.getString("result") == "OK"){
                        val name = obj.getString("name")
                        val pwd = obj.getString("password")

                        edName.setText(name)
                        edPwd.setText(pwd)
                        txtGreet.setText("Hello $name!")
                    }
                    else{
                        Log.d("error", "Failed to get data")
                    }
                },
                {
                    Log.e("volley_error", it.message.toString())
                }
            ){
                override fun getParams(): MutableMap<String,String>{
                    val params = HashMap<String,String>()
                    params["user_id"] = Global.user_id.toString()
                    return params
                }
            }
            queue.add(stringRequest)

            btnUpdate.setOnClickListener{
                val newName = edName.text.toString()
                val newPassword = edPwd.text.toString()

                val queue = Volley.newRequestQueue(context)
                val url = "https://wheli.site/adv/updateprofile.php"
                val stringRequest = object : StringRequest(Request.Method.POST, url,
                    Response.Listener<String> {
                        Log.d("volley_result", it)
                        val obj = JSONObject(it)
                        if (obj.getString("result") == "OK") {

                        } else {

                        }
                    }, Response.ErrorListener {
                        Log.d("volley_error", it.message.toString())
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["name"] = newName
                        params["password"] = newPassword
                        params["user_id"] = Global.user_id.toString()
                        return params
                    }
                }
                queue.add(stringRequest)

                Toast.makeText(getActivity(), "User data updated succesfully!", Toast.LENGTH_SHORT).show()
            }

            btnLogout.setOnClickListener{
                Global.user_id = 0
                val action = ProfileFragmentDirections.actionProfileLogin()
                Navigation.findNavController(it).navigate(action)
            }
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
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}