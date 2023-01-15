package com.example.noteapp.Models

import android.app.Application
import androidx.lifecycle.AndroidViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope


import com.example.noteapp.Database.NoteDatabase
import com.example.noteapp.Database.NotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel (application: Application) : AndroidViewModel(application) {

    private val repository : NotesRepository

    val allnotes : LiveData<List<Note>>

    init {
        val dao = NoteDatabase.getDatabase(application).getNoteDao()
        repository = NotesRepository(dao)
        allnotes = repository.allNotes
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO){

        repository.delete(note)

    }

    fun insertNote(note: Note?) = viewModelScope.launch(Dispatchers.IO){
        if (note != null) {
            repository.insert(note)
        }

    }

    fun updateNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(note)
    }



}


