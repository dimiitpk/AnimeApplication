package com.dimi.animeapp.repository;

import android.os.Handler;
import android.util.Log;

import com.dimi.animeapp.Retrofit.AnimeJikanApi;
import com.dimi.animeapp.Retrofit.AnimeRetrofit;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.SearchAnimeResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreRepository {

    private int GENRE_ID;
    private static final String TAG = "GenreRepository";
    private static final AnimeJikanApi animeJikanApi = AnimeRetrofit.APIService();

    public int getGENRE_ID() {
        return GENRE_ID;
    }

    public void setGENRE_ID(int GENRE_ID) {
        this.GENRE_ID = GENRE_ID;
    }

    private MutableLiveData<List<Anime>> animGenreList = new MutableLiveData<>();

    public GenreRepository() {}

    public GenreRepository(int GENRE_ID) {
        this.GENRE_ID = GENRE_ID;
        animGenreList = getAnimeGenreList( GENRE_ID );
    }

    public MutableLiveData<List<Anime>> getAnimeGenreList(int genre) { // stavljen delay, api salje 429 code = to many requests

        new Handler().postDelayed(() -> animeJikanApi.getAnimebyGenre("score", "desc", genre, 1).enqueue(new Callback<SearchAnimeResponse>() {
            @Override
            public void onResponse(Call<SearchAnimeResponse> call, Response<SearchAnimeResponse> response) {
                if (response.isSuccessful() && response.body() != null)
                    animGenreList.postValue(response.body().getAnimes());
                else
                    Log.d(TAG, "onResponse(getAnimebyGenre) Code: " + response.code());
            }

            @Override
            public void onFailure(Call<SearchAnimeResponse> call, Throwable t) {
                Log.d(TAG, "onFailure(getAnimebyGenre): " + t.getMessage());
            }
        }),1000);
        return animGenreList;
    }

    public LiveData<Anime> getFullAnimeDetails(int animeID) {
        MutableLiveData<Anime> video = new MutableLiveData<>();
        animeJikanApi.getVideos(animeID).enqueue(new Callback<Anime>() {
            @Override
            public void onResponse(Call<Anime> call, Response<Anime> response) {
                if (response.isSuccessful() && response.body() != null)
                    video.setValue(response.body());
                else
                    Log.d(TAG, "onResponse(getVideo) Code: " + response.code());
            }

            @Override
            public void onFailure(Call<Anime> call, Throwable t) {
                Log.d(TAG, "onFailure(getVideo): " + t.getMessage());
            }
        });
        return video;
    }
}
