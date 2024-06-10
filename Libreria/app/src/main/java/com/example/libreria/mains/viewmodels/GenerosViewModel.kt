package com.example.libreria.mains.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libreria.models.Genero
import com.example.libreria.repositories.GeneroRepository

class GenerosViewModel : ViewModel() {
    private val _generoList: MutableLiveData<List<Genero>> by lazy {
        MutableLiveData<List<Genero>>()
    }
    val generoList: LiveData<List<Genero>> get() = _generoList

    fun fetchGeneros() {
        GeneroRepository.getGeneros(
            success = { generos ->
                generos?.let {
                    _generoList.value = it
                }
            },
            failure = {
                it.printStackTrace()
            }
        )
    }
}
