package com.dimi.animeapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.dimi.animeapp.R;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;

public class Anime implements Parcelable {

    @SerializedName("mal_id")
    private int mal_id;
    @SerializedName("image_url")
    private String image_url;
    @SerializedName("title")
    private String title;
    @SerializedName("synopsis")
    private String synopis;
    @SerializedName("episodes")
    private int episodes;

    public int getMal_id() {
        return mal_id;
    }

    public void setMal_id(int mal_id) {
        this.mal_id = mal_id;
    }

    public  Anime() {}

    public Anime(String image_url, String title, String synopis, int episodes) {
        this.image_url = image_url;
        this.title = title;
        this.synopis = synopis;
        this.episodes = episodes;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSynopis(String synopis) {
        this.synopis = synopis;
    }

    public void setEpisodes(int episodes) {
        this.episodes = episodes;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getTitle() {
        return title;
    }

    public String getSynopis() {
        return synopis;
    }

    public int getEpisodes() {
        return episodes;
    }

    @BindingAdapter({ "avatar" })
    public static void loadImage(ImageView imageView, String imageURL) {


        String[] image_url_2 = imageURL.split("\\?");
        Picasso.get().load(image_url_2[0]).fit().placeholder(R.drawable.default_image).into(imageView);
    }

    ////////////////////////////////////////////////

    //parcel part
    public Anime( Parcel in ){
        String[] data= new String[5];

        in.readStringArray(data);
        this.image_url = data[0];
        this.title = data[1];
        this.synopis = data[2];
        this.episodes = Integer.parseInt(data[3]);
        this.mal_id = Integer.parseInt(data[4]);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[]{this.image_url,this.title,this.synopis,String.valueOf(this.episodes),String.valueOf(this.mal_id)});
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Anime> CREATOR= new Parcelable.Creator<Anime>() {

        @Override
        public Anime createFromParcel(Parcel source) {
            return new Anime(source);  //using parcelable constructor
        }

        @Override
        public Anime[] newArray(int size) {
            return new Anime[size];
        }
    };
}
