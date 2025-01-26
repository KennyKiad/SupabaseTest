package com.example.supabasetest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.supabasetest.databinding.Onboard2Binding

class OnBoard2Activity : AppCompatActivity() {

    private lateinit var binding: Onboard2Binding

    private var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Onboard2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonOnboard2.setOnClickListener() {
            if (!isClicked) {
                try {
                    Handler(Looper.getMainLooper()).postDelayed({ isClicked = false }, 3000)
                    val intent = Intent(this@OnBoard2Activity, OnBoard3Activity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("supabase", "Error: ${e.message}")
                }

            }
        }
    }
}