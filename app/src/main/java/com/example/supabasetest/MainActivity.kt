@file:Suppress("DEPRECATION")

package com.example.supabasetest


import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.supabasetest.databinding.SignInBinding
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlin.reflect.jvm.jvmErasure

class MainActivity : AppCompatActivity() {

    private var _binding: SignInBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for SignInBinding must not be null")

    private var isClicked = false

    private var client: SupabaseClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

            _binding = SignInBinding.inflate(layoutInflater)
            setContentView(binding.root)

            client = Client.client


            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN + View.SYSTEM_UI_FLAG_LAYOUT_STABLE

            binding.buttonSignIn.setOnClickListener() {

                if (inputDataValidator(binding.editTextEmail, binding.editTextPassword)) {
                    if (!isClicked) {
                        isClicked = true
                        val userInfo = User(
                            email = binding.editTextEmail.text.toString(),
                            password = binding.editTextPassword.text.toString()
                        )
                        //userSignIn(userInfo)
                        startSignIn()
                        Handler(Looper.getMainLooper()).postDelayed({ isClicked = false }, 3000)
                    }
                }
            }

        val textView = binding.textCreateAccount
        val textCreateAccount = binding.textCreateAccount.text.toString()
        val spanString = SpannableString(textCreateAccount)

        val textClick = object : ClickableSpan() {
            override fun onClick(p0: View) {
                if (!isClicked) {
                    try {
                        val intent = Intent(this@MainActivity, SignUp::class.java)
                        startActivity(intent)
                        isClicked = true
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    } catch (e: Exception) {
                        Log.e("mainActivity", "Error: ${e.message}")
                    }
                    Handler(Looper.getMainLooper()).postDelayed({isClicked = false},3000)
                }
            }
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)

                ds.color = Color.BLACK
                ds.isUnderlineText = false
            }
        }

        spanString.setSpan(textClick, textCreateAccount.indexOf('?')+2, textCreateAccount.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        textView.movementMethod = LinkMovementMethod.getInstance()
        textView.text = spanString
    }

    fun inputDataValidator(etEmail: EditText, etPassword: EditText) : Boolean {

        val emailNotEmpty: Boolean = etEmail.text.toString().isNotEmpty()
        val passwNotEmpty: Boolean = etPassword.text.toString().isNotEmpty()

        return (passwNotEmpty && emailNotEmpty && Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches())
    }

    private fun userSignIn(userInfo: User) {
        lifecycleScope.launch {
            try {
                Log.d("supabase", "Error")
                val supabaseResponse =
                    client!!.postgrest["users2"].select(columns = Columns.list("email, password")) {
                        filter {
                            User::email eq userInfo.email
                            User::password eq userInfo.password
                        }
                    }.decodeAs<User>()
                Log.d("supabase", supabaseResponse.toString())
                if (supabaseResponse.toString().isNotEmpty()) {
                    startSignIn()
                    Log.d("supabase", supabaseResponse.toString())
                }
                Log.d("supabase", supabaseResponse.toString())

            } catch (e: Exception) {
                Log.e("supabase", e.message.toString())
            }

        }
    }

    private fun startSignIn() {
        try {
            val intent = Intent(this@MainActivity, OnBoard1Activity::class.java)
            startActivity(intent)
            //finish()
        } catch (e: Exception) {
            Log.e("supabase", "Error signIn: ${e.message}")
        }
    }


}

