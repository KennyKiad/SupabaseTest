@file:Suppress("DEPRECATION")

package com.example.supabasetest

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.supabasetest.databinding.SignUpBinding
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import kotlinx.coroutines.launch

class SignUp : AppCompatActivity() {
    private var _binding: SignUpBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("Binding for SignUpBinding must not be null")

    private var isClicked = false

    private var client : SupabaseClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = SignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        client = Client.client

        binding.buttonSignUp.setOnClickListener() {
            if (MainActivity().inputDataValidator(binding.editTextEmail, binding.editTextPassword) && !isClicked) {
                isClicked = true
                if (binding.checkboxPersonData.isChecked) {
                    binding.checkboxPersonData.buttonTintList = getColorStateList(R.color.black)
                    val userInfo = User(
                        name = binding.editTextName.text.toString(),
                        email = binding.editTextEmail.text.toString(),
                        password = binding.editTextPassword.text.toString()
                    )
                    signUp(userInfo)
                    Handler(Looper.getMainLooper()).postDelayed({ isClicked = false }, 3000)
                } else {
                    binding.checkboxPersonData.buttonTintList = getColorStateList(R.color.errorColor)
                }
            }
        }

        val textView = binding.textSignIn
        val text = textView.text.toString()

        val spannableText = SpannableString(text)

        val textClick = object : ClickableSpan() {
            override fun onClick(p0: View) {
                finish()
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = Color.BLACK
                ds.isUnderlineText = false
            }
        }

        spannableText.setSpan(textClick, text.indexOf('?')+2, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.text = spannableText
    }

    private fun signUp(userInfo: User) {
        Log.d("supabase", "In SignUp")
        lifecycleScope.launch {
            Log.d("supabase", "In launch")
            try {
                Log.d("supabase", "In try")
                client!!.postgrest["users2"].insert(userInfo)
            } catch(e:Exception) {
                Log.e("supabase", "Insert failed: ${e.message}")
            }
                Log.d("supabase", "After insert")
                finish()
        }
    }

}