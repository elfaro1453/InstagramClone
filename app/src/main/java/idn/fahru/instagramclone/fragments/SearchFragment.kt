package idn.fahru.instagramclone.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import idn.fahru.instagramclone.databinding.FragmentSearchBinding
import idn.fahru.instagramclone.model.User
import idn.fahru.instagramclone.recyclerview.adapter.ItemUserAdapter

class SearchFragment : Fragment() {

    // view binding untuk fragment_search.xml
    private lateinit var binding: FragmentSearchBinding

    // variabel adapterRV yang berisi ItemUserAdapter
    private lateinit var adapterRV: ItemUserAdapter

    // fragment binding diawali di onCreateView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // viewbinding fragmentsearch
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        // inisialisasi adapter terlebih dahulu
        adapterRV = ItemUserAdapter()
        // setting recyclerview di onCreateView
        binding.rvSearch.run {
            setHasFixedSize(true)
            // gunakan layoutmanager Grid sehingga terbagi 2
            layoutManager = GridLayoutManager(context, 2)
            adapter = adapterRV
        }
        return binding.root
    }

    // onViewCreated itu artinya setelah tampilan dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // jalankan getUsers di awal
        getUsers()

        // mendeteksi perubahan keyword pada searchView
        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // Jika keyword dirubah dan tombol enter ditekan maka fungsi ini berjalan
                    Toast.makeText(view.context, query.toString(), Toast.LENGTH_SHORT).show()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Fungsi akan berjalan setiap kali ada perubahan keyword
                    return false
                }
            }
        )
    }

    // buat fungsi mendapatkan semua user dari firebase realtime database
    private fun getUsers() {
        val usersDB = FirebaseDatabase.getInstance()
            .reference
            // ambil data dari tabel users
            .child("users")
            // urutkan berdasarkan fullname
            .orderByChild("fullname")
            // batasi 10 data pertama
            .limitToFirst(10)

        usersDB.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        Log.e("SnapshotExist", "Yes")
                        Log.e("SnapshotSum", snapshot.childrenCount.toString())
                        // buat variabel sebagai wadah dari data user
                        val listUser = arrayListOf<User>()
                        // snapshot berisi 10 data sesuai dengan limitnya
                        // lakukan perulangan pada tiap data
                        for (data in snapshot.children) {
                            val user = data.getValue(User::class.java) as User
                            listUser.add(user)
                        }
                        adapterRV.addData(listUser)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("SnapshotError", error.details)
                }
            }
        )
    }

}
