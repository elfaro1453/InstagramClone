package idn.fahru.instagramclone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import idn.fahru.instagramclone.databinding.ActivityAccountSettingBinding

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

    }
}
