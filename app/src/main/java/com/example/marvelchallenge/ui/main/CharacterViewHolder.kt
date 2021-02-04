package com.example.marvelchallenge.ui.main

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.marvelchallenge.databinding.ItemCharacterBinding
import com.example.marvelchallenge.network.Character

class CharacterViewHolder(private val binding: ItemCharacterBinding, private val callback: CharacterCallback) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(character: Character) {
        binding.image.load(character.thumbnail.standardMedium())
        binding.name.text = character.name
        binding.root.setOnClickListener { callback.onCharacterClicked(character) }
    }

    interface CharacterCallback {
        fun onCharacterClicked(character: Character)
    }
}