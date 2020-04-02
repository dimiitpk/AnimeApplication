package com.dimi.animeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.dimi.animeapp.R;
import com.dimi.animeapp.adapter.AnimeListRecyclerView;
import com.dimi.animeapp.databinding.ActivityMainBinding;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.vm.AnimeListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AnimeListRecyclerView animeListRecyclerView;
    AnimeListViewModel animeListViewModel;
    private EditText searchAnime;
    private ImageButton searchButton;
    private String searchAnimeName = "Naruto";
    ProgressBar loadingIndicator;
    ActivityMainBinding activityMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        loadingIndicator = activityMainBinding.acMainLoadingIndicator;

        searchAnime = findViewById(R.id.main_search_edit_text);
        searchButton = findViewById(R.id.main_search_button);

        animeListViewModel = new ViewModelProvider(this ).get(AnimeListViewModel.class);

        searchButton.setOnClickListener(v -> {
            if(TextUtils.isEmpty(searchAnime.getText().toString().trim()))
                searchAnimeName = "Naruto";
            else
                searchAnimeName = searchAnime.getText().toString().trim();

            animeListViewModel.getAnimeList(searchAnimeName);
            animeListRecyclerView.notifyDataSetChanged();
        });

        animeListViewModel.getIsLoading().observe(this, (Boolean isLoading) -> {
            if (isLoading) loadingIndicator.setVisibility(View.VISIBLE);
            else loadingIndicator.setVisibility(View.GONE);
        });

        animeListViewModel.getAnimeList(searchAnimeName).observe(this, (List<Anime> animList) -> {
            animeListViewModel.getIsLoading().postValue(false);
            initRecyclerView( animList );
        });
    }

    private void initRecyclerView( List<Anime> animList ) {
        Log.d("AnimINIT", "initRecyclerView: " + animList.toString());

        animeListRecyclerView = new AnimeListRecyclerView( this, animList );

        recyclerView = activityMainBinding.acMainRecyclerView;
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        activityMainBinding.setMyAdapter(animeListRecyclerView);
    }
}
