package com.dimi.animeapp.vm.interfaces;

import com.dimi.animeapp.model.Anime;

import androidx.lifecycle.LiveData;

public interface FullAnimeDetails {
    LiveData<Anime> getFullAnimeDetails( int AnimeID );
}
