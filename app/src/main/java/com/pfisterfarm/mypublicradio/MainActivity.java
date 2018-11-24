package com.pfisterfarm.mypublicradio;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.pfisterfarm.mypublicradio.model.Podcast;
import com.pfisterfarm.mypublicradio.model.PodcastRecyclerAdapter;
import com.pfisterfarm.mypublicradio.model.Podcasts;
import com.pfisterfarm.mypublicradio.network.PodcastInterface;
import com.pfisterfarm.mypublicradio.network.podcastClient;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PodcastRecyclerAdapter.PodcastClickListener {

    private TextView mTextMessage;

    BottomNavigationView navigation;

    private final static String LOG_TAG = "debugpublicradio";

    private static Podcasts podcastDirectory;
    private podcastClient mPodcastClient;
    private PodcastInterface mPodcastInterface;

    View podcastRecycler;
    PodcastRecyclerAdapter mPodcastAdapter;
    ;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dir:
                    return true;
                case R.id.navigation_mylib:
                    Intent startLibrary = new Intent(MainActivity.this, LibraryActivity.class);
                    startActivity(startLibrary);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("MyPublicRadio - All Podcasts");

        mTextMessage = (TextView) findViewById(R.id.message);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().findItem(R.id.navigation_dir).setChecked(true);
        navigation.getMenu().findItem(R.id.navigation_mylib).setChecked(false);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        podcastRecycler = findViewById(R.id.podcast_list);
        setupRecyclerView((android.support.v7.widget.RecyclerView) podcastRecycler);

        fetchPodcastList();

        Stetho.initialize(
                Stetho.newInitializerBuilder(this).
                        enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this)).build());

    }

    @Override
    protected void onResume() {
        super.onResume();
        navigation.getMenu().findItem(R.id.navigation_dir).setChecked(true);
        navigation.getMenu().findItem(R.id.navigation_mylib).setChecked(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fetchPodcastList() {
        PodcastInterface podInt = podcastClient.getInstance();
        Call<Podcasts> podcastCall = podInt.fetchPodcastList("NPR", "podcast");
        podcastCall.enqueue(new Callback<Podcasts>() {
            @Override
            public void onResponse(Call<Podcasts> call, Response<Podcasts> response) {
                Podcasts podcasts = response.body();
                Log.d(LOG_TAG, "URL = " + response.raw().request().url());
                if (podcasts.getPodcastCount() > 0) {
                    podcastDirectory = podcasts;
                    mPodcastAdapter.setPodcastList(podcastDirectory);
                } else {
                    Log.d(LOG_TAG, "Call was successful, but no podcasts found");
                }
            }
            public void onFailure(Call<Podcasts> call, Throwable t) {
                    Log.d(LOG_TAG, "Failure trying to fetch podcast list");
            }
        });
     }

     private void setupRecyclerView(RecyclerView recyclerView) {
        final int CELL_WIDTH = 150;
        mPodcastAdapter = new PodcastRecyclerAdapter(this, podcastDirectory, this);
        recyclerView.setAdapter(mPodcastAdapter);
        float screenWidth  = (Resources.getSystem().getDisplayMetrics().widthPixels
                / Resources.getSystem().getDisplayMetrics().density);
        int numColumns = (int) screenWidth / CELL_WIDTH;
        recyclerView.setLayoutManager(new GridLayoutManager(this,numColumns,GridLayoutManager.VERTICAL,false));
        recyclerView.setHasFixedSize(true);
     }

    @Override
    public void onPodcastClick(int clickedPodcastIndex) {
        final String SEND_PODCAST = "send_podcast";
        Intent intent = new Intent(this, PodcastDetail.class);
        intent.putExtra(SEND_PODCAST, podcastDirectory.getSinglePodcast(clickedPodcastIndex));
        startActivity(intent);
    }

}
