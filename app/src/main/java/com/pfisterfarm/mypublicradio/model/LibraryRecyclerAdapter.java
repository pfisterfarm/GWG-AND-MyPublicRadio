package com.pfisterfarm.mypublicradio.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pfisterfarm.mypublicradio.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LibraryRecyclerAdapter extends RecyclerView.Adapter<LibraryRecyclerAdapter.ViewHolder> {

    private List<Podcast> mLibrary;

    public LibraryRecyclerAdapter(List<Podcast> podcastList) {
        mLibrary = podcastList;
    }

    @NonNull
    @Override
    public LibraryRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.library_element,parent,false);
        return new LibraryRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryRecyclerAdapter.ViewHolder holder, int position) {
        final Podcast thisPodcast = mLibrary.get(position);
        holder.bind(thisPodcast);
    }

    @Override
    public int getItemCount() {
        if (mLibrary != null) {
            return mLibrary.size();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView mLibraryIcon;
        final TextView mLibraryTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            mLibraryIcon = (ImageView) itemView.findViewById(R.id.rv_podcast_icon);
            mLibraryTitle = (TextView) itemView.findViewById(R.id.rv_podcast_title);

        }

        public void bind(Podcast bindLibrary) {
            mLibraryTitle.setText(bindLibrary.getTrackName());
            Picasso.with(mLibraryIcon.getContext()).
                    load(bindLibrary.getArtworkUrl100()).
                    fit().
                    into(mLibraryIcon);
        }
    }

    public void setLibrary(List<Podcast> incomingList) {
        mLibrary = incomingList;
        notifyDataSetChanged();
    }
}
