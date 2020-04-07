package com.dimi.animeapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dimi.animeapp.BR;
import com.dimi.animeapp.R;
import com.dimi.animeapp.databinding.CharacterListRowBinding;
import com.dimi.animeapp.model.Character;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

public class CharacterListRecyclerView extends RecyclerView.Adapter<CharacterListRecyclerView.ViewHolder>  {

    public List<Character> getCharacterList() {
        return characterList;
    }

    private List<Character> characterList;
    private Context context;
    private OnCharacterClickListener onCharacterClickListener;


    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CharacterListRowBinding characterListRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.character_list_row, parent, false);

        return new ViewHolder(characterListRowBinding, onCharacterClickListener);
    }

    public CharacterListRecyclerView(Context context, OnCharacterClickListener onCharacterClickListener) {
        this.context = context;
        this.onCharacterClickListener = onCharacterClickListener;
    }

    public interface OnCharacterClickListener {
        void onCharacterClick( int position );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (characterList.get(position) == null) return;
        Character character = characterList.get(position);
        holder.bind(character);
    }

    @Override
    public int getItemCount() {
        return characterList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CharacterListRowBinding characterListRowBinding;
        private OnCharacterClickListener onCharacterClickListener;

        ViewHolder(@NonNull CharacterListRowBinding characterListRowBinding, OnCharacterClickListener onCharacterClickListener ) {
            super(characterListRowBinding.getRoot());
            this.characterListRowBinding = characterListRowBinding;
            this.onCharacterClickListener = onCharacterClickListener;

            characterListRowBinding.setCharClick(this);

        }

        void bind(Object obj) {
            characterListRowBinding.setVariable(BR.character, obj);
            characterListRowBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            onCharacterClickListener.onCharacterClick( getAdapterPosition() );
        }
    }
}
