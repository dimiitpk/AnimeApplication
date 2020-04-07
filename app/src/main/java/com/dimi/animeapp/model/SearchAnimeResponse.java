package com.dimi.animeapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchAnimeResponse {

    @SerializedName("request_hash")
    private String request_hash;
    @SerializedName("request_cached")
    private String request_cached;
    @SerializedName("request_cache_expiry")
    private String request_cache_expiry;
    @SerializedName("results")
    private
    List<Anime> animes;

    public void setRequest_hash(String request_hash) {
        this.request_hash = request_hash;
    }

    public void setRequest_cached(String request_cached) {
        this.request_cached = request_cached;
    }

    public void setRequest_cache_expiry(String request_cache_expiry) {
        this.request_cache_expiry = request_cache_expiry;
    }

    public void setAnimes(List<Anime> animes) {
        this.animes = animes;
    }

    public List<Anime> getAnimes() {
        return animes;
    }

    public String getRequest_hash() {
        return request_hash;
    }

    public String getRequest_cached() {
        return request_cached;
    }

    public String getRequest_cache_expiry() {
        return request_cache_expiry;
    }
}
