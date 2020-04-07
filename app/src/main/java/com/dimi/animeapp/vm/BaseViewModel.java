package com.dimi.animeapp.vm;

import android.app.Application;

import com.dimi.animeapp.repository.BaseRepository;
import com.dimi.animeapp.vm.interfaces.LoadingIndicator;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

public abstract class BaseViewModel extends AndroidViewModel implements LoadingIndicator {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    private BaseRepository baseRepository;

    BaseViewModel(@NonNull Application application) {
        super(application);
        baseRepository = BaseRepository.getInstance();
    }

    public BaseRepository getBaseRepository() {
        return baseRepository;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    @Override
    public void setIsLoadingToTrue() {
        this.isLoading.postValue(true);
    }
    @Override
    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}
