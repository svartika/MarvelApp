package com.example.networkcontroller;

import com.example.entitiy.models.MarvelCharactersLoadResponse;
import com.example.entitiy.models.MarvelComicsLoadResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelRetrofitEndpointApi {


    @GET("/v1/public/characters")
    Observable<MarvelCharactersLoadResponse> loadCharacters();

    @GET("/v1/public/characters/{characterId}/comics")
    Observable<MarvelComicsLoadResponse> loadComics(@Path("characterId") int characterId);

    @GET("/v1/public/characters")
    Observable<MarvelCharactersLoadResponse> searchNameStartsWith(@Query("nameStartsWith") String nameStartsWith);
}
