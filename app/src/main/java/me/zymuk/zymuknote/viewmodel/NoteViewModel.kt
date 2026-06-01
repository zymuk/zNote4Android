package me.zymuk.zymuknote.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.zymuk.zymuknote.data.Note
import me.zymuk.zymuknote.repo.NoteRepo

class NoteViewModel(private val repo: NoteRepo): ViewModel() {

    val allNotes: LiveData<List<Note>> = repo.allNotes

    fun insert(note: Note) = viewModelScope.launch {
        repo.insert(note)
    }

    fun update(note: Note) = viewModelScope.launch {
        repo.update(note)
    }

    fun delete(note: Note) = viewModelScope.launch {
        repo.delete(note)
    }

    fun searchNotes(query: String): LiveData<List<Note>> {
        return if (query.isEmpty()) {
            allNotes
        }else repo.searchNotes(query)
    }
}

class NoteViewModelFactory(private val repo: NoteRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

