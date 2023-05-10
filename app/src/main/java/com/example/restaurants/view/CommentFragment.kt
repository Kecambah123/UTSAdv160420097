package com.example.restaurants.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.restaurants.R
import com.example.restaurants.util.loadImage
import com.example.restaurants.viewmodel.DetailViewModel
import com.example.restaurants.viewmodel.ListViewModel
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

/**
 * A simple [Fragment] subclass.
 * Use the [CommentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CommentFragment : Fragment() {
    private lateinit var detailModel: DetailViewModel
    private val commentAdapter = CommentAdapter(arrayListOf())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        if(arguments != null){
            val id = CommentFragmentArgs.fromBundle(requireArguments()).restoId
            detailModel = ViewModelProvider(this).get(DetailViewModel::class.java)
            detailModel.comment(id)
            detailModel.commentNum(id)

            val commentView = view.findViewById<RecyclerView>(R.id.commentView)
            commentView.layoutManager = LinearLayoutManager(context)
            commentView.adapter = commentAdapter

            observeDetailModel()

            val commentEd = view.findViewById<TextInputEditText>(R.id.commentEd)
            val btnSend = view.findViewById<ImageButton>(R.id.btnSend)
            btnSend.setOnClickListener{
                val comment = commentEd.text.toString()
                val user_id = Global.user_id
                val resto_id = CommentFragmentArgs.fromBundle(requireArguments()).restoId
                if(comment == ""){
                    Toast.makeText(getActivity(), "Please input your comment!", Toast.LENGTH_SHORT).show()
                }
                else if(user_id == 0){
                    Toast.makeText(getActivity(), "Please login first!", Toast.LENGTH_SHORT).show()
                }
                else if(user_id == 0 && comment == ""){
                    Toast.makeText(getActivity(), "Please login first, then input your comment!", Toast.LENGTH_SHORT).show()
                }
                else{
                    val queue = Volley.newRequestQueue(activity)
                    val url = "https://wheli.site/adv/insertcomment.php"
                    val stringRequest = object : StringRequest(Request.Method.POST,url,
                        Response.Listener<String> {
                            Log.d("volley_result",it)
                            val obj = JSONObject(it)
                            if(obj.getString("result") == "OK") {
                                Toast.makeText(getActivity(), "Comment added succesfully!", Toast.LENGTH_SHORT).show()

                                val action = RegisterFragmentDirections.actionLoginFragment()
                                Navigation.findNavController(view).navigate(action)
                            }
                            else{
                                Toast.makeText(getActivity(),"Failed to add comment!",Toast.LENGTH_SHORT).show()
                            }
                        }, Response.ErrorListener{
                            Log.d("volley_error",it.message.toString())
                        }
                    ){
                        override fun getParams(): MutableMap<String,String>{
                            val params = HashMap<String,String>()
                            params["comment"] = comment
                            params["user_id"] = user_id.toString()
                            params["resto_id"] = resto_id
                            return params
                        }
                    }
                    queue.add(stringRequest)
                }
            }
        }
    }

    fun observeDetailModel(){
        val txtCommentCount: TextView = requireView().findViewById(R.id.txtCommentCount)
        detailModel.commentNum.observe(viewLifecycleOwner, Observer {
            txtCommentCount.setText(it.comment_num)
        })
        detailModel.comment.observe(viewLifecycleOwner, Observer{
            commentAdapter.updateCommentList(it)
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
        return inflater.inflate(R.layout.fragment_comment, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CommentFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CommentFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}