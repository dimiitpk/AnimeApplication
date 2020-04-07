package com.dimi.animeapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.dimi.animeapp.R;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;

public class Character implements Parcelable {

    @SerializedName("mal_id")
    private int mal_id;
    @SerializedName("image_url")
    private String image_url;
    @SerializedName("name")
    private String name;
    @SerializedName("role")
    private String role;
    @SerializedName("about")
    private String about;

    public Character() {
    }

    public Character(int mail_id, String image_url, String name, String role, String about) {
        this.mal_id = mail_id;
        this.image_url = image_url;
        this.name = name;
        this.role = role;
        this.about = about;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public Boolean isRoleMain( String textRole ) {
        return textRole.equals("Main");
    }

    @BindingAdapter({ "char_avatar" })
    public static void loadImage(ImageView imageView, String imageURL) {


        String[] image_url_2 = imageURL.split("\\?");
        Picasso.get().load(image_url_2[0]).fit().placeholder(R.drawable.default_image).into(imageView);
    }
    @BindingAdapter({ "avatarFull" })
    public static void loadImageFull(ImageView imageView, String imageURL) {

        Picasso.get().load(imageURL).fit().placeholder(R.drawable.default_image).into(imageView);
    }

    //////////
    //parcel part
    public Character( Parcel in ){
        String[] data= new String[5];

        in.readStringArray(data);
        this.mal_id = Integer.parseInt(data[0]);
        this.image_url = data[1];
        this.name = data[2];
        this.role = data[3];
        this.about = data[4];
    }

    public int getMal_id() {
        return mal_id;
    }

    public void setMal_id(int mal_id) {
        this.mal_id = mal_id;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeStringArray(new String[]{String.valueOf(this.mal_id),this.image_url,this.name,this.role,this.about});
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<Character> CREATOR= new Parcelable.Creator<Character>() {

        @Override
        public Character createFromParcel(Parcel source) {
            return new Character(source);  //using parcelable constructor
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };
}
