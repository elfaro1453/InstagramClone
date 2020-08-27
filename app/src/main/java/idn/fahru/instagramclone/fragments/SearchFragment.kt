package idn.fahru.instagramclone.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import idn.fahru.instagramclone.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {

    // view binding untuk fragment_search.xml
    private lateinit var binding: FragmentSearchBinding

    // fragment binding diawali di onCreateView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // viewbinding fragmentsearch
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    // onViewCreated itu artinya setelah tampilan dibuat
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // mendeteksi perubahan keyword pada searchView
        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // Jika keyword dirubah dan tombol enter ditekan maka fungsi ini berjalan
                    Toast.makeText(view.context, query.toString(), Toast.LENGTH_SHORT).show()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            }
        )
    }

}
