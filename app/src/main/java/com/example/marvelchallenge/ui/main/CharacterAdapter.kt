package com.example.marvelchallenge.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marvelchallenge.databinding.ItemCharacterBinding
import com.example.marvelchallenge.network.Character

class CharacterAdapter(private val callback: CharacterViewHolder.CharacterCallback) : RecyclerView.Adapter<CharacterViewHolder>() {
    private var items: List<Character> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false), callback)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun setItems(characters: List<Character>) {
        items = characters
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size
}
