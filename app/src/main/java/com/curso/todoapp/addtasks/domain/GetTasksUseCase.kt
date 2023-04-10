package com.curso.todoapp.addtasks.domain

import com.curso.todoapp.addtasks.data.TaskRepository
import com.curso.todoapp.addtasks.ui.model.TaskModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTasksUseCase @Inject constructor( private val tasksRepository: TaskRepository){
    operator fun invoke(): Flow<List<TaskModel>> = tasksRepository.tasks

}