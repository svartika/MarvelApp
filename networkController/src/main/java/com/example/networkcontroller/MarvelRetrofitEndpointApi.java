package com.example.networkcontroller;

import com.example.entitiy.models.MarvelCharacterResponse;
import com.example.entitiy.models.MarvelCharactersLoadResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelRetrofitEndpointApi {


    @GET("/v1/public/characters")
    Observable<MarvelCharactersLoadResponse> loadCharacters();

    @GET("/v1/public/characters/{characterId}")
    Observable<MarvelCharacterResponse> loadCharacter(@Path("characterId") String characterId);

    @GET("/v1/public/characters")
    Observable<MarvelCharactersLoadResponse> searchNameStartsWith(@Query("nameStartsWith") String nameStartsWith);
}
