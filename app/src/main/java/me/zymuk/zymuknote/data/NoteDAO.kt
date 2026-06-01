package me.zymuk.zymuknote.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Insert
import androidx.room.Update
import androidx.room.Delete
import androidx.room.OnConflictStrategy

@Dao
interface NoteDAO {
    @Query("SELECT * FROM notes ORDER BY updated_at DESC")
    fun getAll(): LiveData<List<Note>>

    @Query("SELECT * FROM notes WHERE id = :id")
    fun getById(id: Int): Note?


    @Query("SELECT * FROM notes WHERE title LIKE :title")
    fun getByTitle(title: String): List<Note>

    @Query("SELECT * FROM notes WHERE content LIKE :content")
    fun getByContent(content: String): List<Note>


    @Query("SELECT * FROM notes WHERE is_pinned = 1")
    fun getPinned(): List<Note>


    @Query("SELECT * FROM notes WHERE is_pinned = 0")
    fun getUnpinned(): List<Note>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(note: Note)

    @Update
    fun update(note: Note)

    @Delete
    fun delete(note: Note)

    @Query("SELECT * FROM notes WHERE title LIKE :query OR content LIKE :query ORDER BY updated_at DESC")
    fun searchNotes(query: String): LiveData<List<Note>>
}