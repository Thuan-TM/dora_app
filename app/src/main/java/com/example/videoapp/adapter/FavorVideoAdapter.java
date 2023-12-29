package com.example.videoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.videoapp.R;
import com.example.videoapp.object.Video;

import java.util.ArrayList;

public class FavorVideoAdapter extends RecyclerView.Adapter<FavorVideoAdapter.FavorViewHolder>{
    @NonNull
    @Override
    public FavorVideoAdapter.FavorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favor_video, parent, false);
        return new FavorVideoAdapter.FavorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavorVideoAdapter.FavorViewHolder holder, int position) {
        Video video = videoArr.get(position);

        holder.title.setText(video.getTitle());
        holder.description.setText(video.getDescription());
        Glide.with(context).load(video.getThumbnails()).into(holder.thumbnails);
    }

    @Override
    public int getItemCount() {
        return videoArr.size();
    }

    private Context context;
    private ArrayList<Video> videoArr;
    private FavorVideoAdapter.OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(FavorVideoAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public FavorVideoAdapter(Context context, ArrayList<Video> videoArr) {
        this.context = context;
        this.videoArr = videoArr;
    }

    public class FavorViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private ImageView thumbnails;

        public FavorViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.videoTitle);
            description = itemView.findViewById(R.id.videoDescription);
            thumbnails = itemView.findViewById(R.id.videoThumbnails);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
