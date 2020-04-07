package com.dimi.animeapp.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dimi.animeapp.R;
import com.dimi.animeapp.model.User;

import java.util.Objects;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AnimeListGenreFragment extends Fragment {

    public AnimeListGenreFragment() {}

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_top_genre_lists, container, false);

        FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        ViewGroup placeholder = view.findViewById(R.id.linear_list_nested);

        for( int i = 0; i < User.getInstance().getGenreListSize(); i++ ) {

            FrameLayout frameLayout = new FrameLayout(getActivity());
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            frameLayout.setLayoutParams( layoutParams );
            frameLayout.setId(ViewCompat.generateViewId());

            placeholder.addView(frameLayout);

            AnimeGenreFragment fragment = AnimeGenreFragment.newInstance(User.getInstance().getGenreID(i));
            User.getInstance().getGenreFragmentList().add(fragment);
            fragmentTransaction.replace(frameLayout.getId(), fragment);
        }

        fragmentTransaction.commit();

        return view;
    }
}
