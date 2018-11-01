package com.pfisterfarm.mypublicradio.model;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pfisterfarm.mypublicradio.R;

import java.util.List;

public class EpisodeRecyclerAdapter extends RecyclerView.Adapter<EpisodeRecyclerAdapter.ViewHolder> {

    private final EpisodeClickListener mEpisodeClickListener;

    public interface EpisodeClickListener {
        void onEpisodeClick(int clickedEpisodeIndex);
    }

    private List<Episode> mEpisodes;

    public EpisodeRecyclerAdapter(List<Episode> episodeList, EpisodeClickListener listener) {
        mEpisodes = episodeList;
        mEpisodeClickListener = listener;
    }


    @NonNull
    @Override
    public EpisodeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.episodes_list_element,parent,false);
        return new EpisodeRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeRecyclerAdapter.ViewHolder holder, int position) {
        final Episode thisEpisode = mEpisodes.get(position);
        holder.bind(thisEpisode);
    }

    @Override
    public int getItemCount() {
       if (mEpisodes != null) {
           return mEpisodes.size();
       } else {
           return 0;
       }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final TextView mEpTitle;
        final TextView mEpDesc;
        final LinearLayout mSubItem;
        final ImageView mExpandIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            mEpTitle = (TextView) itemView.findViewById(R.id.episode_title);
            mEpDesc = (TextView) itemView.findViewById(R.id.sub_item_desc);
            mSubItem = (LinearLayout) itemView.findViewById(R.id.sub_item);
            mExpandIcon = (ImageView) itemView.findViewById(R.id.expand_icon);
            itemView.setOnClickListener(this);
        }

        public void bind(Episode bindEpisode) {
            boolean expanded = bindEpisode.isExpanded();
            mSubItem.setVisibility(expanded ? View.VISIBLE : View.GONE);
            mEpTitle.setText(bindEpisode.getTitle());
            mEpDesc.setText(Html.fromHtml(bindEpisode.getDescription()));
            mExpandIcon.setImageResource(expanded ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_right);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mEpisodeClickListener.onEpisodeClick(clickedPosition);
        }
    }
}
