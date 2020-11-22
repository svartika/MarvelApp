package com.example.controllers.commons;

import android.os.Parcel;
import android.os.Parcelable;

public class ProcessedMarvelItemBase implements Parcelable {
    public int id;
    public String title, description, modified, imageurl;

    protected ProcessedMarvelItemBase(Parcel in) {
        id = in.readInt();
        title = in.readString();
        description = in.readString();
        modified = in.readString();
        imageurl = in.readString();
    }

    public ProcessedMarvelItemBase() {

    }

    public boolean displayName() {
        if(imageurl=="" || imageurl.contains("image_not_available"))
            return true;
        else
            return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(description);
        parcel.writeString(modified);
        parcel.writeString(imageurl);
    }

    public static final Creator<ProcessedMarvelItemBase> CREATOR = new Creator<ProcessedMarvelItemBase>() {
        @Override
        public ProcessedMarvelItemBase createFromParcel(Parcel in) {
            return new ProcessedMarvelItemBase(in);
        }

        @Override
        public ProcessedMarvelItemBase[] newArray(int size) {
            return new ProcessedMarvelItemBase[size];
        }
    };
}
