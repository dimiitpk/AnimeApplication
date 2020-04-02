package com.dimi.animeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.dimi.animeapp.R;
import com.dimi.animeapp.adapter.AnimeListRecyclerView;
import com.dimi.animeapp.adapter.CharacterListRecyclerView;
import com.dimi.animeapp.databinding.ActivityCharacterListBinding;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.Character;
import com.dimi.animeapp.vm.CharactersListViewModel;

import java.util.List;

public class CharacterListActivity extends AppCompatActivity {

    private ActivityCharacterListBinding activityCharacterListBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityCharacterListBinding = DataBindingUtil.setContentView(this, R.layout.activity_character_list);

        ProgressBar loadingIndicator = activityCharacterListBinding.acCharacterLoadingIndicator;

        Intent intent = getIntent();
        Anime anime = intent.getParcelableExtra("Anime");
        Log.d("SVE", "AllCharactersClicked1: " + anime.getMal_id());
        setTitle(anime.getTitle());
        CharactersListViewModel charactersListViewModel = new ViewModelProvider(this).get(CharactersListViewModel.class);

        charactersListViewModel.getIsLoading().observe(this, (Boolean isLoading) -> {
            if (isLoading) loadingIndicator.setVisibility(View.VISIBLE);
            else loadingIndicator.setVisibility(View.GONE);
        });

        charactersListViewModel.getCharactersList(anime).observe(this, characters -> {
            charactersListViewModel.getIsLoading().postValue(false);
            initRecyclerView(characters);
        });

    }

    private void initRecyclerView( List<Character> charactersList ) {
        Log.d("AnimINIT", "initRecyclerView: " + charactersList.toString());

        CharacterListRecyclerView characterListRecyclerView = new CharacterListRecyclerView( this, charactersList );

        RecyclerView recyclerView = activityCharacterListBinding.acCharListRecycler;
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        activityCharacterListBinding.setMyCharAdapter(characterListRecyclerView);
    }
}
