package com.dimi.animeapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharactersAndMore {
    @SerializedName("characters")
    private
    List<Character> characters;

    public CharactersAndMore() {
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}