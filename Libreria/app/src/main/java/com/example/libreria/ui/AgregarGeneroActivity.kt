package com.example.libreria.ui

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast
import com.example.libreria.databinding.ActivityAgregarGeneroBinding
import com.example.libreria.models.Genero
import com.example.libreria.repositories.GeneroRepository

class AgregarGeneroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAgregarGeneroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAgregarGeneroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGuardar.setOnClickListener {
            agregarGenero()
        }
    }

    private fun agregarGenero() {
        val nombre = binding.lblNombreGenero.text.toString()
        if (nombre.isNotEmpty()) {
            val nuevoGenero = Genero(0, nombre)
            GeneroRepository.insertGenero(nuevoGenero,
                success = {
                    Toast.makeText(this, "Género agregado con éxito", Toast.LENGTH_SHORT).show()
                    setResult(Activity.RESULT_OK)
                    finish()
                },
                failure = {
                    it.printStackTrace()
                    Toast.makeText(this, "Error al agregar el género", Toast.LENGTH_SHORT).show()
                })
        } else {
            Toast.makeText(this, "El nombre del género no puede estar vacío", Toast.LENGTH_SHORT).show()
        }
    }
}
