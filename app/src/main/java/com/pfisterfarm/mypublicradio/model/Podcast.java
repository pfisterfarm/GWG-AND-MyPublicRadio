package com.pfisterfarm.mypublicradio.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

@Entity(tableName = "podcasts")
public class Podcast implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    private long id;

    @ColumnInfo(name = "title")
    @SerializedName("trackName")
    private String trackName;

    @SerializedName("feedUrl")
    private String feedUrl;

    @SerializedName("artworkUrl100")
    private String artworkUrl100;

    @SerializedName("artworkUrl600")
    private String artworkUrl600;

    @SerializedName("primaryGenreName")
    private String primaryGenreName;

    public String getTrackName() {
        return trackName;
    }

    public void setTrackName(String trackName) {
        this.trackName = trackName;
    }

    public String getFeedUrl() {
        return feedUrl;
    }

    public void setFeedUrl(String feedUrl) {
        this.feedUrl = feedUrl;
    }

    public String getArtworkUrl100() {
        return artworkUrl100;
    }

    public String getArtworkUrl600() {
        return artworkUrl600;
    }

    public void setArtworkUrl100(String artworkUrl100) {
        this.artworkUrl100 = artworkUrl100;
    }

    public String getPrimaryGenreName() {
        return primaryGenreName;
    }

    public void setPrimaryGenreName(String primaryGenreName) {
        this.primaryGenreName = primaryGenreName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.trackName);
        dest.writeString(this.feedUrl);
        dest.writeString(this.artworkUrl100);
        dest.writeString(this.artworkUrl600);
        dest.writeString(this.primaryGenreName);
    }

    public Podcast(String trackName, String feedUrl, String artworkUrl100, String artworkUrl600, String primaryGenreName) {
        this.trackName = trackName;
        this.feedUrl = feedUrl;
        this.artworkUrl100 = artworkUrl100;
        this.artworkUrl600 = artworkUrl600;
        this.primaryGenreName = primaryGenreName;
    }

    protected Podcast(Parcel in) {
        this.trackName = in.readString();
        this.feedUrl = in.readString();
        this.artworkUrl100 = in.readString();
        this.artworkUrl600 = in.readString();
        this.primaryGenreName = in.readString();
    }

    public static final Parcelable.Creator<Podcast> CREATOR = new Parcelable.Creator<Podcast>() {
        @Override
        public Podcast createFromParcel(Parcel source) {
            return new Podcast(source);
        }

        @Override
        public Podcast[] newArray(int size) {
            return new Podcast[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
