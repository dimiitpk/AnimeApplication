package com.dimi.animeapp.ui.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.dimi.animeapp.R;
import com.dimi.animeapp.Retrofit.JikanApiUtils;
import com.dimi.animeapp.adapter.AnimeListRecyclerView;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.User;
import com.dimi.animeapp.vm.GenreViewModel;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class AnimeGenreFragment extends Fragment implements AnimeListRecyclerView.OnAnimeForDetailsClicked {

    private AnimeListRecyclerView animeListRecyclerView;
    private GenreViewModel genreViewModel;
    private ProgressBar loadingIndicator;
    private RecyclerView recyclerView;

    private int genreID = JikanApiUtils.GENRE_ACTION;
    private TextView genreNameTV;

    public TextView getGenreNameTV() {
        return genreNameTV;
    }

    public AnimeGenreFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public GenreViewModel getGenreViewModel() {
        return genreViewModel;
    }

    public int getGenreID() {
        return genreID;
    }

    private void setGenreID(int genreID) {
        this.genreID = genreID;
    }

    static AnimeGenreFragment newInstance(int genreID) {

        AnimeGenreFragment animeGenreFragment = new AnimeGenreFragment();
        animeGenreFragment.setGenreID(genreID);
        return animeGenreFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (genreViewModel.getGenreRepository() != null) {

            genreViewModel.getIsLoading().observe(getViewLifecycleOwner(), (Boolean isLoading) -> {
                if (isLoading) loadingIndicator.setVisibility(View.VISIBLE);
                else loadingIndicator.setVisibility(View.GONE);
            });

            genreViewModel.getAnimeGenreList(genreID).observe(getViewLifecycleOwner(), animList -> {
                genreViewModel.getIsLoading().postValue(false);
                if (animeListRecyclerView == null)
                    initRecyclerView(animList);
                else {
                    animeListRecyclerView.setAnimeList(animList);
                    animeListRecyclerView.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_genre_view, container, false);

        loadingIndicator = view.findViewById(R.id.frag_search_loading);
        genreNameTV = view.findViewById(R.id.frag_search_genreName);
        recyclerView = view.findViewById(R.id.frag_search_recycler_view);

        genreNameTV.setText(JikanApiUtils.names[(genreID - 1)]);

        genreViewModel = new ViewModelProvider(this).get(GenreViewModel.class);
        genreViewModel.createGenreRepository(getGenreID());

        ImageButton chose_genre = view.findViewById(R.id.chose_genre);
        chose_genre.setOnClickListener(v -> choseGenreArrowClicked( inflater, container ));

        return view;
    }

    private void choseGenreArrowClicked( LayoutInflater inflater, ViewGroup container) {

        View view1 = inflater.inflate(R.layout.dialog_genre_chose,  container, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));

        builder.setView(view1);
        AlertDialog dialog = builder.create();
        ListView listView = view1.findViewById(R.id.listview);

        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        listView.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, JikanApiUtils.names){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextColor(Color.WHITE);
                return view;

            }
        });

        dialog.show();
        view1.findViewById(R.id.close_button_genre_chose).setOnClickListener(v1 -> dialog.dismiss());
        listView.setOnItemClickListener((parent, view2, position, id) -> {
            genreListItemClicked( position );
            dialog.dismiss();
        });
    }

    private void genreListItemClicked( int position ) {

        int newGenre = position+1;
        String snackbar_text = "Ups! This genre is already chosen!";


        if (!User.getInstance().isAlreadyChosen(newGenre)) {

            int oldIndex = User.getInstance().getGenreIndex(genreID);
            if (oldIndex != -1) {

                if (User.getInstance().setGenreID(oldIndex, newGenre) == -1) {

                    genreViewModel.setIsLoadingToTrue();
                    setGenreID(newGenre);
                    snackbar_text = "Chosen genre: " + JikanApiUtils.names[position]+"!";
                }
                else snackbar_text = "Ups! Something went wrong!";
            }
            else snackbar_text = "Ups! Something went wrong!";
        }
        Toast.makeText(getActivity(), snackbar_text, Toast.LENGTH_LONG).show();
    }

    private void initRecyclerView(List<Anime> animList) {

        animeListRecyclerView = new AnimeListRecyclerView(getActivity(), true, this);
        animeListRecyclerView.setAnimeList(animList);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(animeListRecyclerView);
    }

    @Override
    public void OnAnimForDetailsClick(int position) {

        Anime anime1 = animeListRecyclerView.getAnimeList().get(position);
        genreViewModel.getFullAnimeDetails(anime1.getMal_id()).observe(this, anime -> {

            anime1.setTrailer_url(anime.getTrailer_url());
            animeListRecyclerView.showDialogDeatilsAnime(anime);
            genreViewModel.getIsLoading().postValue(false);
        });
    }
}
