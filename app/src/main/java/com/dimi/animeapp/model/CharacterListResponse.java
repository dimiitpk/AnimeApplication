package com.dimi.animeapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CharacterListResponse {
    @SerializedName("characters")
    private
    List<Character> characters;

    public CharacterListResponse() {
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }
}