package com.example.marvelchallenge.network

import retrofit2.http.GET

interface MarvelApi {
    @GET("v1/public/characters")
    suspend fun getCharacters(): CharacterResponse
}