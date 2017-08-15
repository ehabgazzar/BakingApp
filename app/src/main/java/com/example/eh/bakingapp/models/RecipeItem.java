package com.example.eh.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Eh on 6/23/2017.
 */

public class RecipeItem implements Parcelable {

    int id;
    String name;
    private String image;
    ArrayList<IngredientItem> IngredientItems = new ArrayList<>();
    ArrayList<StepItem> StepItems= new ArrayList<>();
    String servings;

    protected RecipeItem(Parcel in) {
        id = in.readInt();
        name = in.readString();
        image = in.readString();
        in.readTypedList(IngredientItems,IngredientItem.CREATOR);
        in.readTypedList(StepItems,StepItem.CREATOR);
        servings = in.readString();
    }

    public static final Creator<RecipeItem> CREATOR = new Creator<RecipeItem>() {
        @Override
        public RecipeItem createFromParcel(Parcel in) {
            return new RecipeItem(in);
        }

        @Override
        public RecipeItem[] newArray(int size) {
            return new RecipeItem[size];
        }
    };

    public RecipeItem() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    public ArrayList<IngredientItem> getIngredientItems() {
        return IngredientItems;
    }

    public void setIngredientItems(ArrayList<IngredientItem> ingredientItems) {
        this.IngredientItems = ingredientItems;
    }

    public ArrayList<StepItem> getStepItems() {
        return StepItems;
    }

    public void setStepItems(ArrayList<StepItem> stepItems) {
        this.StepItems = stepItems;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeTypedList(IngredientItems);
        dest.writeTypedList(StepItems);
        dest.writeString(servings);
    }
}
