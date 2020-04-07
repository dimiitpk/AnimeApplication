package com.dimi.animeapp.repository;

import android.util.Log;

import com.dimi.animeapp.Retrofit.AnimeJikanApi;
import com.dimi.animeapp.Retrofit.AnimeRetrofit;
import com.dimi.animeapp.Retrofit.JikanApiUtils;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.SearchAnimeResponse;
import com.dimi.animeapp.model.Character;
import com.dimi.animeapp.model.CharacterListResponse;
import com.dimi.animeapp.model.User;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import android.os.Handler;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// Singleton pattern
public class BaseRepository {


    private MutableLiveData<List<Anime>> animListLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Character>> charactersList = new MutableLiveData<>();
    private static BaseRepository instance;
    private static final AnimeJikanApi animeJikanApi = AnimeRetrofit.APIService();
    private static final String TAG = "BaseRepository";

    public static BaseRepository getInstance() {
        if (instance == null) {
            instance = new BaseRepository();
        }
        return instance;
    }

    public LiveData<List<Anime>> getAnimeList(String searchName) {

        animeJikanApi.getAnimeByName(searchName, 1).enqueue(new Callback<SearchAnimeResponse>() {
            @Override
            public void onResponse(Call<SearchAnimeResponse> call, Response<SearchAnimeResponse> response) {

                if (response.isSuccessful() && response.body() != null)
                    animListLiveData.setValue(response.body().getAnimes());
                else
                    Log.d(TAG, "onResponse(getAnimeList) Code: " + response.code());
            }

            @Override
            public void onFailure(Call<SearchAnimeResponse> call, Throwable t) {
                Log.d(TAG, "onFailure(getAnimeList): " + t.getMessage());
            }
        });
        return animListLiveData;
    }

    public LiveData<List<Character>> getCharactersList(Anime anime) {
        animeJikanApi.getCharacterList(anime.getMal_id()).enqueue(new Callback<CharacterListResponse>() {
            @Override
            public void onResponse(@NotNull Call<CharacterListResponse> call, @NotNull Response<CharacterListResponse> response) {

                if (response.isSuccessful() && response.body() != null)
                    charactersList.setValue(response.body().getCharacters());
                else
                    Log.d(TAG, "onResponse(getCharactersList) Code: " + response.code());
            }

            @Override
            public void onFailure(@NotNull Call<CharacterListResponse> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure(getCharactersList): " + t.getMessage());
            }
        });
        return charactersList;
    }


    public LiveData<Character> getCharacter(Character character) {

        MutableLiveData<Character> character1 = new MutableLiveData<>();
        animeJikanApi.getCharacter(character.getMal_id()).enqueue(new Callback<Character>() {
            @Override
            public void onResponse(Call<Character> call, Response<Character> response) {
                if (response.isSuccessful() && response.body() != null)
                    character1.setValue(response.body());
                else
                    Log.d(TAG, "onResponse(getCharacter) Code: " + response.code());

            }

            @Override
            public void onFailure(Call<Character> call, Throwable t) {
                Log.d(TAG, "onFailure(getCharacter): " + t.getMessage());
            }
        });
        return character1;
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
