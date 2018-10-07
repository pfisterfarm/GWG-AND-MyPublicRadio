package com.pfisterfarm.mypublicradio.network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class podcastClient {

    public static PodcastInterface getInstance() {
        final String BASE_URL = "https://itunes.apple.com";

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()). build();


        return retrofit.create(PodcastInterface.class);
    }



}
