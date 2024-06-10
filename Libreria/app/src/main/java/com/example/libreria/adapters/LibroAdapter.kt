package com.example.libreria.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.libreria.databinding.LibroItemLayoutBinding
import com.example.libreria.models.Libro

class LibroAdapter(
    //pa mostrar la lista de libros al recyclerview
    private val libros: List<Libro>,
    private val listener: OnLibroClickListener //maneja clic en el libro
) : RecyclerView.Adapter<LibroAdapter.LibroViewHolder>() {

    interface OnLibroClickListener {
        fun onLibroClick(libro: Libro)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val binding = LibroItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LibroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = libros[position]
        holder.bind(libro, listener)
    }

    override fun getItemCount(): Int = libros.size

    class LibroViewHolder(private val binding: LibroItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(libro: Libro, listener: OnLibroClickListener) {
            binding.apply {
                txtNombreLibro.text = libro.nombre
                Glide.with(root.context)
                    .load(libro.imagen)
                    .into(imgLibro)
                root.setOnClickListener {
                    listener.onLibroClick(libro)
                }
            }
        }
    }
}
