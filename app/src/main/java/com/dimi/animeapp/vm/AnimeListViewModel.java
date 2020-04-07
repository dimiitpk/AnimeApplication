package com.dimi.animeapp.vm;

import android.app.Application;

import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.vm.interfaces.FullAnimeDetails;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AnimeListViewModel extends BaseViewModel implements FullAnimeDetails {

    private MutableLiveData<String> searchAnimeName = new MutableLiveData<>();

    public AnimeListViewModel(@NonNull Application application) {
        super(application);
        searchAnimeName.postValue( "Naruto" );
    }

    public void setSearchAnimeName( String searchAnimeName) {
        this.searchAnimeName.postValue( searchAnimeName );
    }

    public MutableLiveData<String> getSearchAnimeName() {
        return searchAnimeName;
    }

    public LiveData<List<Anime>> getAnimeList( String searchAnimeName ) {
        setIsLoadingToTrue();
        return getBaseRepository().getAnimeList( searchAnimeName );
    }

    @Override
    public LiveData<Anime> getFullAnimeDetails( int AnimeID ) {
        setIsLoadingToTrue();
        return getBaseRepository().getFullAnimeDetails( AnimeID );
    }
}
