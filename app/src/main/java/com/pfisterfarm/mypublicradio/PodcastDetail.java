package com.pfisterfarm.mypublicradio;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pfisterfarm.mypublicradio.database.PodcastDatabase;
import com.pfisterfarm.mypublicradio.model.Episode;
import com.pfisterfarm.mypublicradio.model.EpisodeRecyclerAdapter;
import com.pfisterfarm.mypublicradio.model.Podcast;
import com.pfisterfarm.mypublicradio.model.PodcastRSS;
import com.pfisterfarm.mypublicradio.model.Podcasts;
import com.pfisterfarm.mypublicradio.network.PodcastInterface;
import com.pfisterfarm.mypublicradio.network.podcastClient;
import com.pfisterfarm.mypublicradio.utils.AppExecutors;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PodcastDetail extends AppCompatActivity implements EpisodeRecyclerAdapter.EpisodeClickListener {

    private static final String SEND_PODCAST = "send_podcast";
    ImageView podcastBackground;
    ImageView podcastIcon;
    TextView podcastTitle;
    TextView podcastDesc;
    TextView podcastType;
    Button addButton;
    Podcast mPodcast;
    private PodcastDatabase mDb;
    boolean isInLibrary = false;    // hopefully we can find a better way to handle this
                                     // than in Popular Movies stage 2. For now assume false
    PodcastRSS podcastRSS;
    RecyclerView epRecycler;
    EpisodeRecyclerAdapter epRecAdapter;
    List<Episode> episodesToDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String[] splitArr;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_podcast_detail);

        mDb = PodcastDatabase.getInstance(getApplicationContext());

        Intent intent = getIntent();

        if (intent != null) {
            if (intent.hasExtra(SEND_PODCAST)) {
                mPodcast = intent.getParcelableExtra(SEND_PODCAST);
                setTitle(mPodcast.getTrackName());
                podcastIcon = findViewById(R.id.podcast_detail_icon);
                Picasso.with(this).
                        load(mPodcast.getArtworkUrl100()).
                        fit().
                        error(R.drawable.npr100).
                        into(podcastIcon);
                podcastTitle = findViewById(R.id.podcast_detail_title);
                podcastTitle.setText(mPodcast.getTrackName());

                addButton = (Button) findViewById(R.id.add_button);
                addButton.setEnabled(false);    // set disabled initially until db can be checked

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        Podcast checkLibrary = mDb.podcastDao().findPodcast(mPodcast.getTrackName());
                        isInLibrary = (checkLibrary != null);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                addButton.setEnabled(true);
                                if (isInLibrary) {
                                    addButton.setText("Remove From Library");
                                } else {
                                    addButton.setText("Add To Library");
                                }
                            }
                        });

                    }
                 });


                addButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                if (!isInLibrary) {
                                    mDb.podcastDao().insertPodcast(mPodcast);
                                    isInLibrary = true;
                                    addButton.setText("Remove From Library");
                                } else {
                                    mDb.podcastDao().deletePodcast(mPodcast);
                                    isInLibrary = false;
                                    addButton.setText("Add To Library");
                                }
                            }
                        });

                    }
                });

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
                podcastRSS = response.body();
                Log.d(LOG_TAG, "URL = " + response.raw().request().url());
                displayPodcastDetail();
            }
            public void onFailure(Call<PodcastRSS> call, Throwable t) {
                Log.d(LOG_TAG, "Failure trying to fetch podcast list");
            }
        });
    }

    public void displayPodcastDetail() {
        podcastDesc = findViewById(R.id.podcast_detail_desc);
        podcastDesc.setText(Html.fromHtml(podcastRSS.getChannel().getDescription()));
        setupRecyclerView();
    }

    public void setupRecyclerView() {
        // copy most recent episodes to new list, to a maximum of 20
        episodesToDisplay = new ArrayList<Episode>();
        int numberEpisodes = Math.min(podcastRSS.getChannel().getEpisodes().size(), 20);
        for (int i = 0; i < numberEpisodes; i++) {
            episodesToDisplay.add(podcastRSS.getChannel().getEpisodes().get(i));
        }
        epRecycler = findViewById(R.id.episodes_recycler);
        epRecAdapter = new EpisodeRecyclerAdapter(episodesToDisplay,this);
        epRecycler.setAdapter(epRecAdapter);
        epRecycler.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
    }

    @Override
    public void onEpisodeClick(int clickedEpisodeIndex) {
        boolean expanded = episodesToDisplay.get(clickedEpisodeIndex).isExpanded();
        episodesToDisplay.get(clickedEpisodeIndex).setExpanded(!expanded);
        epRecAdapter.notifyItemChanged(clickedEpisodeIndex);
    }
}
