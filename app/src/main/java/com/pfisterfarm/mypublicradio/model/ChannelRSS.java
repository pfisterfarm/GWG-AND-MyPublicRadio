package com.pfisterfarm.mypublicradio.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "channel", strict = false)
public class ChannelRSS {

    @Element(name = "description")
    private String mDescription;

    @ElementList(name = "item", inline = true)
    private List<Episode> mEpisodes;

    public List<Episode> getEpisodes() {
        return mEpisodes;
    }

    public ChannelRSS() {

    }

    public ChannelRSS(List<Episode> inEpisodes) {
        this.mEpisodes = inEpisodes;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
    }
}
