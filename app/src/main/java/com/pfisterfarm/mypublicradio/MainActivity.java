package com.pfisterfarm.mypublicradio;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pfisterfarm.mypublicradio.model.Podcasts;
import com.pfisterfarm.mypublicradio.network.PodcastInterface;
import com.pfisterfarm.mypublicradio.network.podcastClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private final static String LOG_TAG = "debugpublicradio";

    private Podcasts podcastDirectory;
    private podcastClient mPodcastClient;
    private PodcastInterface mPodcastInterface;

    View podcastRecycler;
    PodcastRecyclerAdapter mPodcastAdapter;

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

        fetchPodcastList();

        podcastRecycler = findViewById(R.id.podcast_list);
        setupRecyclerView((android.support.v7.widget.RecyclerView) podcastRecycler);

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
                } else {
                    Log.d(LOG_TAG, "Call was successful, but no podcasts found");
                }
            }
            public void onFailure(Call<Podcasts> call, Throwable t) {
                    Log.d(LOG_TAG, "Failure trying to fetch podcast list");
            }
        });
     }

     private void setupRecyclerView(android.support.v7.widget.Recyclerview recyclerView) {
        mPodcastAdapter = new PodcastRecyclerAdapter();
        recyclerView.setAdapter(mPodcastAdapter);

     }

     public static class PodcastRecyclerAdapter extends RecyclerView.Adapter<PodcastRecyclerAdapter.ViewHolder> {

         @NonNull
         @Override
         public PodcastRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
             View view = LayoutInflater.from(parent.getContext())
                     .inflate(R.layout.podcast_list_element,parent,false);
             return new ViewHolder(view);
         }

         @Override
         public void onBindViewHolder(@NonNull PodcastRecyclerAdapter.ViewHolder holder, int position) {
            holder.mPodcastText(podcastDirectory.)
         }

         @Override
         public int getItemCount() {
             if (podcastDirectory != null) {
                 return podcastDirectory.getPodcastCount();
             } else {
                 return 0;
             }
         }

         class ViewHolder extends RecyclerView.ViewHolder {
             final ImageView mPodcastIcon;
             final TextView mPodcastText;

             public ViewHolder(View itemView) {
                 super(itemView);
                 mPodcastIcon = (ImageView) itemView.findViewById(R.id.podcast_element_icon);
                 mPodcastText = (TextView) itemView.findViewById(R.id.podcast_element_text);
             }
         }
     }
}
