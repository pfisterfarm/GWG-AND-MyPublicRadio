package com.pfisterfarm.mypublicradio.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.pfisterfarm.mypublicradio.model.Podcast;

@Database(entities = {Podcast.class}, version = 1, exportSchema = false)
public abstract class PodcastDatabase extends RoomDatabase {

    private static final String TAG = PodcastDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "podcasts";
    private static PodcastDatabase sInstance;

    public static PodcastDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        PodcastDatabase.class, PodcastDatabase.DATABASE_NAME).build();
            }
        }
        Log.d(TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract PodcastDao podcastDao();
}
