package com.dimi.animeapp.vm;

import android.app.Application;

import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.repository.AnimeListRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AnimeListViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private LiveData<List<Anime>> animeList;
    private AnimeListRepository animeListRepository;

    public AnimeListViewModel(@NonNull Application application) {
        super(application);
        animeListRepository = AnimeListRepository.getInstance();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Anime>> getAnimeList( String searchName) {
        isLoading.setValue(true);
        animeList = animeListRepository.getAnimeList( searchName );
        return animeList;
    }
}
