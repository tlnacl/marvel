package com.example.marvelchallenge.network

data class Character(val id: Int, val name: String, val thumbnail: Thumbnail, val description: String)
data class Thumbnail(val path: String, val extension: String) {
    fun standardMedium() = "$path/standard_medium.$extension"
    fun landscapeAmazing() = "$path/landscape_amazing.$extension"
}

data class CharacterResponse<T>(val data: MarvelData<T>)
data class MarvelData<out T>(val offset: Int, val total: Int, val count: Int, val results: T)