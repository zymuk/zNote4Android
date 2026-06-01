package me.zymuk.zymuknote.repo

import androidx.lifecycle.LiveData
import me.zymuk.zymuknote.data.Note
import me.zymuk.zymuknote.data.NoteDAO

class NoteRepo(private val noteDAO: NoteDAO) {

    val allNotes: LiveData<List<Note>> = noteDAO.getAll()

    suspend fun insert(note: Note) {
        noteDAO.insert(note)
    }

    suspend fun update(note: Note) {
        noteDAO.update(note)
    }

    suspend fun delete(note: Note) {
        noteDAO.delete(note)
    }

    fun searchNotes(query: String): LiveData<List<Note>>  {
        return noteDAO.searchNotes("%$query%")
    }
}