package com.dimi.animeapp.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.dimi.animeapp.R;
import com.dimi.animeapp.adapter.CharacterListRecyclerView;
import com.dimi.animeapp.databinding.ActivityCharacterListBinding;
import com.dimi.animeapp.databinding.CharacterListRowBinding;
import com.dimi.animeapp.databinding.DialogCharacterDetailsBinding;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.Character;
import com.dimi.animeapp.vm.CharactersListViewModel;

import java.util.ArrayList;
import java.util.List;

public class CharacterListActivity extends AppCompatActivity implements CharacterListRecyclerView.OnCharacterClickListener {


    private ViewPager2 viewPager2;
    private CharactersListViewModel charactersListViewModel;
    CharacterListRecyclerView characterListRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ActivityCharacterListBinding activityCharacterListBinding = DataBindingUtil.setContentView(this, R.layout.activity_character_list);
        characterListRecyclerView = new CharacterListRecyclerView(this, this);

        ProgressBar loadingIndicator = activityCharacterListBinding.acCharacterLoadingIndicator;
        viewPager2 = activityCharacterListBinding.acCharListRecycler;

        Intent intent = getIntent();
        Anime anime = intent.getParcelableExtra("Anime");
        setTitle(anime.getTitle());
        charactersListViewModel = new ViewModelProvider(this).get(CharactersListViewModel.class);

        activityCharacterListBinding.setAnime(anime);
        charactersListViewModel.getIsLoading().observe(this, (Boolean isLoading) -> {
            if (isLoading) loadingIndicator.setVisibility(View.VISIBLE);
            else loadingIndicator.setVisibility(View.GONE);
        });

        charactersListViewModel.getCharactersList(anime).observe(this, (List<Character> characters) -> {
            charactersListViewModel.getIsLoading().postValue(false);
            initViewPager2(characters);
        });

    }

    private void initViewPager2(List<Character> charactersList) {
        characterListRecyclerView.setCharacterList(charactersList);

        viewPager2.setAdapter(characterListRecyclerView);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));

        viewPager2.setPageTransformer(compositePageTransformer);
    }

    @Override
    public void onCharacterClick(int position) {

        Character character1 = characterListRecyclerView.getCharacterList().get(position);
        charactersListViewModel.getIsLoading().postValue(true);
        charactersListViewModel.getCharacter(character1).observe(this, character -> {

            DialogCharacterDetailsBinding dialogCharacterDetailsBinding = DataBindingUtil.inflate(LayoutInflater.from(getApplicationContext()), R.layout.dialog_character_details, null, false);//

            character.setAbout(character.getAbout().replace("\\n", ""));
            dialogCharacterDetailsBinding.setCharacter(character);

            dialogCharacterDetailsBinding.charAboutText.setMovementMethod(new ScrollingMovementMethod());
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(dialogCharacterDetailsBinding.getRoot());

            AlertDialog dialog = builder.create();
            if (dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialog;
            }
            dialog.show();
            charactersListViewModel.getIsLoading().postValue(false);

            ImageButton closeButton = dialogCharacterDetailsBinding.closeImageButton;
            closeButton.setOnClickListener(v -> dialog.dismiss());
        });
    }
}
