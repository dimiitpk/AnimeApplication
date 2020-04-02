package com.dimi.animeapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dimi.animeapp.BR;
import com.dimi.animeapp.R;
import com.dimi.animeapp.databinding.AnimeListRowBinding;
import com.dimi.animeapp.databinding.CharacterListRowBinding;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.Character;
import com.dimi.animeapp.ui.AnimeDetailsActivity;
import com.dimi.animeapp.util.AnimeListCustomClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CharacterListRecyclerView extends RecyclerView.Adapter<CharacterListRecyclerView.ViewHolder> {

    private List<Character> characterList;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CharacterListRowBinding characterListRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.character_list_row, parent, false);
        return new ViewHolder(characterListRowBinding);
    }

    public CharacterListRecyclerView(Context context, List<Character> animeList) {
        this.context = context;
        this.characterList = animeList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (characterList.get(position) == null) return;
        Character character = characterList.get(position);
        holder.bind(character);
        //holder.characterListRowBinding.setItemClickListener(this);
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

//    @Override
//    public void animeClicked(Character c) {
//        Intent intent = new Intent( context, AnimeDetailsActivity.class);
//        intent.putExtra("Anime", anime );
//        context.startActivity(intent);
//
//        Log.d("SVE", "animeClicked: " + anime.getTitle());
//    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private CharacterListRowBinding characterListRowBinding;

        ViewHolder(@NonNull CharacterListRowBinding characterListRowBinding) {
            super(characterListRowBinding.getRoot());
            this.characterListRowBinding = characterListRowBinding;
        }

        void bind(Object obj) {
            characterListRowBinding.setVariable(BR.character, obj);
            characterListRowBinding.executePendingBindings();
        }
    }
}
