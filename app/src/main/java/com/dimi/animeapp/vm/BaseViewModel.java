package com.dimi.animeapp.vm;

import android.app.Application;

import com.dimi.animeapp.repository.BaseRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public class BaseViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private BaseRepository baseRepository;

    BaseViewModel(@NonNull Application application) {
        super(application);
        baseRepository = BaseRepository.getInstance();
    }

    BaseRepository getBaseRepository() {
        return baseRepository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    void setIsLoadingToTrue() {
        this.isLoading.setValue(true);
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
