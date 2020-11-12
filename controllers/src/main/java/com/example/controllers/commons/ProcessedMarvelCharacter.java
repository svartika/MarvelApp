package com.example.controllers.commons;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.entitiy.models.MarvelCharacter;

import java.util.List;


public class ProcessedMarvelCharacter implements Parcelable {
    public int id;
    public String name, imageurl, description, modified;
    List<MarvelCharacter.URL> urls;

    public ProcessedCollection comics, series, stories, events;

    public ProcessedMarvelCharacter(MarvelCharacter character) {
        id = character.id;
        name = character.name;
        //ToDo: Fix https http issue by using manifest usinghttp option
      //  imageurl = character.thumbnail.path.replace("http", "https").concat("/portrait_xlarge.").concat(character.thumbnail.extension);
        imageurl = character.thumbnail.path.concat("/portrait_xlarge.").concat(character.thumbnail.extension);
        description = character.description;
        comics = new ProcessedCollection();
        series = new ProcessedCollection();
        stories = new ProcessedCollection();
        events = new ProcessedCollection();
        comics.set(character.comics);
        series.set(character.series);
        stories.set(character.stories);
        events.set(character.events);
        urls = character.urls;
        modified = character.modified;
    }

    protected ProcessedMarvelCharacter(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imageurl = in.readString();
        description = in.readString();
        modified = in.readString();
    }

    public static final Creator<ProcessedMarvelCharacter> CREATOR = new Creator<ProcessedMarvelCharacter>() {
        @Override
        public ProcessedMarvelCharacter createFromParcel(Parcel in) {
            return new ProcessedMarvelCharacter(in);
        }

        @Override
        public ProcessedMarvelCharacter[] newArray(int size) {
            return new ProcessedMarvelCharacter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(imageurl);
        parcel.writeString(description);
        parcel.writeString(modified);
    }
}
