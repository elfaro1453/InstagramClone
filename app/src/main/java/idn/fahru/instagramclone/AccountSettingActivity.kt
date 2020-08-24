package idn.fahru.instagramclone

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import idn.fahru.instagramclone.databinding.ActivityAccountSettingBinding
import idn.fahru.instagramclone.model.User

class AccountSettingActivity : AppCompatActivity() {
    // viewbinding untuk activity_account_setting.xml
    private lateinit var binding: ActivityAccountSettingBinding

    // buat variabel userInfo berisi database reference
    private lateinit var userInfo: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // pengaturan viewbinding dimulai
        val inflater = layoutInflater
        binding = ActivityAccountSettingBinding.inflate(inflater)
        setContentView(binding.root)
        // pengaturan viewbinding selesai

        // setting agar button logout bisa logout dari firebase
        // dan kembali ke activity login
        binding.btnLogout.setOnClickListener {
            // signout / logout dari firebase
            FirebaseAuth.getInstance().signOut()
            // intent menuju activity login
            val intent = Intent(this, LoginActivity::class.java)
            // add flags agar tombol back tidak bisa diklik
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            // mulai activity intent
            startActivity(intent)
            // finish (hapus) activity account setting activity
            finish()
        }

        // tombol close diberi setOnClickListener finish untuk menutup activity
        binding.btnClose.setOnClickListener {
            finish()
        }

        // tombol centang diberi setonclick untuk mengupdate info
        binding.btnAccept.setOnClickListener {
            updateUserInfo()
        }

        // jika ada user yang login
        FirebaseAuth.getInstance().currentUser?.let { currentUser ->
            // dapatkan UID dari User yang login
            val uidUser = currentUser.uid
            // dapatkan user info yang login berdasarkan UIDnya
            userInfo = FirebaseDatabase.getInstance()
                .reference
                // child users berisi nama folder dari user yang ada di firebase realtime database
                // nama ini harus persis
                .child("users")
                .child(uidUser)

            // jika ada user yang login maka tombol delete akun bisa diklik
            // tombol delete akun setonclick untuk menghapus akun
            binding.btnDelete.setOnClickListener {
                // hapus akun start
                userInfo.removeValue()
                // hapus akun end

                // intent ke login activity
                val intent = Intent(this, LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                finish()
            }

            // ambil data dari userInfo
            userInfo.addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            // jadikan data dari firebase menjadi data class User
                            val user = snapshot.getValue(User::class.java)
                            binding.run {
                                // Masukkan data name, username, dan Bio ke dalam EditText
                                inputName.text = SpannableStringBuilder(user?.fullname)
                                inputUsername.text = SpannableStringBuilder(user?.username)
                                inputBio.text = SpannableStringBuilder(user?.Bio)
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                }
            )
        }
    }

    // buat private fungsi updateUserInfo() untuk menyimpan info user ke dalam database
    // private fun itu fungsi yang hanya bisa diakses oleh fungsi lain di dalam class yang sama
    private fun updateUserInfo() {
        // akses semua input text yang ada di account setting activity
        binding.run {
            val fullName = inputName.text.toString()
            val userName = inputUsername.text.toString()
            val userBio = inputBio.text.toString()

            // cek apakah semua data terisi
            if (fullName.isEmpty()) {
                Toast.makeText(
                    this@AccountSettingActivity,
                    "Nama Harus Diisi !",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return
            }
            if (userName.isEmpty()) {
                Toast.makeText(
                    this@AccountSettingActivity,
                    "Username Harus Diisi !",
                    Toast.LENGTH_SHORT
                )
                    .show()
                return
            }

            // buat userMap yang menyimpan data terupdate
            // nama variabel dalam userMap harus persis sama dengan yang ada di firebase
            val userMap = HashMap<String, Any>()
            userMap["fullname"] = fullName
            userMap["username"] = userName
            userMap["Bio"] = userBio

            // Update data yang ada pada firebase
            userInfo.updateChildren(userMap)
            Toast.makeText(this@AccountSettingActivity, "User Telah Diupdate", Toast.LENGTH_SHORT)
                .show()
        }
    }
}