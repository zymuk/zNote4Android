package me.zymuk.zymuknote.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import me.zymuk.zymuknote.R
import me.zymuk.zymuknote.data.Note
import me.zymuk.zymuknote.data.NoteDB
import me.zymuk.zymuknote.databinding.ActivityMainBinding
import me.zymuk.zymuknote.databinding.ActivityNoteEditorBinding
import me.zymuk.zymuknote.repo.NoteRepo
import me.zymuk.zymuknote.viewmodel.NoteViewModel
import me.zymuk.zymuknote.viewmodel.NoteViewModelFactory

class NoteEditor : AppCompatActivity() {

    private lateinit var binding: ActivityNoteEditorBinding
    private lateinit var viewModel: NoteViewModel

    private var noteCurrent: Note? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        setupViewModel()

        noteCurrent=intent.getParcelableExtra("note")
        if (noteCurrent!=null){
            binding.etTitle.setText(noteCurrent!!.title)
            binding.etContent.setText(noteCurrent!!.content)
        }
    }

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
    }

    private fun setupViewModel(){
        var dao = NoteDB.getDatabase(this).noteDao()
        var repo = NoteRepo(dao)
        var factory = NoteViewModelFactory(repo)

        viewModel = ViewModelProvider(this, factory)[(NoteViewModel::class.java)]
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_note_editor, menu)
        menu.findItem(R.id.action_delete).isVisible = noteCurrent != null
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_save -> {
                saveNote()
                true
            }
            R.id.action_delete -> {
                deleteNote()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun deleteNote() {
        val title = binding.etTitle.text.toString()
        val content = binding.etContent.text.toString()

        if(title.isEmpty()){
            binding.etTitle.error = "Title is required"
            return
        }
        if(content.isEmpty()){
            binding.etContent.error = "Content is required"
            return
        }

        if(noteCurrent==null){
            val note = Note(title = title, content = content, createdAt = "", updatedAt = "", isPinned = false)
        }else{
            val note=noteCurrent!!.copy(title = title, content = content)
            viewModel.update(note)
        }

        finish()
    }

    private fun saveNote() {
        if(noteCurrent!=null){
            viewModel.delete(noteCurrent!!)
        }
        finish()
    }
}