package com.example.noteapp


import android.app.Activity


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.provider.ContactsContract.CommonDataKinds.Note
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.noteapp.Adapter.NotesAdapter
import com.example.noteapp.Database.NoteDatabase
import com.example.noteapp.Models.NoteViewModel
import com.example.noteapp.databinding.ActivityMainBinding


class MainActivity() : AppCompatActivity(), LifecycleOwner, Parcelable {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    lateinit var viewModel: NoteViewModel
    lateinit var adapter: NotesAdapter
    lateinit var selectedNote: Note

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initializing the UI
        initUI()

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
            .get(NoteViewModel::class.java)

        viewModel.allnotes.observe(this) { list ->

            list?.let {

                adapter.updateList(list)

            }

        }

        database = NoteDatabase.getDatabase(this)


    }

    private fun initUI() {

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayout.VERTICAL)
        adapter = NotesAdapter(this, this)
        binding.recyclerView.adapter = adapter

        val getContent =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                if (result.resultCode == Activity.RESULT_OK) {

                    val note = result.data?.getSerializableExtra("note") as? Note
                    if (note != null) {

                        viewModel.insertNote(note)

                    }

                }

            }

        binding.fbAddNote
    }
}
