package com.curso.todoapp.addtasks.domain

import com.curso.todoapp.addtasks.data.TaskRepository
import com.curso.todoapp.addtasks.ui.model.TaskModel
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val taskRepository: TaskRepository) {
    suspend operator fun invoke(taskModel: TaskModel){
        taskRepository.add(taskModel)
    }

}