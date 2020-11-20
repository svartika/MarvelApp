package com.example.networkcontroller;

import com.example.entitiy.models.MarvelCharactersLoadResponse;
import com.example.entitiy.models.MarvelComicsLoadResponse;
import com.example.entitiy.models.MarvelEventsLoadResponse;
import com.example.entitiy.models.MarvelSeriesLoadResponse;
import com.example.entitiy.models.MarvelStoriesLoadResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelRetrofitEndpointApi {


    @GET("/v1/public/characters")
    Observable<MarvelCharactersLoadResponse> loadCharacters();

    @GET("/v1/public/characters")
    Observable<MarvelCharactersLoadResponse> searchNameStartsWith(@Query("nameStartsWith") String nameStartsWith);

    @GET("/v1/public/characters/{characterId}/comics")
    Observable<MarvelComicsLoadResponse> loadComics(@Path("characterId") int characterId);

    @GET("/v1/public/characters/{characterId}/series")
    Observable<MarvelSeriesLoadResponse> loadSeries(@Path("characterId") int characterId);

    @GET("/v1/public/characters/{characterId}/stories")
    Observable<MarvelStoriesLoadResponse> loadStories(@Path("characterId") int characterId);

    @GET("/v1/public/characters/{characterId}/events")
    Observable<MarvelEventsLoadResponse> loadEvents(@Path("characterId") int characterId);

}
