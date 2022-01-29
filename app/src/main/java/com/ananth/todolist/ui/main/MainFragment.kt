package com.ananth.todolist.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ananth.todolist.R
import com.ananth.todolist.models.TodoListItem
import com.ananth.todolist.ui.main.recyclerview.TodoListAdapter
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.github.kittinunf.fuel.Fuel
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Math.round
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel
        val recyclerView = view?.findViewById<RecyclerView>(R.id.listRecyclerView)
        val addButton = view?.findViewById<FloatingActionButton>(R.id.addFloatingActionButton)
        val editText = view?.findViewById<EditText>(R.id.editText)
        val completeFAB = view?.findViewById<ExtendedFloatingActionButton>(R.id.completeFAB)
        val adapter = TodoListAdapter() { numChecked ->
            completeFAB?.visibility = if (numChecked > 0) {
                View.VISIBLE
            } else {
                View.GONE
            }
            val end = if (numChecked > 1) {
                "s"
            } else {
                ""
            }
            completeFAB?.text = "Complete $numChecked item$end"
        }
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
        viewModel.list.observe(viewLifecycleOwner) { list ->
            adapter.updateData(list)
        }
        addButton?.setOnClickListener {
            editText?.let { editText ->
                if (editText.text.trim().isNotEmpty()) {
                    val value = editText.text.trim()
                    val todoItem = TodoListItem(value.toString(), Calendar.getInstance().time)
                    val list = adapter.getData()
                    val newList = mutableListOf<TodoListItem>()
                    newList.add(todoItem)
                    newList.addAll(list)
                    viewModel.updateData(newList)
                    editText.setText("")
                }
            }
        }
        completeFAB?.setOnClickListener {
            val list = adapter.getData()
            val checks = adapter.checked
            val newList = mutableListOf<TodoListItem>()
            for (i in list.indices) {
                if (!checks[i]) {
                    newList.add(list[i])
                }
            }
            viewModel.updateData(newList)
        }

        val textView = view?.findViewById<TextView>(R.id.weatherTextView)

        val queue = Volley.newRequestQueue(activity)
        val url = "https://api.openweathermap.org/data/2.5/weather?zip=30308,us&appid=10b8315120299e47872dd8301065eccf"

        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val jsonResponse = JSONObject(response)
                val weather = JSONObject(jsonResponse.get("main").toString())
                val temp = round((weather.get("temp").toString().toFloat() - 273.15) * 9.0 / 5.0 + 32)


                textView?.text = "The current temperature in Atlanta is: " + temp.toString() + " degrees"
            },
            { textView?.text = "That didn't work!" })

        queue.add(stringRequest)

        viewModel.setupDataLoading()
    }

}