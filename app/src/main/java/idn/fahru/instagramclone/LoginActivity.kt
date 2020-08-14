package idn.fahru.instagramclone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import idn.fahru.instagramclone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = layoutInflater
        binding = ActivityLoginBinding.inflate(inflater)
        setContentView(binding.root)

        binding.run {
            // menambahkan click pada btnsignup
            btnSignup.setOnClickListener {
                //buat intent menuju ke Register Activity
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                // mulai activity intent itu
                startActivity(intent)
            }
        }

    }
}