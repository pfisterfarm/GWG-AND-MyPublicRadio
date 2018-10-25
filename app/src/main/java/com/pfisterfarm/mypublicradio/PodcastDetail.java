package com.pfisterfarm.mypublicradio;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.pfisterfarm.mypublicradio.model.Podcast;
import com.pfisterfarm.mypublicradio.model.PodcastRSS;
import com.pfisterfarm.mypublicradio.model.Podcasts;
import com.pfisterfarm.mypublicradio.network.PodcastInterface;
import com.pfisterfarm.mypublicradio.network.podcastClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PodcastDetail extends AppCompatActivity {

    private static final String SEND_PODCAST = "send_podcast";
    ImageView podcastBackground;
    ImageView podcastIcon;
    TextView podcastTitle;
    TextView podcastDesc;
    TextView podcastType;
    Podcast mPodcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] splitArr;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_detail);

        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra(SEND_PODCAST)) {
                mPodcast = intent.getParcelableExtra(SEND_PODCAST);
                podcastIcon = findViewById(R.id.podcast_detail_icon);
                Picasso.with(this).
                        load(mPodcast.getArtworkUrl100()).
                        fit().
                        error(R.drawable.npr100).
                        into(podcastIcon);
                podcastTitle = findViewById(R.id.podcast_detail_title);
                podcastTitle.setText(mPodcast.getTrackName());
                podcastDesc = findViewById(R.id.podcast_detail_desc);
                podcastDesc.setText("Need to write networking code to fetch podcast description and episode info");
                podcastType = findViewById(R.id.podcast_detail_type);
                podcastType.setText(mPodcast.getPrimaryGenreName());
                splitArr = mPodcast.getFeedUrl().split("=", 2);

                fetchPodcastRSS(splitArr[1]);


            }
        }

    }

    public void fetchPodcastRSS(String idString) {
        final String LOG_TAG = "debugpublicradio";

        PodcastInterface podInt = podcastClient.getRSSInstance();
        Call<PodcastRSS> podcastCall = podInt.fetchPodcastRSS(idString);
        podcastCall.enqueue(new Callback<PodcastRSS>() {
            @Override
            public void onResponse(Call<PodcastRSS> call, Response<PodcastRSS> response) {
                PodcastRSS podcastRSS = response.body();
                Log.d(LOG_TAG, "URL = " + response.raw().request().url());

            }
            public void onFailure(Call<PodcastRSS> call, Throwable t) {
                Log.d(LOG_TAG, "Failure trying to fetch podcast list");
            }
        });
    }
}
