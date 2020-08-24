package idn.fahru.instagramclone

import android.content.Intent
import android.os.Bundle
import android.text.SpannableStringBuilder
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import idn.fahru.instagramclone.databinding.ActivityAccountSettingBinding
import idn.fahru.instagramclone.model.User

class AccountSettingActivity : AppCompatActivity() {
    // viewbinding untuk activity_account_setting.xml
    private lateinit var binding: ActivityAccountSettingBinding

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

        // jika ada user yang login
        FirebaseAuth.getInstance().currentUser?.let { currentUser ->
            // dapatkan UID dari User yang login
            val uidUser = currentUser.uid
            // dapatkan user info yang login berdasarkan UIDnya
            val userInfo = FirebaseDatabase.getInstance()
                .reference
                .child("users")
                .child(uidUser)

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
}