package com.example.videoapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.videoapp.R;
import com.example.videoapp.object.Video;

import java.util.ArrayList;
import java.util.List;

public class VideoAdapter extends ArrayAdapter<Video> {
    private Context ct;
    private ArrayList<Video> arr;
    public VideoAdapter(@NonNull Context context, int resource, @NonNull List<Video> objects) {
        super(context, resource, objects);
        this.ct = context;
        this.arr = new ArrayList<>(objects);
    }
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater)ct.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item_video, null);
        }
        if(arr.size() > 0){
            Video video = this.arr.get(position);
            TextView title = convertView.findViewById(R.id.videoTitle);
            TextView description = convertView.findViewById(R.id.videoDescription);
            ImageView thumbnails = convertView.findViewById(R.id.videoThumbnails);

            title.setText(video.getTitle());
            description.setText(video.getDescription());
            Glide.with(this.ct).load(video.getThumbnails()).into(thumbnails);
        }
        return convertView;
    }
}
