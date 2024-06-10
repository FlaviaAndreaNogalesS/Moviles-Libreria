package com.example.libreria.repositories

import com.example.libreria.API.APILibreria
import com.example.libreria.models.Genero
import com.example.libreria.models.Libro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LibroRepository {
    private val retrofit = RetrofitRepository.getRetrofitInstance()
    private val service = retrofit.create(APILibreria::class.java)

    fun getLibros(success: (List<Libro>?) -> Unit, failure: (Throwable) -> Unit) {
        service.getLibros().enqueue(object : Callback<List<Libro>> {
            override fun onResponse(call: Call<List<Libro>>, response: Response<List<Libro>>) {
                success(response.body())
            }

            override fun onFailure(call: Call<List<Libro>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getLibroById(id: Int, success: (Libro?) -> Unit, failure: (Throwable) -> Unit) {
        service.getLibroById(id).enqueue(object : Callback<Libro> {
            override fun onResponse(call: Call<Libro>, response: Response<Libro>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Libro>, t: Throwable) {
                failure(t)
            }
        })

    }

    fun insertLibro(libro: Libro, success: (Libro?) -> Unit, failure: (Throwable) -> Unit) {
        service.insertLibro(libro).enqueue(object : Callback<Libro> {
            override fun onResponse(call: Call<Libro>, response: Response<Libro>) {
                if (response.isSuccessful) {
                    val libroAgregado = response.body()
                    success(libroAgregado)
                } else {
                    failure(Exception("Error al agregar el libro"))
                }
            }

            override fun onFailure(call: Call<Libro>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateLibro(libro: Libro, success: () -> Unit, failure: (Throwable) -> Unit) {
        service.updateLibro(libro, libro.id).enqueue(object : Callback<Libro> {
            override fun onResponse(call: Call<Libro>, response: Response<Libro>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMessage = "Error al actualizar el libro: ${response.code()} - $errorBody"
                    failure(Exception(errorMessage))
                }
            }

            override fun onFailure(call: Call<Libro>, t: Throwable) {
                failure(t)
            }
        })
    }

}
