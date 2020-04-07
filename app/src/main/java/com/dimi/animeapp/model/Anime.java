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
    @SerializedName("trailer_url")
    private String trailer_url;
    @SerializedName("title")
    private String title;
    @SerializedName("synopsis")
    private String synopis;
    @SerializedName("episodes")
    private int episodes;
    @SerializedName("score")
    private float score;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public int getMal_id() {
        return mal_id;
    }

    public void setMal_id(int mal_id) {
        this.mal_id = mal_id;
    }

    public  Anime() {}

    public Anime(int mal_id, String image_url, String trailer_url, String title, String synopis, int episodes, float score) {
        this.mal_id = mal_id;
        this.image_url = image_url;
        this.trailer_url = trailer_url;
        this.title = title;
        this.synopis = synopis;
        this.episodes = episodes;
        this.score = score;
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

    public String getTrailer_url() {
        return trailer_url;
    }

    public void setTrailer_url(String trailer_url) {
        this.trailer_url = trailer_url;
    }

    @BindingAdapter({ "avatar" })
    public static void loadImage(ImageView imageView, String imageURL) {


        String[] image_url_2 = imageURL.split("\\?");
        Picasso.get().load(image_url_2[0]).fit().placeholder(R.drawable.default_image).into(imageView);
    }

    public float formatScoreString(float scoreStars) {
        return scoreStars/2;
    }

    ////////////////////////////////////////////////

    //parcel part
    public Anime( Parcel in ){
        String[] data= new String[7];

        in.readStringArray(data);
        this.image_url = data[0];
        this.trailer_url = data[1];
        this.title = data[2];
        this.synopis = data[3];
        this.episodes = Integer.parseInt(data[4]);
        this.mal_id = Integer.parseInt(data[5]);
        this.score = Float.parseFloat(data[6]);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[]{this.image_url,this.trailer_url,this.title,this.synopis,String.valueOf(this.episodes),String.valueOf(this.mal_id),String.valueOf(this.score)});
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Anime> CREATOR = new Parcelable.Creator<Anime>() {

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
