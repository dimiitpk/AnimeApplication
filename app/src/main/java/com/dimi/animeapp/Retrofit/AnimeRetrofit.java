package com.dimi.animeapp.Retrofit;

import android.util.Log;

import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.AnimeAndMore;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnimeRetrofit {

    private static final String API_URL = "https://api.jikan.moe/v3/";
    private static Retrofit animeRetrofit;

    private static Retrofit getInstance() {
        if( animeRetrofit != null )
            return animeRetrofit;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        animeRetrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return animeRetrofit;
    }

    public static AnimeJikanApi APIService() {
        return getInstance().create(AnimeJikanApi.class);
    }
}
