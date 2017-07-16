package com.example.eh.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Eh on 6/23/2017.
 */

public class IngredientItem implements Parcelable {
    int quantity;
    String measure;
    String ingredient;

    public IngredientItem() {
    }

    protected IngredientItem(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<IngredientItem> CREATOR = new Creator<IngredientItem>() {
        @Override
        public IngredientItem createFromParcel(Parcel in) {
            return new IngredientItem(in);
        }

        @Override
        public IngredientItem[] newArray(int size) {
            return new IngredientItem[size];
        }
    };

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }
}
