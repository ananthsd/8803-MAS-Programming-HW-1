package com.ananth.todolist.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ananth.todolist.models.TodoListItem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainViewModel : ViewModel() {
    // TODO: Implement the ViewModel
    val list: MutableLiveData<List<TodoListItem>> = MutableLiveData()

    fun updateData(items: List<TodoListItem>) {
        val database = Firebase.database
        val testReference = database.getReference("list")
        testReference.setValue(items)
    }

    fun setupDataLoading(){
        val database = Firebase.database
        val testReference = database.getReference("list")
        testReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val value = snapshot.getValue<List<TodoListItem>>()
                value?.let { list.value = it }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("DataLoading", "Failed to read value.", error.toException())
            }

        })
    }
}