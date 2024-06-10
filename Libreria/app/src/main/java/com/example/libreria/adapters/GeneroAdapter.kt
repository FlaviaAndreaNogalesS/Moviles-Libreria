package com.example.libreria.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.libreria.databinding.GeneroItemLayoutBinding
import com.example.libreria.models.Genero

class GeneroAdapter(
    //mostrar lista de generos
    private val generos: List<Genero>,
    private val listener: OnGeneroClickListener, //clic al genero
    private val editListener: OnEditGeneroClickListener //clic al editar
) : RecyclerView.Adapter<GeneroAdapter.GeneroViewHolder>() {

    interface OnGeneroClickListener {
        fun onGeneroClick(genero: Genero)
    }

    interface OnEditGeneroClickListener {
        fun onEditGeneroClick(genero: Genero)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneroViewHolder {
        val binding = GeneroItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GeneroViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GeneroViewHolder, position: Int) {
        val genero = generos[position]
        holder.bind(genero, listener, editListener)
    }

    override fun getItemCount(): Int = generos.size

    class GeneroViewHolder(private val binding: GeneroItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(genero: Genero, listener: OnGeneroClickListener, editListener: OnEditGeneroClickListener) {
            binding.txtNombreGenero.text = genero.nombre
            binding.root.setOnClickListener {
                listener.onGeneroClick(genero)
            }
            binding.btnEditar.setOnClickListener {
                editListener.onEditGeneroClick(genero)
            }
        }
    }
}
