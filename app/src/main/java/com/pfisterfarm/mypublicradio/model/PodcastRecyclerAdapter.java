package com.pfisterfarm.mypublicradio.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pfisterfarm.mypublicradio.MainActivity;
import com.pfisterfarm.mypublicradio.R;
import com.squareup.picasso.Picasso;

public class PodcastRecyclerAdapter extends RecyclerView.Adapter<PodcastRecyclerAdapter.ViewHolder> {

    Podcasts mPodcasts;
    private final MainActivity mParentActivity;

    private final PodcastClickListener mPodcastClickListener;

    public interface PodcastClickListener {
        void onPodcastClick(int clickedPodcastIndex);
    }

    public PodcastRecyclerAdapter(MainActivity parent, Podcasts myPodcasts, PodcastClickListener listener) {
        mPodcasts = myPodcasts;
        mParentActivity = parent;
        mPodcastClickListener = listener;
    }

    @NonNull
    @Override
    public PodcastRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.podcast_list_element,parent,false);
        return new PodcastRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PodcastRecyclerAdapter.ViewHolder holder, int position) {
        Podcast currentPodcast;
        currentPodcast = mPodcasts.getSinglePodcast(position);
        holder.mPodcastText.setText(currentPodcast.getTrackName());
        Picasso.with(holder.mPodcastIcon.getContext()).
                load(currentPodcast.getArtworkUrl100()).
                fit().
                into(holder.mPodcastIcon);
    }

    @Override
    public int getItemCount() {
        if (mPodcasts != null) {
            return mPodcasts.getPodcastCount();
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final ImageView mPodcastIcon;
        final TextView mPodcastText;

        public ViewHolder(View itemView) {
            super(itemView);
            mPodcastIcon = (ImageView) itemView.findViewById(R.id.podcast_element_icon);
            mPodcastText = (TextView) itemView.findViewById(R.id.podcast_element_text);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mPodcastClickListener.onPodcastClick(clickedPosition);
        }
    }

    public void setPodcastList(Podcasts myPodcasts) {
        mPodcasts = myPodcasts;
        notifyDataSetChanged();
    }
}
