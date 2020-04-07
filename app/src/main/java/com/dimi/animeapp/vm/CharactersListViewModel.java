package com.dimi.animeapp.vm;

import android.app.Application;

import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.Character;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class CharactersListViewModel extends BaseViewModel {

    public CharactersListViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Character>> getCharactersList( Anime anime ) {
        setIsLoadingToTrue();
        return getBaseRepository().getCharactersList( anime );
    }

    public LiveData<Character> getCharacter( Character character ) {
        return getBaseRepository().getCharacter( character );
    }
}
