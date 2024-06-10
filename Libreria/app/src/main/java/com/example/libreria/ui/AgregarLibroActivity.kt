package com.example.libreria.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.libreria.databinding.ActivityAgregarLibroBinding
import com.example.libreria.models.Genero
import com.example.libreria.models.GeneroLibro
import com.example.libreria.models.Libro
import com.example.libreria.repositories.GeneroRepository
import com.example.libreria.repositories.LibroRepository

class AgregarLibroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarLibroBinding
    private var generos: List<Genero> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarLibroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtiene los géneros existentes de la API
        fetchGeneros()

        binding.btnGuardar.setOnClickListener {
            if (validarCampos()) {
                agregarLibro()
            } else {
                Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchGeneros() {
        GeneroRepository.getGeneros(
            success = { generosList ->
                generosList?.let {
                    generos = it
                    mostrarGenerosEnSpinner()
                }
            },
            failure = {
                it.printStackTrace()
            }
        )
    }

    private fun mostrarGenerosEnSpinner() {
        val generoNombres = generos.map { it.nombre }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, generoNombres)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerGeneros.adapter = adapter
    }

    private fun validarCampos(): Boolean {
        val nombre = binding.agregarNombreLibro.text.toString()
        val autor = binding.agregarAutor.text.toString()
        val editorial = binding.agregarEditorial.text.toString()
        val imagen = binding.agregarImagenUrl.text.toString()
        val sinopsis = binding.agregarSinopsis.text.toString()
        val isbn = binding.agregarISBN.text.toString()
        val calificacion = binding.agregarCalificacion.text.toString()
        val generoSeleccionadoNombre = binding.spinnerGeneros.selectedItem.toString()

        return !(nombre.isEmpty() || autor.isEmpty() || editorial.isEmpty() || imagen.isEmpty() ||
                sinopsis.isEmpty() || isbn.isEmpty() || calificacion.isEmpty() || generoSeleccionadoNombre.isEmpty())
    }

    private fun agregarLibro() {

        // Obtiene los datos del libro desde los campos de entrada
        val nombre = binding.agregarNombreLibro.text.toString()
        val autor = binding.agregarAutor.text.toString()
        val editorial = binding.agregarEditorial.text.toString()
        val imagen = binding.agregarImagenUrl.text.toString()
        val sinopsis = binding.agregarSinopsis.text.toString()
        val isbn = binding.agregarISBN.text.toString()
        val calificacion = binding.agregarCalificacion.text.toString().toInt()

        // Obtiene el género seleccionado
        val generoSeleccionadoNombre = binding.spinnerGeneros.selectedItem.toString()
        val generoSeleccionado = generos.find { it.nombre == generoSeleccionadoNombre }

        // Verifica si se encontró el género seleccionado
        if (generoSeleccionado != null) {
            // Crea un objeto Libro con los datos ingresados por el usuario y el género asociado
            val nuevoLibro = Libro(0, nombre, autor, editorial, imagen, sinopsis, isbn, calificacion, listOf(generoSeleccionado))

            // Llama al método para agregar el libro a la API
            LibroRepository.insertLibro(nuevoLibro, { libro ->
                Toast.makeText(this, "Libro agregado con éxito", Toast.LENGTH_SHORT).show()
                finish()
            }, { error ->
                Toast.makeText(this, "Error al agregar el libro", Toast.LENGTH_SHORT).show()
            })
        } else {
            Toast.makeText(this, "Género seleccionado no válido", Toast.LENGTH_SHORT).show()
        }
    }

}