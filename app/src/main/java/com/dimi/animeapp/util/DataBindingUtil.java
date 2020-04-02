package com.dimi.animeapp.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

public class DataBindingUtil {

    public static String formatEpisodeNumberText(int num_episode_br) {
        return "Number of episodes: " + num_episode_br;
    }
}
