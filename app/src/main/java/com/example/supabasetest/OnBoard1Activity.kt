package com.example.supabasetest

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.supabasetest.databinding.Onboard1Binding

class OnBoard1Activity : AppCompatActivity() {
    private lateinit var binding : Onboard1Binding

    private var isClicked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Onboard1Binding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonOnboard1.setOnClickListener() {
            if (!isClicked) {
                isClicked = true
                try {
                    Handler(Looper.getMainLooper()).postDelayed({ isClicked = false }, 3000)
                    val intent = Intent(this@OnBoard1Activity, OnBoard2Activity::class.java)
                    startActivity(intent)
                } catch (e: Exception) {
                    Log.e("supabase", "Error: ${e.message}")
                }
            }
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}