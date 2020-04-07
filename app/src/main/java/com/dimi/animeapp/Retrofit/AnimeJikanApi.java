package com.dimi.animeapp.Retrofit;

import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.SearchAnimeResponse;
import com.dimi.animeapp.model.Character;
import com.dimi.animeapp.model.CharacterListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnimeJikanApi {

    // https://api.jikan.moe/v3/top/anime/1/bypopularity top popular
    // https://api.jikan.moe/v3/anime/1535/videos promo video
    // https://api.jikan.moe/v3/anime/20/episodes/2 episodes, pages
    // https://api.jikan.moe/v3/top/anime/1/upcoming upcoming
    // https://api.jikan.moe/v3/top/anime/1/movie movies
    // https://api.jikan.moe/v3/schedule/monday schedule ako ne stavis nista onda je za celu nedelju
    // http://api.jikan.moe/v3/search/anime?order_by=score&sort=desc&page=2 TOP SCORES DESC

    //http://api.jikan.moe/v3/search/anime?order_by=score&sort=desc&genre=2&page=1
    @GET("search/anime")
    Call<SearchAnimeResponse> getAnimebyGenre (
            @Query("order_by") String order,
            @Query("sort") String sort,
            @Query("genre") int genre,
            @Query("page") int id
    );

    @GET("anime/{id}")
    Call<Anime> getVideos (
            @Path("id") int id
    );

    @GET( "character/{id}")
    Call<Character> getCharacter (
            @Path("id") int id
    );

    @GET("anime/{id}/characters_staff")
    Call<CharacterListResponse> getCharacterList (
            @Path("id") int id
    );

    @GET("search/anime")
    Call<SearchAnimeResponse> getAnimeByName (
            @Query("q") String name,
            @Query("page") int id
    );
}
