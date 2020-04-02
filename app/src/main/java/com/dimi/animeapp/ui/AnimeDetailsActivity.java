package com.dimi.animeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.dimi.animeapp.R;
import com.dimi.animeapp.databinding.ActivityAnimeDetailsBinding;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.util.AnimeCharactersListClick;

public class AnimeDetailsActivity extends AppCompatActivity implements AnimeCharactersListClick  {

    ActivityAnimeDetailsBinding activityAnimeDetailsBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        activityAnimeDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_anime_details);
        activityAnimeDetailsBinding.setItemClickListener(this);

        Intent intent = getIntent();
        Anime anime = intent.getParcelableExtra("Anime");
        Log.d("SVE", "AllCharactersClicked2: " + anime.getMal_id());

        activityAnimeDetailsBinding.setAnimeChar(anime);
    }

    @Override
    public void AllCharactersClicked(Anime anime) {
        Log.d("SVE", "AllCharactersClicked: " + anime.getMal_id());
        Intent intent = new Intent(AnimeDetailsActivity.this, CharacterListActivity.class);
        intent.putExtra("Anime", anime);
        startActivity(intent);
    }
}
