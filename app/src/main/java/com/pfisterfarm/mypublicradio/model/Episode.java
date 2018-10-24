package com.pfisterfarm.mypublicradio.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "item", strict = false)
public class Episode implements Parcelable {

    @Element(name = "title")
    private String mTitle;

    @Element(name = "description")
    private String mDescription;

    @Element(name="pubDate")
    private String mPubDate;

    @Element(name="itunes:duration")
    private int mDuration;   // episode duration in seconds

    @Element(name="enclosure")
    @Attribute(name="url")
    private String mUrl;

    protected Episode(Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
        mPubDate = in.readString();
        mDuration = in.readInt();
        mUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mPubDate);
        dest.writeInt(mDuration);
        dest.writeString(mUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Episode> CREATOR = new Creator<Episode>() {
        @Override
        public Episode createFromParcel(Parcel in) {
            return new Episode(in);
        }

        @Override
        public Episode[] newArray(int size) {
            return new Episode[size];
        }
    };

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public void setPubDate(String mPubDate) {
        this.mPubDate = mPubDate;
    }

    public int getDuration() {
        return mDuration;
    }

    public void setDuration(int mDuration) {
        this.mDuration = mDuration;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
