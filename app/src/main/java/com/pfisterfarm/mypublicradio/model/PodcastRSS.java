package com.pfisterfarm.mypublicradio.model;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Path;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name="rss", strict = false)
public class PodcastRSS {

    @Element(name = "channel")
    private ChannelRSS mChannel;
    public ChannelRSS getChannel() {
        return mChannel;
    }

    public PodcastRSS() {

    }

    public PodcastRSS(ChannelRSS inChannel) {
        this.mChannel = inChannel;
    }
}
