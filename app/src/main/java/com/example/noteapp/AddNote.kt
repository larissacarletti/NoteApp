package com.example.noteapp

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.noteapp.models.Note
import com.example.noteapp.databinding.ActivityAddNoteBinding
import java.util.*


class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var note : Note
    private lateinit var  oldNote : Note
    private var isUpdated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText(oldNote.title)
            binding.etNote.setText(oldNote.note)
            isUpdated = true
        } catch (e : Exception){
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {

            val title  = binding.etTitle.text.toString()
            var note = binding.etNote.text.toString()

            if (title.isNotEmpty() || note.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE, d MMM yyyy.HH:mm a")

                note = if (isUpdated) {
                    Note(
                        id = oldNote.id,
                        title = title,
                        note = note,
                        date = formatter.format(Date())
                    ).toString()
                } else {
                    Note(
                        id = null,
                        title = title,
                        note = note,
                        date = formatter.format(Date())
                    ).toString()
                }

                val intent = Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            } else {
                Toast.makeText(this@AddNote, "Please enter some data", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.imgBackArrow.setOnClickListener {
                onBackPressed()
            }
        }
    }
}