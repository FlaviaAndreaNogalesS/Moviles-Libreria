package com.example.libreria.API

import com.example.libreria.models.Genero
import com.example.libreria.models.Libro
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APILibreria {

    @GET("libros")
    fun getLibros(): Call<List<Libro>>

    @GET("libros/{id}")
    fun getLibroById(@Path("id") id: Int): Call<Libro>

    @POST("libros")
    fun insertLibro(@Body libro: Libro): Call<Libro>

    @PUT("libros/{id}")
    fun updateLibro(@Body libro: Libro, @Path("id") id: Int): Call<Libro>

    @GET("generos")
    fun getGeneros(): Call<List<Genero>>

    @GET("generos/{id}")
    fun getGeneroById(@Path("id") id: Int): Call<Genero>

    @PUT("generos/{id}")
    fun updateGenero(@Path("id") id: Int, @Body genero: Genero): Call<Genero>

    @POST("generos")
    fun insertGenero(@Body genero: Genero): Call<Genero>

}