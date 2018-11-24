package com.pfisterfarm.mypublicradio.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.pfisterfarm.mypublicradio.model.Podcast;

import java.util.List;

@Dao
public interface PodcastDao {

    @Query("SELECT * FROM podcasts ORDER BY title")
    List<Podcast> loadAllPodcasts();

    @Insert
    void insertPodcast(Podcast podcast);

    @Delete
    void deletePodcast(Podcast podcast);

    @Query("SELECT * FROM podcasts WHERE id = :id")
    Podcast getPodcast(long id);

    @Query("SELECT * FROM podcasts WHERE title=:title")
    Podcast findPodcast(String title);

}
