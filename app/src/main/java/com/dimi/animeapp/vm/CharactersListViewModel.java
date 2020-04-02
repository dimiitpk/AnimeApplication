package com.dimi.animeapp.vm;

import android.app.Application;

import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.Character;
import com.dimi.animeapp.repository.AnimeListRepository;
import com.dimi.animeapp.repository.CharactersListRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class CharactersListViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private LiveData<List<Character>> charactersList;
    private CharactersListRepository charactersListRepository;

    public CharactersListViewModel(@NonNull Application application) {
        super(application);
        charactersListRepository = CharactersListRepository.getInstance();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Character>> getCharactersList( Anime anime ) {
        isLoading.setValue(true);
        charactersList = charactersListRepository.getCharactersList( anime );
        return charactersList;
    }
}
