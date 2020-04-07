package com.dimi.animeapp.model;

import android.util.Log;

import com.dimi.animeapp.Retrofit.JikanApiUtils;
import com.dimi.animeapp.repository.BaseRepository;
import com.dimi.animeapp.ui.fragments.AnimeGenreFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class User {

    private static User INSTANCE;

    private List<AnimeGenreFragment> genreFragmentList = new ArrayList<>();

    public List<AnimeGenreFragment> getGenreFragmentList() {
        return genreFragmentList;
    }

    private User() {}

    public static User getInstance() {
        if (INSTANCE == null)
            INSTANCE = new User();

        if( genreIDs == null ) {
            genreIDs = new ArrayList<>();
            genreIDs.add(JikanApiUtils.GENRE_ACTION);
            genreIDs.add(JikanApiUtils.GENRE_ROMANCE);
            genreIDs.add(JikanApiUtils.GENRE_SPORTS);
            //genreIDs.add(JikanApiUtils.GENRE_COMEDY);
        }
        return INSTANCE;
    }

    private static ArrayList<Integer> genreIDs;

    public int getGenreIndex(int oldGenreID ) {
        int index = -1;
        for( int i = 0; i < genreIDs.size(); i++ ) {
            if( genreIDs.get(i) == oldGenreID ) {
                index = i;
                break;
            }
        }
        return index;
    }

    public int getGenreListSize() {
        return genreIDs.size();
    }
    public int getGenreID( int index ) {
        return genreIDs.get(index);
    }

    public boolean isAlreadyChosen( int genreID ) {
        boolean isAlreadyChosenGenre = false;
        for ( AnimeGenreFragment animeGenreFragment : getGenreFragmentList()) {
            if (animeGenreFragment.getGenreID() == genreID) {
                isAlreadyChosenGenre = true;
                break;
            }
        }
        return isAlreadyChosenGenre;
    }

    public int setGenreID( int index, int genreID) {
        int returnValue = -1;
        for ( AnimeGenreFragment animeGenreFragment : getGenreFragmentList()) {
            if( animeGenreFragment.getGenreID() != genreID ) {
                if (animeGenreFragment.getGenreID() == genreIDs.get(index)) {
                    //this.genreID[index] = genreID;
                    genreIDs.set(index, genreID);
                    animeGenreFragment.getGenreViewModel().getAnimeGenreList(genreID);
                    animeGenreFragment.getGenreNameTV().setText(JikanApiUtils.names[(genreID - 1)]);
                    returnValue = -1;
                    break;
                }
                else {
                    returnValue = -2;
                }
            }
            else {

                returnValue = -3;
                break;
            }
        }
        return returnValue;
    }
}
