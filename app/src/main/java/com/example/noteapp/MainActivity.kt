package com.example.noteapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.noteapp.databinding.ActivityMainBinding
import com.example.noteapp.databinding.ActivityMainBinding.inflate


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = inflate(layoutInflater)
        setContentView(binding.root)

    }
}