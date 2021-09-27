package com.example.universitysportsfacilitybookingapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Queue;

public class BookingsAdapter extends RecyclerView.Adapter<BookingsAdapter.ViewHolder> {
    private List<Booking> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    // data is passed into the constructor
    BookingsAdapter(Context context, List<Booking> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.booking_ticket, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String facilityName = String.valueOf(mData.get(position).getFacilityName());
        String facilityAddress = mData.get(position).getFacilityAddress();
        String date = mData.get(position).getDate();
        String time = mData.get(position).getTime();
        String pax = mData.get(position).getPax();

        holder.tvFacilityName.setText(facilityName);
        holder.tvAddress.setText(facilityAddress);
        holder.tvDate.setText(date);
        holder.tvTime.setText(time);
        holder.tvPax.setText(pax + " pax");
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvFacilityName, tvDate, tvTime, tvAddress, tvPax;

        ViewHolder(View itemView) {
            super(itemView);
            tvFacilityName = itemView.findViewById(R.id.tvFacilityName);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTime = itemView.findViewById(R.id.tvTime);
            tvAddress = itemView.findViewById(R.id.tvAddress);
            tvPax = itemView.findViewById(R.id.tvPax);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) {
                mClickListener.onItemClick(view, mData, getAdapterPosition());
            }
        }

    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, List<Booking> tickets, int position);
    }
}
