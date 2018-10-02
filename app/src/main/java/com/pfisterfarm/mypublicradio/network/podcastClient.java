package com.pfisterfarm.mypublicradio.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class podcastClient {
    private static final String BASE_URL = "https://itunes.apple.com/search?term=NPR&media=podcast";
    private static PodcastInterface retrofit = null;

    public static PodcastInterface getClient() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder().create();
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();

            retrofit = new Retrofit.Builder().
                    baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create(gson)).
                    callFactory(okHttpClientBuilder.build()).
                    build().create(PodcastInterface.class);
        }
        return retrofit;
    }
}
