package com.example.libreria.repositories

import com.example.libreria.API.APILibreria
import com.example.libreria.models.Genero
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object GeneroRepository {
    private val retrofit = RetrofitRepository.getRetrofitInstance()
    private val service = retrofit.create(APILibreria::class.java)

    fun getGeneros(success: (List<Genero>?) -> Unit, failure: (Throwable) -> Unit) {
        service.getGeneros().enqueue(object : Callback<List<Genero>> {
            override fun onResponse(call: Call<List<Genero>>, response: Response<List<Genero>>) {
                success(response.body())
            }

            override fun onFailure(call: Call<List<Genero>>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getGeneroById(id: Int, success: (Genero?) -> Unit, failure: (Throwable) -> Unit) {
        service.getGeneroById(id).enqueue(object : Callback<Genero> {
            override fun onResponse(call: Call<Genero>, response: Response<Genero>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Genero>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun updateGenero(genero: Genero, success: () -> Unit, failure: (Throwable) -> Unit) {
        service.updateGenero(genero.id, genero).enqueue(object : Callback<Genero> {
            override fun onResponse(call: Call<Genero>, response: Response<Genero>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Exception("Error al actualizar el género"))
                }
            }

            override fun onFailure(call: Call<Genero>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun insertGenero(genero: Genero, success: () -> Unit, failure: (Throwable) -> Unit) {
        service.insertGenero(genero).enqueue(object : Callback<Genero> {
            override fun onResponse(call: Call<Genero>, response: Response<Genero>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    failure(Exception("Error al agregar el género"))
                }
            }

            override fun onFailure(call: Call<Genero>, t: Throwable) {
                failure(t)
            }
        })
    }

}

