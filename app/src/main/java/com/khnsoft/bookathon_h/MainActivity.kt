package com.khnsoft.bookathon_h

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.khnsoft.bookathon_h.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}