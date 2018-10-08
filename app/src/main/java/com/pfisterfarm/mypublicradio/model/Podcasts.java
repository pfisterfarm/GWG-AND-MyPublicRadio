package com.pfisterfarm.mypublicradio.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Podcasts {

    @SerializedName("resultCount")
    private int podcastCount;

    @SerializedName("results")
    private List<Podcast> mPodcasts = null;

    public int getPodcastCount() {
        return podcastCount;
    }

    public void setPodcastCount(int podCount) {
        this.podcastCount = podCount;
    }

    public List<Podcast> getPodcasts() {
        return mPodcasts;
    }

    public void setmPodcasts(List<Podcast> pods) {
        this.mPodcasts = pods;
    }

    public Podcast getSinglePodcast(int position) {
        return mPodcasts.get(position);
    }

}
