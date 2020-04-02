package com.dimi.animeapp.repository;

import com.dimi.animeapp.Retrofit.AnimeJikanApi;
import com.dimi.animeapp.Retrofit.AnimeRetrofit;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.AnimeAndMore;
import com.dimi.animeapp.model.Character;
import com.dimi.animeapp.model.CharactersAndMore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Singleton pattern
public class CharactersListRepository {

    private static CharactersListRepository instance;
    private MutableLiveData<List<Character>> charactersList = new MutableLiveData<>();
    private static final AnimeJikanApi animeJikanApi = AnimeRetrofit.APIService();

    public static CharactersListRepository getInstance() {
        if( instance == null ) {
            instance = new CharactersListRepository();
        }
        return instance;
    }

    public LiveData<List<Character>> getCharactersList( Anime anime ) {

        animeJikanApi.getCharacterList( anime.getMal_id() ).enqueue(new Callback<CharactersAndMore>() {
            @Override
            public void onResponse(@NotNull Call<CharactersAndMore> call, @NotNull Response<CharactersAndMore> response) {

                if( response.isSuccessful() && response.body() != null )
                    charactersList.setValue(response.body().getCharacters());
            }

            @Override
            public void onFailure(@NotNull Call<CharactersAndMore> call, @NotNull Throwable t) {

            }
        });
        return charactersList;
    }
}
