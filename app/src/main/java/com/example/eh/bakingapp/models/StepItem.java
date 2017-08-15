package com.example.eh.bakingapp.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

/**
 * Created by Eh on 6/23/2017.
 */

public class StepItem implements Parcelable {
    int id;
    String shortDescription;
    String description;
    String videoURL;




    String thumbnailURL;

    public StepItem()
    {

    }
    protected StepItem(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        description = in.readString();
        videoURL = in.readString();
    }

    public static final Creator<StepItem> CREATOR = new Creator<StepItem>() {
        @Override
        public StepItem createFromParcel(Parcel in) {
            return new StepItem(in);
        }

        @Override
        public StepItem[] newArray(int size) {
            return new StepItem[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoURL() {
        return videoURL;
    }
    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shortDescription);
        dest.writeString(description);
        dest.writeString(videoURL);
    }
}
