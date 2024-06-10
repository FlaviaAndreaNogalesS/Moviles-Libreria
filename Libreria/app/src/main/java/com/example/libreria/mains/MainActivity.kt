package com.example.libreria.mains

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.libreria.adapters.LibroAdapter
import com.example.libreria.databinding.ActivityMainBinding
import com.example.libreria.mains.viewmodels.MainViewModel
import com.example.libreria.models.Libro
import com.example.libreria.ui.AgregarLibroActivity

class MainActivity : AppCompatActivity(), LibroAdapter.OnLibroClickListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerViewLibros.layoutManager = LinearLayoutManager(this)

        binding.btnGeneros.setOnClickListener {
            val intent = Intent(this, GenerosActivity::class.java)
            startActivity(intent)
        }

        binding.btnAgregar.setOnClickListener {
            val intent = Intent(this, AgregarLibroActivity::class.java)
            startActivity(intent)
        }

        // Observa los cambios en la lista de libros
        viewModel.libros.observe(this, Observer { libros ->
            libros?.let {
                // Ordena los libros por calificación descendente
                val librosOrdenados = it.sortedByDescending { libro -> libro.calificacion }
                binding.recyclerViewLibros.adapter = LibroAdapter(librosOrdenados, this)
            }
        })

        // Carga los libros
        viewModel.fetchLibros()
    }

    //maneja el clic en un libro
    override fun onLibroClick(libro: Libro) {
        val intent = Intent(this, DetalleLibroActivity::class.java).apply {
            //pasa los detalles del libro seleccionado
            putExtra("libroId", libro.id)
            putExtra("nombre", libro.nombre)
            putExtra("autor", libro.autor)
            putExtra("editorial", libro.editorial)
            putExtra("sinopsis", libro.sinopsis)
            putExtra("isbn", libro.isbn)
            putExtra("calificacion", libro.calificacion)
            putExtra("imagen", libro.imagen)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchLibros() // Vuelve a cargar los libros
    }
}
