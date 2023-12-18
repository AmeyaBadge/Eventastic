package com.amykoder.eventastic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amykoder.eventastic.model.EventModel;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomRecyclerEventsAdapter extends RecyclerView.Adapter<CustomRecyclerEventsAdapter.ViewHolder> {
    Context context;
    ArrayList<EventModel> arrLEvents;
    public CustomRecyclerEventsAdapter(Context context, ArrayList<EventModel> arrLEvents) {
        this.context = context;
        this.arrLEvents = arrLEvents;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.custom_event_cardview, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(arrLEvents.get(position).getTitle());
        holder.address.setText(arrLEvents.get(position).getAddress());
        holder.time.setText(arrLEvents.get(position).getTime());
        holder.date.setText(arrLEvents.get(position).getDate());
//        holder.image.setImageResource(arrLEvents.get(position).image);
        Glide.with(context).load(arrLEvents.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return arrLEvents.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView title, address, date, time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.eventCardImage);
            title = itemView.findViewById(R.id.eventCardTitle);
            address = itemView.findViewById(R.id.eventCardAddress);
            date = itemView.findViewById(R.id.eventCardDate);
            time = itemView.findViewById(R.id.eventCardTime);
        }
    }
}
