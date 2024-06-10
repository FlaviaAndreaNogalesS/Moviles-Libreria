package com.example.libreria.mains

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libreria.adapters.GeneroAdapter
import com.example.libreria.databinding.ActivityGenerosBinding
import com.example.libreria.mains.viewmodels.GenerosViewModel
import com.example.libreria.models.Genero
import com.example.libreria.ui.AgregarGeneroActivity
import com.example.libreria.ui.EditarGeneroActivity

class GenerosActivity : AppCompatActivity(), GeneroAdapter.OnGeneroClickListener, GeneroAdapter.OnEditGeneroClickListener {

    private lateinit var binding: ActivityGenerosBinding
    private val viewModel: GenerosViewModel by viewModels()

    companion object {
        const val REQUEST_CODE_EDIT_GENERO = 101 // Código de solicitud para editar
        const val REQUEST_CODE_ADD_GENERO = 102 // Código de solicitud para agregar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenerosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewGeneros.layoutManager = LinearLayoutManager(this)

        // Observa la lista de géneros desde el ViewModel
        viewModel.generoList.observe(this) { generos ->
            generos?.let {
                binding.recyclerViewGeneros.adapter = GeneroAdapter(it, this, this)
            }
        }

        // Agrega listener al botón para agregar un nuevo género
        binding.btnAgregarGenero.setOnClickListener {
            val intent = Intent(this, AgregarGeneroActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_GENERO)
        }

        // Carga la lista de géneros al iniciar la actividad
        viewModel.fetchGeneros()
    }

    //pasa el id del genero al seleccionar un genero
    override fun onGeneroClick(genero: Genero) {
        val intent = Intent(this, LibrosPorGeneroActivity::class.java).apply {
            putExtra("generoId", genero.id)
        }
        startActivity(intent)
    }

    override fun onEditGeneroClick(genero: Genero) {
        val intent = Intent(this, EditarGeneroActivity::class.java).apply {
            putExtra("generoId", genero.id)
        }
        startActivityForResult(intent, REQUEST_CODE_EDIT_GENERO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_EDIT_GENERO && resultCode == Activity.RESULT_OK) {
            viewModel.fetchGeneros() //recarga
        } else if (requestCode == REQUEST_CODE_ADD_GENERO && resultCode == Activity.RESULT_OK) {
            viewModel.fetchGeneros()
        }
    }
}
