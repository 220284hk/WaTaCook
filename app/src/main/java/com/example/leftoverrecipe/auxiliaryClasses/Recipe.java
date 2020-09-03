package com.example.leftoverrecipe.auxiliaryClasses;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Objects;

import static com.example.leftoverrecipe.auxiliaryClasses.Strings.ID;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.READY_IN_MINUTES;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.SERVINGS;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.SOURCE_URL;
import static com.example.leftoverrecipe.auxiliaryClasses.Strings.TITLE;

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
    private String id, sourceUrl, title, imageURL, servings, prepTime;
    private Image image;

    public Recipe(String[] info) {
        this.id = info[0];
        this.sourceUrl = info[1];
        this.title = info[2];
        this.imageURL = info[3];
        this.servings = info[4];
        this.prepTime = info[5];
//        this.image = image;
    }



    public Recipe(HashMap<String, String> info) {
        this.id = info.get(ID);
        this.sourceUrl = info.get(SOURCE_URL);
        this.title = info.get(TITLE);
        this.imageURL =  String.format("https://spoonacular.com/recipeImages/%s-480x360.jpg", info.get(ID));
        this.servings = info.get(SERVINGS);
        this.prepTime = info.get(READY_IN_MINUTES);
//        this.image = image;
    }

    protected Recipe(Parcel in) {
        id = in.readString();
        sourceUrl = in.readString();
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

    public String getSourceUrl() {
        return sourceUrl;
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
                ", sourceUrl='" + sourceUrl + '\'' +
                ", title='" + title + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", servings='" + servings + '\'' +
                ", prepTime='" + prepTime + '\'' +
                ", image=" + image +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(sourceUrl);
        parcel.writeString(title);
        parcel.writeString(imageURL);
        parcel.writeString(servings);
        parcel.writeString(prepTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id.equals(recipe.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
