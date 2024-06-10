package com.example.libreria.mains

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.libreria.databinding.ActivityDetalleLibroBinding
import com.example.libreria.mains.viewmodels.DetalleLibroViewModel
import com.example.libreria.models.Libro
import com.example.libreria.ui.ActualizarLibroActivity

class DetalleLibroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleLibroBinding
    private val viewModel: DetalleLibroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtiene los detalles del libro del intent
        val libroId = intent.getIntExtra("libroId", -1)
        if (libroId == -1) {
            Toast.makeText(this, "ID del libro no válido", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val nombre = intent.getStringExtra("nombre")
        val autor = intent.getStringExtra("autor")
        val editorial = intent.getStringExtra("editorial")
        val sinopsis = intent.getStringExtra("sinopsis")
        val isbn = intent.getStringExtra("isbn")
        val calificacion = intent.getIntExtra("calificacion", 0)
        val imagen = intent.getStringExtra("imagen")

        // Crea el objeto Libro y lo carga en el ViewModel
        val libro = Libro(libroId, nombre ?: "", autor ?: "", editorial ?: "", imagen ?: "", sinopsis ?: "", isbn ?: "", calificacion, listOf())
        viewModel.cargarLibro(libro)

        // Observa los cambios en el libro
        viewModel.libro.observe(this, Observer { libro ->
            libro?.let {
                // Muestra los detalles del libro
                binding.txtNombreLibro.text = it.nombre
                binding.txtAutor.text = "Autor: ${it.autor}"
                binding.txtEditorial.text = "Editorial: ${it.editorial}"
                binding.txtSinopsis.text = "Sinopsis: ${it.sinopsis}"
                binding.txtISBN.text = "ISBN: ${it.isbn}"
                binding.txtCalificacion.text = "Calificación: ${it.calificacion}"

                Glide.with(this)
                    .load(it.imagen)
                    .into(binding.imgLibro)

                // Configura el botón de editar y pasa los datos
                binding.btnEdit.setOnClickListener { _ ->
                    val intent = Intent(this, ActualizarLibroActivity::class.java).apply {
                        putExtra("libroId", it.id)
                        putExtra("nombre", it.nombre)
                        putExtra("autor", it.autor)
                        putExtra("editorial", it.editorial)
                        putExtra("sinopsis", it.sinopsis)
                        putExtra("isbn", it.isbn)
                        putExtra("calificacion", it.calificacion)
                        putExtra("imagen", it.imagen)
                    }
                    startActivity(intent)
                }
            }
        })
    }
}
