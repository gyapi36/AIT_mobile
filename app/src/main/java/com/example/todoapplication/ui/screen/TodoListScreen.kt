package com.example.todoapplication.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.todoapplication.data.TodoItem
import com.example.todoapplication.data.TodoPriority
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    todoListViewModel: TodoListViewModel = viewModel(),
    navController: NavController
) {
    Column (
        modifier = Modifier.padding(10.dp)
    ){

        TopAppBar(
            title = {
                Text("AIT Todo")
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor =
                MaterialTheme.colorScheme.secondaryContainer
            ),
            actions = {
                IconButton(onClick = {
                    navController.navigate(
                        "summary/${todoListViewModel.getAllTodoNum()}/${todoListViewModel.getImportantTodoNum()}"
                    )
                }) {
                    Icon(Icons.Filled.Info, null)
                }
            })

        AddNewTodoForm()

        LazyColumn {
            items(todoListViewModel.getAllToDoList()) {
                TodoCard(todoItem = it,
                    onTodoCheckChange = { checked ->
                        todoListViewModel.changeTodoState(it, checked) },
                    onRemoveItem = {
                        todoListViewModel.removeTodoItem(it)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddNewTodoForm(
    todoListViewModel: TodoListViewModel = viewModel()
) {
    var newTodoTitle by remember { mutableStateOf("") }
    var newTodoDescription by remember { mutableStateOf("") }
    var newTodoPriority by remember {mutableStateOf (false)}

    Column{
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = newTodoTitle,
                onValueChange = {
                    newTodoTitle = it
                },
                modifier = Modifier.weight(1f)
            )

            OutlinedTextField(
                value = newTodoDescription,
                onValueChange = {
                    newTodoDescription = it
                },
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ){
            Checkbox(checked = newTodoPriority , onCheckedChange = {
                newTodoPriority = it
            })
            Text(text = "Important")
        }

        Button(onClick = {
            todoListViewModel.addTodoList(
                TodoItem(
                    UUID.randomUUID().toString(),
                    newTodoTitle,
                    newTodoDescription,
                    Date(System.currentTimeMillis()).toString(),
                    if(newTodoPriority) TodoPriority.HIGH else TodoPriority.NORMAL,
                    false
                )
            )
        }) {
            Text(text = "Add")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCard(
    todoItem: TodoItem,
    onTodoCheckChange : (Boolean) -> Unit = {},
    onRemoveItem: () -> Unit = {}
){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
        ),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = Modifier.padding(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Image(painter = painterResource(id = todoItem.priority.getIcon()),
                contentDescription = "Priority",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 10.dp)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = todoItem.title,
                    textDecoration =
                    if (todoItem.isDone) {
                        TextDecoration.LineThrough
                    } else {
                        TextDecoration.None
                    }
                )
            }


            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = todoItem.isDone,
                    onCheckedChange = {
                        onTodoCheckChange(it)
                    },
                )
                Icon(

                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete",

                    modifier = Modifier.clickable {
                        onRemoveItem()
                    },
                    tint = Color.Red
                )
            }
        }
    }
}