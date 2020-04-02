package com.dimi.animeapp.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionService;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.dimi.animeapp.R;
import com.dimi.animeapp.Retrofit.AnimeRetrofit;
import com.dimi.animeapp.adapter.AnimeListRecyclerView;
import com.dimi.animeapp.adapter.CharacterListRecyclerView;
import com.dimi.animeapp.databinding.ActivityCharacterListBinding;
import com.dimi.animeapp.databinding.DialogCharacterDetailsBinding;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.Character;
import com.dimi.animeapp.util.CharacterClickCustom;
import com.dimi.animeapp.vm.CharactersListViewModel;

import java.util.List;

public class CharacterListActivity extends AppCompatActivity implements CharacterListRecyclerView.OnShareClickedListener {

    private ActivityCharacterListBinding activityCharacterListBinding;
    private CharactersListViewModel charactersListViewModel;
    CharacterListRecyclerView characterListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        activityCharacterListBinding = DataBindingUtil.setContentView(this, R.layout.activity_character_list);
        characterListRecyclerView = new CharacterListRecyclerView(this);
        characterListRecyclerView.setOnShareClickedListener(this);

        ProgressBar loadingIndicator = activityCharacterListBinding.acCharacterLoadingIndicator;

        Intent intent = getIntent();
        Anime anime = intent.getParcelableExtra("Anime");
        Log.d("SVE", "AllCharactersClicked1: " + anime.getMal_id());
        setTitle(anime.getTitle());
        charactersListViewModel = new ViewModelProvider(this).get(CharactersListViewModel.class);

        charactersListViewModel.getIsLoading().observe(this, (Boolean isLoading) -> {
            if (isLoading) loadingIndicator.setVisibility(View.VISIBLE);
            else loadingIndicator.setVisibility(View.GONE);
        });

        charactersListViewModel.getCharactersList(anime).observe(this, (List<Character> characters) -> {
            charactersListViewModel.getIsLoading().postValue(false);
            initRecyclerView(characters);
        });

    }

    private void initRecyclerView(List<Character> charactersList) {

        characterListRecyclerView.setCharacterList(charactersList);
        RecyclerView recyclerView = activityCharacterListBinding.acCharListRecycler;
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        activityCharacterListBinding.setMyCharAdapter(characterListRecyclerView);
    }

    @Override
    public void ShareClicked(Character character1) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        charactersListViewModel.getCharacter(character1).observe(this, character -> {
            DialogCharacterDetailsBinding dialogCharacterDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()), R.layout.dialog_character_details, null, false);
            dialogCharacterDetailsBinding.setCharacter(character);

            builder.setView(dialogCharacterDetailsBinding.getRoot());

            AlertDialog dialog = builder.create();
            dialog.show();

            ImageButton closeButton = dialogCharacterDetailsBinding.closeImageButton;
            closeButton.setOnClickListener(v -> dialog.dismiss());
        });
    }
}
