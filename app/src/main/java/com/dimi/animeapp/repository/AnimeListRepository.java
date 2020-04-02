package com.dimi.animeapp.repository;

import com.dimi.animeapp.Retrofit.AnimeJikanApi;
import com.dimi.animeapp.Retrofit.AnimeRetrofit;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.AnimeAndMore;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Singleton pattern
public class AnimeListRepository {

    private static AnimeListRepository instance;
    private MutableLiveData<List<Anime>> animListLiveData = new MutableLiveData<>();
    private static final AnimeJikanApi animeJikanApi = AnimeRetrofit.APIService();

    public static AnimeListRepository getInstance() {
        if( instance == null ) {
            instance = new AnimeListRepository();
        }
        return instance;
    }

    public LiveData<List<Anime>> getAnimeList(String searchName ) {

        animeJikanApi.getAnimeByName(searchName, 1).enqueue(new Callback<AnimeAndMore>() {
            @Override
            public void onResponse(@NotNull Call<AnimeAndMore> call, @NotNull Response<AnimeAndMore> response) {

                if( response.isSuccessful() && response.body() != null )
                    animListLiveData.setValue(response.body().getAnimes());
            }

            @Override
            public void onFailure(@NotNull Call<AnimeAndMore> call, @NotNull Throwable t) {

            }
        });
        return animListLiveData;
    }
}
