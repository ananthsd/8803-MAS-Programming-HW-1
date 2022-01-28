package com.ananth.todolist.ui.main.recyclerview

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ananth.todolist.R

class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    lateinit var content: TextView
    lateinit var checkBox: CheckBox
    init {
        if(itemView is ConstraintLayout){
            content = itemView.findViewById(R.id.todoContent)
            checkBox = itemView.findViewById(R.id.checkbox)
        }
    }
}
