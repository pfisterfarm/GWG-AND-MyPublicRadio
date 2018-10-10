package com.pfisterfarm.mypublicradio;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pfisterfarm.mypublicradio.model.Podcast;
import com.squareup.picasso.Picasso;

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

            }
        }

    }
}
