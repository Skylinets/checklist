package com.curso.todoapp.addtasks.ui


import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curso.todoapp.addtasks.data.toData
import com.curso.todoapp.addtasks.domain.AddTaskUseCase
import com.curso.todoapp.addtasks.domain.DeleteTaskUseCase
import com.curso.todoapp.addtasks.domain.GetTasksUseCase
import com.curso.todoapp.addtasks.domain.UpdateTaskUseCase
import com.curso.todoapp.addtasks.ui.TasksUiState.*
import com.curso.todoapp.addtasks.ui.model.TaskModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TasksViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    getTasksUseCase: GetTasksUseCase
): ViewModel() {


    val uiState: StateFlow<TasksUiState> = getTasksUseCase().map(::Success )
        .catch{ Error(it) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Loading)


    private val _showDialog = MutableLiveData<Boolean>()
    val showDialog: LiveData<Boolean> = _showDialog

/*    private val _tasks = mutableStateListOf<TaskModel>()
    val task:List<TaskModel> = _tasks*/

    fun onDialogClose(){
        _showDialog.value = false
    }

    fun onTasksCreated(task: String) {
        _showDialog.value = false
      /* _tasks.add(TaskModel(task = task))*/

        viewModelScope.launch {
            addTaskUseCase(TaskModel(task = task))
        }
    }

    fun onShowDialogClick() {
        _showDialog.value = true
    }

    fun onCheckBoxSelected(taskModel: TaskModel) {
        /* Marcar Check
        val index = _tasks.indexOf(taskModel)
        _tasks[index] = _tasks[index].let {
            it.copy(selected = !it.selected)
        }*/

        viewModelScope.launch {
            updateTaskUseCase(taskModel.copy(selected = !taskModel.selected))
        }
    }

    fun onItemRemove(taskModel: TaskModel) {
        //_tasks.remove(taskModel)  Borrar item
       /* val task = _tasks.find { it.id == taskModel.id}
        _tasks.remove(task)*/
        viewModelScope.launch {
            deleteTaskUseCase(taskModel)
        }

    }


}