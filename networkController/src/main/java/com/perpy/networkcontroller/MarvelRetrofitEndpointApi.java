package com.perpy.networkcontroller;

import com.perpy.entitiy.models.MarvelCharactersLoadResponse;
import com.perpy.entitiy.models.MarvelComicsLoadResponse;
import com.perpy.entitiy.models.MarvelEventsLoadResponse;
import com.perpy.entitiy.models.MarvelSeriesLoadResponse;
import com.perpy.entitiy.models.MarvelStoriesLoadResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelRetrofitEndpointApi {


    @GET("/v1/public/characters")
    Observable<MarvelCharactersLoadResponse> loadCharacters(@Query("offset") int offset, @Query("limit") int limit);

    @GET("/v1/public/characters")
    Observable<MarvelCharactersLoadResponse> searchNameStartsWith(@Query("nameStartsWith") String nameStartsWith, @Query("offset") int offset, @Query("limit") int limit);

    @GET("/v1/public/characters/{characterId}/comics")
    Observable<MarvelComicsLoadResponse> loadComics(@Path("characterId") int characterId);

    @GET("/v1/public/characters/{characterId}/series")
    Observable<MarvelSeriesLoadResponse> loadSeries(@Path("characterId") int characterId);

    @GET("/v1/public/characters/{characterId}/stories")
    Observable<MarvelStoriesLoadResponse> loadStories(@Path("characterId") int characterId);

    @GET("/v1/public/characters/{characterId}/events")
    Observable<MarvelEventsLoadResponse> loadEvents(@Path("characterId") int characterId);

    @GET("/v1/public/comics/{comicId}/characters")
    Observable<MarvelCharactersLoadResponse> loadCharactersForComic(@Path("comicId") int comicId);

    @GET("/v1/public/comics/{comicId}/series")
    Observable<MarvelSeriesLoadResponse> loadSeriesForComic(@Path("comicId") int comicId);

    @GET("/v1/public/comics/{comicId}/stories")
    Observable<MarvelStoriesLoadResponse> loadStoriesForComic(@Path("comicId") int comicId);

    @GET("/v1/public/comics/{comicId}/events")
    Observable<MarvelEventsLoadResponse> loadEventsForComic(@Path("comicId") int comicId);

    @GET("/v1/public/series/{seriesId}/characters")
    Observable<MarvelCharactersLoadResponse> loadCharactersForSeries(@Path("seriesId") int seriesId);

    @GET("/v1/public/series/{seriesId}/comics")
    Observable<MarvelComicsLoadResponse> loadComicsForSeries(@Path("seriesId") int seriesId);

    @GET("/v1/public/series/{seriesId}/stories")
    Observable<MarvelStoriesLoadResponse> loadStoriesForSeries(@Path("seriesId") int seriesId);

    @GET("/v1/public/series/{seriesId}/events")
    Observable<MarvelEventsLoadResponse> loadEventsForSeries(@Path("seriesId") int seriesId);

    @GET("/v1/public/stories/{storyId}/characters")
    Observable<MarvelCharactersLoadResponse> loadCharactersForStory(@Path("storyId") int storyId);

    @GET("/v1/public/stories/{storyId}/comics")
    Observable<MarvelComicsLoadResponse> loadComicsForStory(@Path("storyId") int storyId);

    @GET("/v1/public/stories/{storyId}/series")
    Observable<MarvelSeriesLoadResponse> loadSeriesForStory(@Path("storyId") int storyId);

    @GET("/v1/public/stories/{storyId}/events")
    Observable<MarvelEventsLoadResponse> loadEventsForStory(@Path("storyId") int storyId);

    @GET("/v1/public/events/{eventId}/characters")
    Observable<MarvelCharactersLoadResponse> loadCharactersForEvent(@Path("eventId") int eventId);

    @GET("/v1/public/events/{eventId}/comics")
    Observable<MarvelComicsLoadResponse> loadComicsForEvent(@Path("eventId") int eventId);

    @GET("/v1/public/events/{eventId}/series")
    Observable<MarvelSeriesLoadResponse> loadSeriesForEvent(@Path("eventId") int eventId);

    @GET("/v1/public/events/{eventId}/stories")
    Observable<MarvelStoriesLoadResponse> loadStoriesForEvent(@Path("eventId") int eventId);
}
