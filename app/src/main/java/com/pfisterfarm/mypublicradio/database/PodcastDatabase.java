package com.pfisterfarm.mypublicradio.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.util.Log;

import com.pfisterfarm.mypublicradio.model.Podcast;

@Database(entities = {Podcast.class}, version = 2, exportSchema = false)
public abstract class PodcastDatabase extends RoomDatabase {

    private static final String TAG = PodcastDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "podcasts";
    private static PodcastDatabase sInstance;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Table hasn't actually changed from v1 to v2... we just changed primarykey to autogenerate
            // Empty migration method... nothing to really do here
        }
    };

    public static PodcastDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(TAG, "Creating new database instance");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        PodcastDatabase.class, PodcastDatabase.DATABASE_NAME).addMigrations(MIGRATION_1_2).build();
            }
        }
        Log.d(TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract PodcastDao podcastDao();
}
