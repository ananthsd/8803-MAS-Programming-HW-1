package com.ananth.todolist.models

import java.util.*

data class TodoListItem (val text: String, val created: Date) {
    constructor() : this("default", Calendar.getInstance().time)

}