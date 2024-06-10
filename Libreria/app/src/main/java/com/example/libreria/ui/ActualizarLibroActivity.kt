package com.example.libreria.ui

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.libreria.databinding.ActivityActualizarLibroBinding
import com.example.libreria.models.Libro
import com.example.libreria.repositories.LibroRepository

class ActualizarLibroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityActualizarLibroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActualizarLibroBinding.inflate(layoutInflater)
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

        // Llena los campos con la información del libro
        binding.editNombreLibro.setText(nombre)
        binding.editAutor.setText(autor)
        binding.editEditorial.setText(editorial)
        binding.editSinopsis.setText(sinopsis)
        binding.editISBN.setText(isbn)
        binding.editCalificacion.setText(calificacion.toString())
        binding.editImagenUrl.setText(imagen)

        // botón de guardar
        binding.btnGuardar.setOnClickListener {
            val updatedLibro = Libro(
                id = libroId,
                nombre = binding.editNombreLibro.text.toString(),
                autor = binding.editAutor.text.toString(),
                editorial = binding.editEditorial.text.toString(),
                sinopsis = binding.editSinopsis.text.toString(),
                isbn = binding.editISBN.text.toString(),
                calificacion = binding.editCalificacion.text.toString().toInt(),
                imagen = binding.editImagenUrl.text.toString(),
                generos = listOf()
            )

            // Llama al repositorio para actualizar el libro
            LibroRepository.updateLibro(updatedLibro,
                success = {
                    Toast.makeText(this, "Libro actualizado con éxito", Toast.LENGTH_SHORT).show()
                    finish()
                },
                failure = {
                    Toast.makeText(this, "Error al actualizar el libro", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}
