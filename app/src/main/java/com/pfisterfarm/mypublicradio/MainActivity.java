package com.pfisterfarm.mypublicradio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.pfisterfarm.mypublicradio.model.Podcast;
import com.pfisterfarm.mypublicradio.network.PodcastInterface;
import com.pfisterfarm.mypublicradio.network.podcastClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private ArrayList<Podcast> podcastDirectory;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        podcastDirectory = new ArrayList<Podcast>();
        fetchPodcastList();

    }

    public void fetchPodcastList() {
        podcasts = new ArrayList<Podcast>();
        PodcastInterface podcastInterface = podcastClient.getClient();
        Call<ArrayList<Podcast>> podcasts = podcastInterface.fetchPodcasts();

        podcasts.enqueue(new Callback<ArrayList<Podcast>>() {
            @Override
            public void onResponse(Call<ArrayList<Podcast>> call, Response<ArrayList<Podcast>> response) {
                podcastDirectory = response.body();
            }

            @Override
            public void onFailure(Call<ArrayList<Podcast>> call, Throwable t) {
                Log.v("fetch podcasts failed: ", t.getMessage());
            }

        });
    }
}
