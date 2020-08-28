package idn.fahru.instagramclone.recyclerview.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import idn.fahru.instagramclone.databinding.ItemUserBinding
import idn.fahru.instagramclone.model.User

/**
 * Created by Imam Fahrur Rofi on 28/08/2020.
 */

class ItemUserVH(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User) {
        binding.run {
            // mengisi textview dengan data yang dibutuhkan
            txtName.text = user.fullname
            txtUsername.text = user.username

            Glide.with(binding.root)
                // load diisi oleh url dari gambar
                .load(user.image)
                // CircleCrop agar gambar yang dimasukkan ke dalam glide berbentuk lingkaran
                .circleCrop()
                // into diisi oleh viewnya
                .into(imgProfile)
        }
    }
}