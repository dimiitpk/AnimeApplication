package com.dimi.animeapp.adapter;

import android.content.Context;

import com.dimi.animeapp.Retrofit.JikanApiUtils;
import com.dimi.animeapp.ui.fragments.BlankFrag;
import com.dimi.animeapp.ui.fragments.SearchAnimeFragment;
import com.dimi.animeapp.ui.fragments.AnimeListGenreFragment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class SectionsPageAdapter extends FragmentStatePagerAdapter {

    private static final int FRAGMENT_NUMBER = 3;

    private final Context context;

    public SectionsPageAdapter(Context context, @NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        this.context = context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String titleName = "Action";
        switch ( position) {
            case 0:
                titleName = "Search";
                break;
            case 1:
                titleName = "Genres";
                break;
            case 2:
                titleName = JikanApiUtils.names[(JikanApiUtils.GENRE_CARS-1)];
                break;
        }
        return titleName;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0: {
                fragment = new SearchAnimeFragment();
                break;
            }
            case 1: {
                fragment = new AnimeListGenreFragment();
                break;
            }
            case 2: {
                fragment = new BlankFrag();
                break;
            }
        }
        if( fragment != null )  return fragment;
        else return new BlankFrag();
    }

    @Override
    public int getCount() {
        return FRAGMENT_NUMBER;
    }
}
