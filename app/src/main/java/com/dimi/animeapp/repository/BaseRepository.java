package com.dimi.animeapp.repository;

import android.util.Log;

import com.dimi.animeapp.Retrofit.AnimeJikanApi;
import com.dimi.animeapp.Retrofit.AnimeRetrofit;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.AnimeAndMore;
import com.dimi.animeapp.model.Character;
import com.dimi.animeapp.model.CharactersAndMore;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Singleton pattern
public class BaseRepository {


    private MutableLiveData<List<Anime>> animListLiveData = new MutableLiveData<>();
    private static BaseRepository instance;
    private static final AnimeJikanApi animeJikanApi = AnimeRetrofit.APIService();

    public static BaseRepository getInstance() {
        if( instance == null ) {
            instance = new BaseRepository();
        }
        return instance;
    }

    public LiveData<List<Anime>> getAnimeList(String searchName ) {

        Log.d("SVE", "onCreateSEAC4: ");
        animeJikanApi.getAnimeByName(searchName, 1).enqueue(new Callback<AnimeAndMore>() {
            @Override
            public void onResponse(Call<AnimeAndMore> call, Response<AnimeAndMore> response) {

                if( response.isSuccessful() && response.body() != null )
                    animListLiveData.setValue(response.body().getAnimes());
            }

            @Override
            public void onFailure(Call<AnimeAndMore> call, Throwable t) {
                Log.d("SVE", "onFailure: " + t.getMessage());
            }
        });
        return animListLiveData;
    }

    MutableLiveData<List<Character>> charactersList = new MutableLiveData<>();
    public LiveData<List<Character>> getCharactersList(Anime anime ) {
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


    MutableLiveData<Character> character1 = new MutableLiveData<>();
    public LiveData<Character> getCharacter( Character character ) {

        animeJikanApi.getCharacter( character.getMal_id() ).enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                if( response.isSuccessful() && response.body() != null )
                    character1.setValue(response.body());
            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {

            }
        });
        return character1;
    }
}
