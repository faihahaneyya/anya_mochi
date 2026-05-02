package com.example.anya_mochi.Profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anya_mochi.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Logika Klik Instagram
        binding.tvInstagram.setOnClickListener {
            openUrl("https://www.instagram.com/faihaahy?igsh=c3Flazh6NG1hanhs")
        }

        // Logika Klik LinkedIn
        binding.tvLinkedin.setOnClickListener {
            openUrl("https://www.linkedin.com/in/faiha-haneyya-arrumaisha-46017b3a1")
        }

        // Logika Klik GitHub
        binding.tvGithub.setOnClickListener {
            openUrl("https://github.com/faihahaneyya")
        }
    }

    // Fungsi bantuan untuk membuka URL
    private fun openUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}