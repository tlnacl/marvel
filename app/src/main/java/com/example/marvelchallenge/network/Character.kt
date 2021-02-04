package com.example.marvelchallenge.network

data class Character(val id: Int, val name: String, val thumbnail: Thumbnail)
data class Thumbnail(val path: String, val extension: String) {
    fun standardMedium() = "$path/standard_medium.$extension"
}

data class CharacterResponse(val data: MarvelData<Character>)
data class MarvelData<out T>(val offset: Int, val total: Int, val count: Int, val results: List<T>)