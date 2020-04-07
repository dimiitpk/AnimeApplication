package com.dimi.animeapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dimi.animeapp.R;
import com.dimi.animeapp.Retrofit.JikanApiUtils;
import com.dimi.animeapp.databinding.AnimeListRowBinding;
import com.dimi.animeapp.databinding.DialogAnimeDetailsBinding;
import com.dimi.animeapp.model.Anime;
import com.dimi.animeapp.model.User;
import com.dimi.animeapp.ui.CharacterListActivity;
import com.dimi.animeapp.util.AppUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;

public class AnimeListRecyclerView extends RecyclerView.Adapter<AnimeListRecyclerView.ViewHolder> {

    private List<Anime> animeList;
    private Context context;
    private boolean resizeImage;

    public interface OnAnimeForDetailsClicked {
        void OnAnimForDetailsClick( int position );
    }
    private OnAnimeForDetailsClicked onAnimeForDetailsClicked;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AnimeListRowBinding animeListRowBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.anime_list_row, parent, false);

        if (resizeImage) {
            ImageView imageView = animeListRowBinding.animeListImage;
            imageView.setAdjustViewBounds(false);
            imageView.setScaleType(ImageView.ScaleType.CENTER);
            LinearLayout listRowLinear = animeListRowBinding.listRowLinear;
            listRowLinear.getLayoutParams().width = (AppUtils.getScreenWidth(parent.getContext()) / 3);
            listRowLinear.getLayoutParams().height = (AppUtils.getScreenWidth(parent.getContext()) / 3);
        }

        return new ViewHolder(animeListRowBinding, onAnimeForDetailsClicked);
    }

    public void setAnimeList(List<Anime> animeList) {

        this.animeList = animeList;
    }

    public AnimeListRecyclerView(Context context, boolean resizeImage, OnAnimeForDetailsClicked onAnimeForDetailsClicked) {
        this.context = context;
        this.resizeImage = resizeImage;
        this.onAnimeForDetailsClicked = onAnimeForDetailsClicked;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (animeList.get(position) == null) return;
        Anime anime = animeList.get(position);
        holder.bind(anime);
    }

    @Override
    public int getItemCount() {
        return animeList.size();
    }

    public List<Anime> getAnimeList() {
        return animeList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AnimeListRowBinding animeListRowBinding;
        private OnAnimeForDetailsClicked onAnimeForDetailsClicked;

        ViewHolder(@NonNull AnimeListRowBinding animeListRowBinding, OnAnimeForDetailsClicked onAnimeForDetailsClicked) {
            super(animeListRowBinding.getRoot());
            this.animeListRowBinding = animeListRowBinding;
            this.onAnimeForDetailsClicked = onAnimeForDetailsClicked;

            animeListRowBinding.setAnimeClick(this);
        }

        void bind(Object obj) {
            animeListRowBinding.setVariable(BR.anime, obj);
            animeListRowBinding.executePendingBindings();
        }

        @Override
        public void onClick(View v) {
            onAnimeForDetailsClicked.OnAnimForDetailsClick( getAdapterPosition() );
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    public void showDialogDeatilsAnime(Anime anime) {

        DialogAnimeDetailsBinding detailsButtonCharacters = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.dialog_anime_details, null, false);

        detailsButtonCharacters.setAnimeChar(anime);

        detailsButtonCharacters.animeSynopsysText.setMovementMethod(new ScrollingMovementMethod());
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(context));
        builder.setView(detailsButtonCharacters.getRoot());

        AlertDialog dialog = builder.create();
        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.getWindow().getAttributes().windowAnimations = R.style.SlidingDialog;
        }
        dialog.show();
        Button allCharactersButton = detailsButtonCharacters.dialogButtonAllCharacters;
        Button trailerButton = detailsButtonCharacters.dialogButtonTrailer;
        WebView webView = detailsButtonCharacters.videoWebView;
        ImageView imageView = detailsButtonCharacters.imageView;
        ImageButton closeButton = detailsButtonCharacters.closeButtonDialog;
        allCharactersButton.setOnClickListener(v -> {
            dialog.dismiss();
            Intent intent = new Intent(context, CharacterListActivity.class);
            intent.putExtra("Anime", anime);
            context.startActivity(intent);
        });
        closeButton.setOnClickListener(v -> dialog.dismiss());


        trailerButton.setOnClickListener(v -> {
            if( anime.getTrailer_url() != null ) {

                if( trailerButton.getText().equals(context.getResources().getString(R.string.hide_trailer)) ) {
                    webView.clearFormData();
                    imageView.setVisibility(View.VISIBLE);
                    webView.setVisibility(View.GONE);
                    trailerButton.setText(R.string.show_trailer);
                }
                else {

                    imageView.setVisibility(View.GONE);
                    webView.setVisibility(View.VISIBLE);
                    trailerButton.setText(R.string.hide_trailer);

                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setWebChromeClient(new WebChromeClient());
                    String trailer_url = JikanApiUtils.formatVideoUrl(anime.getTrailer_url());
                    String video_url = "<iframe width=\"100%\" height=\"100%\" src=\""+trailer_url+"\" frameborder=\"0\" allowfullscreen></iframe>";
                    webView.loadData( video_url, "text/html" , "utf-8" );
                }
            }
            else {
                Snackbar snackbar = Snackbar.make(detailsButtonCharacters.getRoot(), "ERROR: There is no trailer for this anime.", 3000);
                snackbar.setTextColor(Color.WHITE);
                snackbar.setBackgroundTint(context.getResources().getColor(R.color.colorAccent, Objects.requireNonNull(context).getTheme()));
                snackbar.show();
            }
        });
    }
}
