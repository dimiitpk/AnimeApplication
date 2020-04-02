package com.dimi.animeapp.vm;

import android.app.Application;

import com.dimi.animeapp.model.Anime;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class AnimeListViewModel extends BaseViewModel {

    public AnimeListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Anime>> getAnimeList( String searchName) {
        setIsLoadingToTrue();
        return getBaseRepository().getAnimeList(searchName);
    }
}
