package com.example.libreria.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.libreria.databinding.ActivityEditarGeneroBinding
import com.example.libreria.models.Genero
import com.example.libreria.repositories.GeneroRepository

class EditarGeneroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditarGeneroBinding
    private var generoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarGeneroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        generoId = intent.getIntExtra("generoId", 0)

        // Carga el género desde la API
        fetchGeneroDetails(generoId)

        binding.btnGuardar.setOnClickListener {
            actualizarGenero()
        }
    }

    private fun fetchGeneroDetails(id: Int) {
        GeneroRepository.getGeneroById(id,
            success = { genero ->
                genero?.let {
                    binding.editarNombreGenero.setText(it.nombre)
                }
            },
            failure = {
                it.printStackTrace()
                Toast.makeText(this, "Error al cargar el género", Toast.LENGTH_SHORT).show()
                finish()
            })
    }

    private fun actualizarGenero() {
        val nombre = binding.editarNombreGenero.text.toString()
        if (nombre.isNotEmpty()) {
            val generoActualizado = Genero(generoId, nombre)
            GeneroRepository.updateGenero(generoActualizado,
                success = {
                    Toast.makeText(this, "Género actualizado con éxito", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                    finish()
                },
                failure = {
                    it.printStackTrace()
                    Toast.makeText(this, "Error al actualizar el género", Toast.LENGTH_SHORT).show()
                })
        } else {
            Toast.makeText(this, "El nombre del género no puede estar vacío", Toast.LENGTH_SHORT).show()
        }
    }


}
