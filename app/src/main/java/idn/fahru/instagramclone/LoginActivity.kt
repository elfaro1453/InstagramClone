package idn.fahru.instagramclone

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import idn.fahru.instagramclone.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    // VIEWBINDING untuk Activity_Login.xml
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // deklarasi VIEWBINDING dimulai
        val inflater = layoutInflater
        binding = ActivityLoginBinding.inflate(inflater)
        setContentView(binding.root)
        // deklarasi VIEWBINDING selesai

        binding.run {
            // menambahkan click pada btnsignup
            btnSignup.setOnClickListener {
                //buat intent menuju ke Register Activity
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                // mulai activity intent itu
                startActivity(intent)
            }

            btnLogin.setOnClickListener {
                // jalankan fungsi loginUser()
                loginUser()
            }
        }
    }

    // buat fungsi loginUser()
    private fun loginUser() {
        // buat variabel berisi email dan password dari editText
        val email = binding.inputEmail.text.toString()
        val passWord = binding.inputPass.text.toString()

        // cek kalau email dan password itu ada
        if (email.isEmpty()) { // jika email kosong
            Toast.makeText(this, "Email harus diisi", Toast.LENGTH_SHORT).show()
            // return agar fungsi berakhir
            return
        }
        if (passWord.isEmpty()) { // jika password kosong
            Toast.makeText(this, "Password harus diisi", Toast.LENGTH_SHORT).show()
            return
        }
        if (passWord.length < 8) { // Jika password kurang dari 8 karakter
            Toast.makeText(this, "Password minimal 8 karakter", Toast.LENGTH_SHORT).show()
            return
        }

        // jika email dan password sudah sesuai
        // buat variabel untuk menghubungkan ke Firebase
        val mAuth = FirebaseAuth.getInstance()
        // masuk ke firebase dengan email dan password
        mAuth.signInWithEmailAndPassword(email, passWord)
            .addOnCompleteListener { task ->
                // jika berhasil login
                if (task.isSuccessful) {
                    // lanjut intent ke MainActivity
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    finish()
                } else { // jika tidak berhasil login
                    // Tampilkan Toast pesan error login
                    val message = task.exception.toString()
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    mAuth.signOut()
                }
            }
    }

}