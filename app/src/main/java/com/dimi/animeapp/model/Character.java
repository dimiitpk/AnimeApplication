package com.dimi.animeapp.model;

import android.widget.ImageView;

import com.dimi.animeapp.R;
import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;

public class Character {

    private int mail_id;
    private String image_url;
    private String name;
    private String role;
    private String about;

    public Character() {
    }

    public Character(int mail_id, String image_url, String name, String role, String about) {
        this.mail_id = mail_id;
        this.image_url = image_url;
        this.name = name;
        this.role = role;
        this.about = about;
    }

    public int getMail_id() {
        return mail_id;
    }

    public void setMail_id(int mail_id) {
        this.mail_id = mail_id;
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
}
