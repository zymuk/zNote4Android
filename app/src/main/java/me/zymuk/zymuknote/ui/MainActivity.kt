package me.zymuk.zymuknote.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import me.zymuk.zymuknote.R
import me.zymuk.zymuknote.data.NoteDB
import me.zymuk.zymuknote.databinding.ActivityMainBinding
import me.zymuk.zymuknote.repo.NoteRepo
import me.zymuk.zymuknote.ui.adapter.NoteAdapter
import me.zymuk.zymuknote.viewmodel.NoteViewModel
import me.zymuk.zymuknote.viewmodel.NoteViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NoteViewModel

    private var noteAdapter = NoteAdapter{note ->
        val intent = Intent(this, NoteEditor::class.java)
        intent.putExtra("note", note)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        setupViewModel()
        setupRecyclerView()
        observeNotes()
        setupSearch()

        binding.btAddNote.setOnClickListener {
            val intent = Intent(this, NoteEditor::class.java)
            startActivity(intent)
        }
    }

    private fun setupViewModel() {
        var dao = NoteDB.getDatabase(this).noteDao()
        var repo = NoteRepo(dao)
        var factory = NoteViewModelFactory(repo)

        viewModel = ViewModelProvider(this, factory)[(NoteViewModel::class.java)]
    }

    private fun setupRecyclerView() {
        binding.rvNotes.layoutManager = LinearLayoutManager(this)
        binding.rvNotes.adapter = noteAdapter
    }

    private fun observeNotes() {
        viewModel.allNotes.observe(this) { notes ->
            noteAdapter.setNotes(notes)
        }
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener { text ->
            viewModel.searchNotes(text.toString()).observe(this) {
                noteAdapter.setNotes(it)
            }
        }
    }
    
}