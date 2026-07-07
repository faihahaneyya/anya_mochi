package com.example.anya_mochi.pertemuan_13

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.anya_mochi.databinding.FragmentTabCaptureBinding

class TabCaptureFragment : Fragment() {
    private var _binding: FragmentTabCaptureBinding? = null
    private val binding get() = _binding!!
    private var currentPhotoUri: Uri? = null

    // Launcher untuk menangani hasil tangkapan dari aplikasi kamera
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            currentPhotoUri?.let { uri ->
                binding.ivCapturedImage.setImageURI(uri) // Tampilkan foto ke ImageView
                
                // Mengirim broadcast agar galeri sistem langsung me-refresh dan memunculkan foto baru
                context?.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
            }
        }
    }

    // Launcher untuk meminta izin penggunaan kamera & storage secara runtime
    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        val cameraGranted = permissions[Manifest.permission.CAMERA] ?: false
        val storageGranted = permissions[Manifest.permission.WRITE_EXTERNAL_STORAGE] ?: (android.os.Build.VERSION.SDK_INT > 28)
        
        if (cameraGranted && storageGranted) {
            openCamera() // Jika diizinkan, langsung buka kamera
        } else {
            Toast.makeText(context, "Izin kamera dan penyimpanan diperlukan", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTabCaptureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnCapture.setOnClickListener {
            if (hasPermissions()) {
                openCamera() // Buka kamera jika izin sudah didapatkan sebelumnya
            } else {
                val permissionsToRequest = if (android.os.Build.VERSION.SDK_INT <= 28) {
                    arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                } else {
                    arrayOf(Manifest.permission.CAMERA)
                }
                permissionLauncher.launch(permissionsToRequest) // Minta izin jika belum ada
            }
        }
    }

    // Fungsi memeriksa status izin kamera dan storage
    private fun hasPermissions(): Boolean {
        val cameraGranted = ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
        
        val storageGranted = if (android.os.Build.VERSION.SDK_INT <= 28) {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
        
        return cameraGranted && storageGranted
    }

    // Fungsi memanggil Intent kamera bawaan
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        currentPhotoUri = createGalleryPhotoUri() // Siapkan lokasi file kosong
        intent.putExtra(MediaStore.EXTRA_OUTPUT, currentPhotoUri) // Masukkan lokasi target penyimpanan
        cameraLauncher.launch(intent)
    }

    // Fungsi generate nama file dan mendaftarkannya ke MediaStore (Galeri)
    private fun createGalleryPhotoUri(): Uri {
        val folderName = "TestCaptures" // Nama sub-folder di dalam directory Pictures
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "IMG_${System.currentTimeMillis()}.jpg") // Nama unik berdasarkan timestamp
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (android.os.Build.VERSION.SDK_INT >= 29) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/${folderName}") // Path folder tujuan
            } else {
                val dir = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_PICTURES)
                val file = java.io.File(dir, folderName)
                if (!file.exists()) file.mkdirs()
                put(MediaStore.Images.Media.DATA, java.io.File(file, "IMG_${System.currentTimeMillis()}.jpg").absolutePath)
            }
        }
        return requireContext().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            ?: throw RuntimeException("Gagal membuat URI MediaStore")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Hapus binding untuk mencegah memory leak
    }
}
