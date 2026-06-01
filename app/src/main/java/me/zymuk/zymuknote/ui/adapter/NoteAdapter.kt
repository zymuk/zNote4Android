package me.zymuk.zymuknote.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.zymuk.zymuknote.data.Note
import me.zymuk.zymuknote.databinding.ItemNoteBinding
import java.text.SimpleDateFormat

class NoteAdapter(private val onClick:(Note) -> Unit): RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private var _notes: ArrayList<Note> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {
        val note = _notes[position]

        holder.binding.apply {
            tvTitle.text = note.title
            tvContent.text = note.content
            tvDate.text = "Updated: ${SimpleDateFormat("yyyy/MM/dd/ HH:mm").format(note.updatedAt)}"
        }
    }

    override fun getItemCount(): Int {
        return _notes.size
    }

    inner class NoteViewHolder(var binding: ItemNoteBinding): RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun setNotes(notes: List<Note>) {
        _notes.clear()
        _notes.addAll(notes)
        notifyDataSetChanged()
    }
}