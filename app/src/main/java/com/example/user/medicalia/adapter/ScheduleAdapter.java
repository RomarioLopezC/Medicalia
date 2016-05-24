package com.example.user.medicalia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.medicalia.R;
import com.example.user.medicalia.models.Hour;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by USER on 22/05/2016.
 */
public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private final Context context;
    private List<Hour> hours;

    public ScheduleAdapter(Context context, List<Hour> hours) {
        this.context = context;
        this.hours = hours;
    }

    public void swap(List<Hour> hours){
        this.hours.clear();
        this.hours.addAll(hours);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Hour currentHour = hours.get(position);
        String hourFormat = currentHour.getHour();
        holder.textView_hour.setText(hourFormat);
        holder.textView_am.setText(currentHour.getHour_format());
        holder.textView_available.setText(currentHour.getInfo());
    }

    @Override
    public int getItemCount() {
        return hours.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.background_available)
        public LinearLayout background_available;

        @Bind(R.id.textView_hour)
        public TextView textView_hour;

        @Bind(R.id.textView_am)
        public TextView textView_am;

        @Bind(R.id.textView_available)
        public TextView textView_available;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
