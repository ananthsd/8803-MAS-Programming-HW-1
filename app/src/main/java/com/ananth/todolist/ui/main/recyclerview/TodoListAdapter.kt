package com.ananth.todolist.ui.main.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ananth.todolist.R
import com.ananth.todolist.models.TodoListItem

class TodoListAdapter(val checkCallback: (Int) -> Unit) : RecyclerView.Adapter<TodoViewHolder>() {
    private val content: MutableList<TodoListItem> = mutableListOf()
    var checked: BooleanArray = BooleanArray(0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(viewType, parent, false);
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.checkBox.isChecked = checked[position]
        holder.content.text = content[position].text

        holder.checkBox.setOnCheckedChangeListener { compoundButton, b ->
            checked[position] = b
            var numChecked = 0
            for (b in checked) {
                if (b) {
                    numChecked++
                }
            }
            checkCallback(numChecked)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.static_todo_item
    }

    fun updateData(data: List<TodoListItem>) {
        content.clear()
        content.addAll(data)
        checked = BooleanArray(data.size)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return content.size
    }

    fun getData(): List<TodoListItem> {
        val x = mutableListOf<TodoListItem>()
        x.addAll(content)
        return x
    }
}