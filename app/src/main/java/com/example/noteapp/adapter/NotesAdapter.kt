package com.example.noteapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.MainActivity
import com.example.noteapp.models.Note
import com.example.noteapp.R
import kotlin.random.Random


class NotesAdapter(
    private val context: Context,
    private val listener: MainActivity
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    private val notesList = ArrayList<Note>()
    private val fullList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate((R.layout.list_item), parent, false)
        )

    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notesList[position]
        holder.run {
            title.text = currentNote.title
            title.isSelected = true
            note.text = currentNote.note //check this after
            date.text = currentNote.date
            date.isSelected = true
            notesLayout.setCardBackgroundColor(
                holder.itemView.resources.getColor(
                    randomColor(),
                    null
                )
            )
            holder.notesLayout.setOnClickListener {
                listener.onItemClicked(notesList[holder.adapterPosition])
            }
            holder.notesLayout.setOnLongClickListener {
                listener.onLongItemClicked(
                    notesList[holder.adapterPosition],
                    holder.notesLayout
                )
                true
            }
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }

    fun updateList(newList: List<Note>) {
        fullList.clear()
        fullList.addAll(newList)
        notesList.clear()
        notesList.addAll(fullList)
        notifyDataSetChanged()
    }

    fun filterList(search: String) {
        notesList.clear()
        for (item in fullList) {
            if (item.title?.lowercase()?.contains(search.lowercase()) == true ||
                item.note?.lowercase()?.contains(search.lowercase()) == true
            ) {
                notesList.add(item)
            }
        }
        notifyDataSetChanged()
    }


    private fun randomColor(): Int {
        val list = ArrayList<Int>()
        list.add(R.color.NoteColor1)
        list.add(R.color.NoteColor2)
        list.add(R.color.NoteColor3)
        list.add(R.color.NoteColor4)
        list.add(R.color.NoteColor5)
        list.add(R.color.NoteColor6)

        val seed = System.currentTimeMillis().toInt()
        val randomIndex = Random(seed).nextInt(list.size)
        return list[randomIndex]
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val notesLayout: CardView = itemView.findViewById(R.id.card_layout)
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val note: TextView = itemView.findViewById(R.id.tv_note)
        val date: TextView = itemView.findViewById(R.id.tv_date)
    }

    interface NotesClickListener {
        fun onItemClicked(note: Note)
        fun onLongItemClicked(note: Note, cardView: CardView)
    }
}