package com.dimi.animeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dimi.animeapp.R;
import com.dimi.animeapp.databinding.AnimeListRowBinding;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.ui.AnimeDetailsActivity;
import com.dimi.animeapp.ui.MainActivity;
import com.dimi.animeapp.util.AnimeListCustomClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

public class AnimeListRecyclerView extends RecyclerView.Adapter<AnimeListRecyclerView.ViewHolder> implements AnimeListCustomClickListener {

    private List<Anime> animeList;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AnimeListRowBinding animeListRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.anime_list_row, parent, false);
        return new ViewHolder(animeListRowBinding);
    }

    public void setAnimeList(List<Anime> animeList) {
        this.animeList = animeList;
    }

    public AnimeListRecyclerView(Context context) {
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (animeList.get(position) == null) return;
        Anime anime = animeList.get(position);
        holder.bind(anime);
        holder.animeListRowBinding.setItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    @Override
    public void animeClicked(Anime anime) {
        Intent intent = new Intent( context, AnimeDetailsActivity.class);
        intent.putExtra("Anime", anime );
        context.startActivity(intent);

        Log.d("SVE", "animeClicked: " + anime.getTitle());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private AnimeListRowBinding animeListRowBinding;

        ViewHolder(@NonNull AnimeListRowBinding animeListRowBinding) {
            super(animeListRowBinding.getRoot());
            this.animeListRowBinding = animeListRowBinding;
        }

        void bind(Object obj) {
            animeListRowBinding.setVariable(BR.anime, obj);
            animeListRowBinding.executePendingBindings();
        }
    }
}
