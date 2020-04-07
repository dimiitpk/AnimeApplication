package com.dimi.animeapp.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.dimi.animeapp.R;
import com.dimi.animeapp.adapter.AnimeListRecyclerView;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.vm.AnimeListViewModel;

import java.util.List;

public class SearchAnimeFragment extends Fragment implements AnimeListRecyclerView.OnAnimeForDetailsClicked {

    public SearchAnimeFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private AnimeListRecyclerView animeListRecyclerView;
    private AnimeListViewModel animeListViewModel;
    private EditText searchAnime;
    private ProgressBar loadingIndicator;
    private RecyclerView recyclerView;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (animeListViewModel.getBaseRepository() != null) {

            animeListViewModel.getIsLoading().observe(getViewLifecycleOwner(), (Boolean isLoading) -> {
                if (isLoading) loadingIndicator.setVisibility(View.VISIBLE);
                else loadingIndicator.setVisibility(View.GONE);
            });

            animeListViewModel.getSearchAnimeName().observe(getViewLifecycleOwner(), s -> {
                animeListViewModel.getIsLoading().postValue(true);
                animeListViewModel.getAnimeList( s );

            });

            animeListViewModel.getAnimeList( animeListViewModel.getSearchAnimeName().getValue() ).observe(getViewLifecycleOwner(), (List<Anime> animList) -> {

                animeListViewModel.getIsLoading().postValue(false);
                if (animeListRecyclerView == null) initRecyclerView(animList);
                else {
                    animeListRecyclerView.setAnimeList(animList);
                    animeListRecyclerView.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search_anime, container, false);

        loadingIndicator = view.findViewById(R.id.ac_main_loading_indicator);
        searchAnime = view.findViewById(R.id.main_search_edit_text);
        ImageButton searchButton = view.findViewById(R.id.main_search_button);
        recyclerView = view.findViewById(R.id.acMainRecyclerView);

        animeListViewModel = new ViewModelProvider(this).get(AnimeListViewModel.class);

        searchButton.setOnClickListener(v -> {
            if (TextUtils.isEmpty(searchAnime.getText().toString().trim()))
                animeListViewModel.setSearchAnimeName("Naruto");
            else
                animeListViewModel.setSearchAnimeName(searchAnime.getText().toString().trim());
        });
        return view;
    }

    private void initRecyclerView(List<Anime> animList) {

        animeListRecyclerView = new AnimeListRecyclerView(getActivity(), false, this);
        animeListRecyclerView.setAnimeList(animList);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(animeListRecyclerView);
    }

    @Override
    public void OnAnimForDetailsClick(int position) {
        animeListViewModel.getIsLoading().postValue(true);
        Anime anime1 = animeListRecyclerView.getAnimeList().get(position);
        animeListViewModel.getFullAnimeDetails(anime1.getMal_id()).observe(this, anime -> {

            anime1.setTrailer_url(anime.getTrailer_url());
            animeListRecyclerView.showDialogDeatilsAnime( anime );
            animeListViewModel.getIsLoading().postValue(false);
        });
    }
}
