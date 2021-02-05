package com.example.marvelchallenge.network

import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApi {
    @GET("v1/public/characters")
    suspend fun getCharacters(): CharacterResponse<List<Character>>

    @GET("v1/public/characters/{characterId}")
    suspend fun getCharacter(@Path("characterId") characterId: Int): CharacterResponse<List<Character>>
}