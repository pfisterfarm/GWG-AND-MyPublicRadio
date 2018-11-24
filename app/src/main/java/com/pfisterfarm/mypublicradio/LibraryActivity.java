package com.pfisterfarm.mypublicradio;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.pfisterfarm.mypublicradio.database.PodcastDatabase;
import com.pfisterfarm.mypublicradio.model.LibraryRecyclerAdapter;
import com.pfisterfarm.mypublicradio.model.Podcast;
import com.pfisterfarm.mypublicradio.network.PodcastInterface;
import com.pfisterfarm.mypublicradio.network.podcastClient;
import com.pfisterfarm.mypublicradio.utils.AppExecutors;

import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    List<Podcast> mMyLibrary;
    RecyclerView rvMyLibrary;
    LibraryRecyclerAdapter myLibRecyclerAdapter;
    BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_dir:
                    Intent startLibrary = new Intent(LibraryActivity.this, MainActivity.class);
                    startActivity(startLibrary);
                    return true;
                case R.id.navigation_mylib:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_library);
        setTitle("MyPublicRadio - My Library");
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.getMenu().findItem(R.id.navigation_dir).setChecked(false);
        navigation.getMenu().findItem(R.id.navigation_mylib).setChecked(true);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        rvMyLibrary = (RecyclerView) findViewById(R.id.rvMyLibrary);
        setupRecycler(rvMyLibrary);
        fetchPodcastLibrary();
    }

    private void fetchPodcastLibrary() {
        final List<Podcast> returnList;
        final PodcastDatabase mDb = PodcastDatabase.getInstance(getApplicationContext());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {

            @Override
            public void run() {
            mMyLibrary = mDb.podcastDao().loadAllPodcasts();
            myLibRecyclerAdapter.setLibrary(mMyLibrary);
            }
        });
    };

    private void setupRecycler(RecyclerView libraryRecycler) {
        myLibRecyclerAdapter = new LibraryRecyclerAdapter(mMyLibrary);
        libraryRecycler.setAdapter(myLibRecyclerAdapter);
        libraryRecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        libraryRecycler.setHasFixedSize(true);

    }
}
