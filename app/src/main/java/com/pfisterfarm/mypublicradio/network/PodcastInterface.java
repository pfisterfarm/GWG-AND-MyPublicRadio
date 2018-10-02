package com.pfisterfarm.mypublicradio.network;

 import com.pfisterfarm.mypublicradio.model.Podcast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PodcastInterface {
    @GET("podcasts.json")
    Call<ArrayList<Podcast>> fetchPodcasts();
}
