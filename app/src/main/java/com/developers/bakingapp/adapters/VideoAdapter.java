package com.developers.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.developers.bakingapp.R;
import com.developers.bakingapp.model.Step;
import com.developers.bakingapp.util.ClickCallBack;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Amanjeet Singh on 8/11/17.
 */

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private Context context;
    private List<Step> stepList;
    private ClickCallBack clickCallBack;

    public VideoAdapter(Context context, List<Step> stepList) {
        this.context = context;
        this.stepList = stepList;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.video_list, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, final int position) {
        String shortDescription = stepList.get(position).getShortDescription();
        holder.stepTextView.setText(shortDescription);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallBack.onClick(context,stepList.get(position).getId(),
                        stepList.get(position).getDescription(),
                        stepList.get(position).getVideoURL(),
                        stepList.get(position).getThumbnailURL());
            }
        });
    }

    public void setOnClick(ClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_name_text_view)
        TextView stepTextView;
        @BindView(R.id.card_video_list)
        CardView cardView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
