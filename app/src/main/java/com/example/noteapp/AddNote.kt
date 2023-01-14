package com.example.noteapp

import android.app.Activity
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.noteapp.Models.Note
import com.example.noteapp.databinding.ActivityAddNoteBinding
import java.util.*


class AddNote : AppCompatActivity() {

    private lateinit var binding: ActivityAddNoteBinding

    private lateinit var note : Note
    private lateinit var  old_note : Note
    var isUpdated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {

            old_note = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText(old_note.title)
            binding.etNote.setText(old_note.note)
            isUpdated = true

        } catch (e : Exception){
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {

            val title  = binding.etTitle.text.toString()
            var note = binding.etNote.text.toString()

            if (title.isNotEmpty() || note.isNotEmpty()){
                val formatter = SimpleDateFormat("EEE, d MMM yyyy.HH:mm a")

                if (isUpdated) {

                    note = Note(
                        old_note.id, title, note, formatter.format(Date())
                    ).toString()
                } else {

                    note = Note(null,title,note,formatter.format(Date())).toString()

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