package idn.fahru.instagramclone

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import idn.fahru.instagramclone.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val inflater = layoutInflater
        binding = ActivityRegisterBinding.inflate(inflater)
        setContentView(binding.root)

        binding.run {
            // tambahkan setonclicklistener pada tombol btnSignin
            btnSignin.setOnClickListener {
                // finish() digunakan untuk mengakhiri sebuah activity
                // sehingga activity sebelumnya akan terbuka
                finish()
            }

            // tambahkan setonclicklistener pada tombol register
            btnRegister.setOnClickListener {
                // jalankan fungsi createAccount()
                // kalau merah maka buat fungsi createAccount()
                createAccount()
            }
        }
    }

    // buat fungsi bernama showToast()
    private fun showToast(pesan: String) {
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show()
    }

    // buat fungsi createAccount()
    private fun createAccount() {
        // gunakan binding.run karena kita akan akses view di layout
        binding.run {
            // ambil nilai yang dimasukkan ke dalam masing-masing editText
            val fullName = inputFullname.text.toString()
            val emailUser = inputEmail.text.toString()
            val userName = inputUsername.text.toString()
            val passWord = inputPassword.text.toString()

            // cek semua input, jika kosong tampilkan toast
            // return digunakan untuk mengakhiri jalannya fungsi
            if (fullName.isEmpty()) {
                showToast("Fullname harus diisi !")
                return
            }
            if (emailUser.isEmpty()) {
                showToast("Email harus diisi !")
                return
            }
            if (userName.isEmpty()) {
                showToast("Username harus diisi !")
                return
            }
            if (passWord.isEmpty()) {
                showToast("Password harus diisi !")
                return
            }
            if (!emailUser.isEmailValid()) {
                // tanda seru ! digunakan untuk hasil yang berlawanan
                // semisal hasil isEmailValid itu true
                // maka di if ini nilainya false
                showToast("Email Tidak Valid")
                return
            }
            if (passWord.count() < 8) { // Jika password ukurannya kurang dari 8 karakter
                showToast("Password minimal 8 karakter")
                return
            }

            showToast("Semua kolom telah diisi !")
        }
    }
}