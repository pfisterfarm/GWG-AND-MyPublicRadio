package com.pfisterfarm.mypublicradio.network;

 import com.pfisterfarm.mypublicradio.model.Podcasts;

 import retrofit2.Call;
 import retrofit2.http.GET;
 import retrofit2.http.Query;

public interface PodcastInterface {
    @GET("search")
    Call<Podcasts> fetchPodcastList(@Query("term") String term, @Query("media") String media);
}
