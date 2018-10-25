package com.pfisterfarm.mypublicradio.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="rss", strict = false)
public class PodcastRSS {

    @Element(name = "description")
    @Path("channel")
    private String mPodcastDescription;

    @ElementList(name = "item", inline = true)
    @Path("channel")
    private List<Episode> mEpisodeList;

    public String getPodcastDescription() {
        return mPodcastDescription;
    }

    public void setPodcastDescription(String mPodcastDescription) {
        this.mPodcastDescription = mPodcastDescription;
    }

    public List<Episode> getEpisodeList() {
        return mEpisodeList;
    }

    public void setEpisodeList(List<Episode> mEpisodeList) {
        this.mEpisodeList = mEpisodeList;
    }
}
