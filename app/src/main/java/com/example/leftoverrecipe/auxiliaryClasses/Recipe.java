package com.example.leftoverrecipe.auxiliaryClasses;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

public class Recipe implements Parcelable {
    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    private String id, sourceURL, title, imageURL, servings, prepTime;
    private Image image;

    public Recipe(String[] info) {
        this.id = info[0];
        this.sourceURL = info[1];
        this.title = info[2];
        this.imageURL = info[3];
        this.servings = info[4];
        this.prepTime = info[5];
//        this.image = image;
    }

    protected Recipe(Parcel in) {
        id = in.readString();
        sourceURL = in.readString();
        title = in.readString();
        imageURL = in.readString();
        servings = in.readString();
        prepTime = in.readString();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getServings() {
        return servings;
    }

    public String getPrepTime() {
        return prepTime;
    }

    public Image getImage() {
        return image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Recipe{" +
                "id='" + id + '\'' +
                ", sourceURL='" + sourceURL + '\'' +
                ", title='" + title + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", servings='" + servings + '\'' +
                ", prepTime='" + prepTime + '\'' +
                ", image=" + image +
                '}';
    }

    public String getSourceURL() {
        return sourceURL;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(sourceURL);
        parcel.writeString(title);
        parcel.writeString(imageURL);
        parcel.writeString(servings);
        parcel.writeString(prepTime);
    }
}
