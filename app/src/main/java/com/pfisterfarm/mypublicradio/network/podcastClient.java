package com.pfisterfarm.mypublicradio.network;


import com.pfisterfarm.mypublicradio.model.PodcastRSS;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class podcastClient {

    public static PodcastInterface getInstance() {
        final String BASE_URL = "https://itunes.apple.com";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()). build();


        return retrofit.create(PodcastInterface.class);
    }

    public static PodcastInterface getRSSInstance() {
        final String BASE_URL = "https://www.npr.org/rss";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create()).build();

        return retrofit.create(PodcastInterface.class);
    }

}
