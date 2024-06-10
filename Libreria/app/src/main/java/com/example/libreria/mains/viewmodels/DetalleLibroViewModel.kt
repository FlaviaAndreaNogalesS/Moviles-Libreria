package com.example.libreria.mains.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libreria.models.Libro


class DetalleLibroViewModel : ViewModel() {
    private val _libro = MutableLiveData<Libro>()
    val libro: LiveData<Libro> get() = _libro

    // carga el libro en el LiveData
    fun cargarLibro(libro: Libro) {
        _libro.value = libro
    }
}
