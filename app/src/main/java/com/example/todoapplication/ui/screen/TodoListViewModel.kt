package com.example.todoapplication.ui.screen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.todoapplication.data.TodoItem
import com.example.todoapplication.data.TodoPriority

class TodoListViewModel : ViewModel() {
    private var _todoList = mutableStateListOf<TodoItem>()

    fun getAllToDoList(): List<TodoItem> {
        return _todoList;
    }

    fun getAllTodoNum(): Int {
        return _todoList.size
    }

    fun getImportantTodoNum(): Int {
        return _todoList.count { it.priority == TodoPriority.HIGH }
    }

    fun addTodoList(todoItem: TodoItem) {
        _todoList.add(todoItem)
    }


    fun removeTodoItem(todoItem: TodoItem) {
        _todoList.remove(todoItem)
    }

    fun clearAllTodos() {
        _todoList.clear()
    }

    fun changeTodoState(todoItem: TodoItem, value: Boolean) {
        val index = _todoList.indexOf(todoItem)

        val newTodo = todoItem.copy(
            title = todoItem.title,
            description = todoItem.description,
            createDate = todoItem.createDate,
            priority = todoItem.priority,
            isDone = value
        )

        //It will redraw the item in the list when i replace an item with a "new" object
        _todoList[index] = newTodo
    }
}