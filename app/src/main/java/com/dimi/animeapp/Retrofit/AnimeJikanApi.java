package com.dimi.animeapp.Retrofit;

import com.dimi.animeapp.model.AnimeAndMore;
import com.dimi.animeapp.model.CharactersAndMore;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface AnimeJikanApi {


    @GET("anime/{id}/characters_staff")
    Call<CharactersAndMore> getCharacterList (
            @Path("id") int id
    );

    @GET("search/anime")
    Call<AnimeAndMore> getAnimeByName (
            @Query("q") String name,
            @Query("page") int id
    );
}
