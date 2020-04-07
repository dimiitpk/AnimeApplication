package com.dimi.animeapp.vm.interfaces;

import androidx.lifecycle.MutableLiveData;

public interface LoadingIndicator {
    void setIsLoadingToTrue();

    MutableLiveData<Boolean> getIsLoading();
}
