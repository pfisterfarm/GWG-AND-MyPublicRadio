package com.pfisterfarm.mypublicradio.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "item", strict = false)
public class Episode implements Parcelable {

    @Path("title")
    @Text(required = true)
    private String mTitle;

    @Element(name = "description")
    private String mDescription;

    @Element(name="pubDate")
    private String mPubDate;

    @Path("itunes:duration")
    @Text(required = true)
    private String mDuration;   // episode duration in seconds

    @Path("enclosure")
    @Attribute(name="url")
    private String mUrl;

    // is this episode expanded in the recycler view to show description?
    private boolean expanded;

    public Episode() {
        this.mTitle = "empty";
        this.mDescription = "empty";
        this.mPubDate = "01/01/01";
        this.mDuration = "0";
        this.mUrl = "http://www.google.com";
        this.expanded = false;
    }

    public Episode(String epTitle, String epDesc, String epPubDate, String epDuration, String epUrl, boolean epExpanded) {
        this.mTitle = epTitle;
        this.mDescription = epDesc;
        this.mPubDate = epPubDate;
        this.mDuration = epDuration;
        this.mUrl = epUrl;
        this.expanded = epExpanded;
    }

    protected Episode(Parcel in) {
        mTitle = in.readString();
        mDescription = in.readString();
        mPubDate = in.readString();
        mDuration = in.readString();
        mUrl = in.readString();
        expanded = (in.readByte() != 0);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeString(mPubDate);
        dest.writeString(mDuration);
        dest.writeString(mUrl);
        dest.writeByte((byte) (expanded ? 1 : 0));
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

    public String  getDuration() {
        return mDuration;
    }

    public void setDuration(String mDuration) {
        this.mDuration = mDuration;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
