package com.example.libreria.mains.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libreria.models.Libro
import com.example.libreria.repositories.LibroRepository

class MainViewModel : ViewModel() {
    private val libroRepository = LibroRepository

    private val _libros = MutableLiveData<List<Libro>?>()
    val libros: LiveData<List<Libro>?> = _libros

    // obtiene los libros desde el repositorio
    fun fetchLibros() {
        libroRepository.getLibros(
            success = { libros ->
                _libros.postValue(libros)
            },
            failure = { error ->
                error.printStackTrace()
            }
        )
    }
}
