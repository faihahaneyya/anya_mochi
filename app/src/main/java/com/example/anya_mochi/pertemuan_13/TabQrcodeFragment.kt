package com.example.anya_mochi.pertemuan_13

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.anya_mochi.databinding.FragmentTabQrcodeBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

class TabQrcodeFragment : Fragment() {
    private var _binding: FragmentTabQrcodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTabQrcodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnGenerate.setOnClickListener {
            val text = binding.edtQrInput.text.toString().trim() // Ambil input teks
            if (text.isEmpty()) return@setOnClickListener // Validasi jika kosong
            
            binding.ivQrCode.setImageBitmap(createQR(text)) // Tampilkan bitmap QR Code
        }
    }

    // Fungsi inti mengubah string menjadi matriks pixel gambar QR Code
    private fun createQR(text: String): Bitmap {
        val writer = QRCodeWriter()
        val matrix = writer.encode(
            text,
            BarcodeFormat.QR_CODE,
            500, // Lebar resolusi matriks
            500, // Tinggi resolusi matriks
            mapOf(EncodeHintType.CHARACTER_SET to "UTF-8") // Set character encoding
        )
        
        // Membuat object bitmap kosong berukuran 500x500 px
        return Bitmap.createBitmap(500, 500, Bitmap.Config.RGB_565).apply {
            // Looping koordinat X dan Y untuk memberikan warna hitam/putih sesuai isi data matriks
            for (x in 0 until 500) {
                for (y in 0 until 500) {
                    setPixel(x, y, if (matrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
