

package com.example.supabasetest


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.supabasetest.databinding.SignInBinding
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import kotlinx.serialization.Serializable

@Suppress("DEPRECATION")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        try {
            Client.client = createSupabaseClient(BuildConfig.supabaseUrl, BuildConfig.supabaseKey) {
                install(Postgrest)
            }
        } catch (e: Exception) {
            Log.e("supabase", "Create client: ${e.message}")
        }

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler(Looper.getMainLooper()).postDelayed({
            try {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } catch (e: Exception) {
                Log.e("supabase", "Start mainActivity Error: ${e.message}")
            }
        }, 1500)

    }
}

fun Handler.postDelayed(run: Unit, l: Long) {

}
