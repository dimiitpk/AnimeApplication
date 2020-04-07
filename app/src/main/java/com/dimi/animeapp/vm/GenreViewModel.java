package com.dimi.animeapp.vm;

import android.app.Application;

import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.repository.GenreRepository;
import com.dimi.animeapp.vm.interfaces.FullAnimeDetails;
import com.dimi.animeapp.vm.interfaces.LoadingIndicator;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class GenreViewModel extends AndroidViewModel implements LoadingIndicator, FullAnimeDetails {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    private GenreRepository genreRepository;

    public GenreViewModel(@NonNull Application application) {
        super(application);
        genreRepository = new GenreRepository();
    }

    public void createGenreRepository( int genreID ) {
        if( genreRepository == null )
            genreRepository = new GenreRepository( genreID );
        else
            genreRepository.setGENRE_ID(genreID);
    }

    public GenreRepository getGenreRepository() {
        return genreRepository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<List<Anime>> getAnimeGenreList(int genreID ) {

        if( genreRepository == null )
            genreRepository = new GenreRepository( genreID );
        else genreRepository.setGENRE_ID(genreID);

        return genreRepository.getAnimeGenreList( genreID );
    }

    @Override
    public void setIsLoadingToTrue() {
        this.isLoading.postValue(true);
    }

    @Override
    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    @Override
    public LiveData<Anime> getFullAnimeDetails( int AnimeID ) {
        setIsLoadingToTrue();
        return genreRepository.getFullAnimeDetails( AnimeID );
    }
}
