package com.example.user.medicalia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.medicalia.R;
import com.example.user.medicalia.models.Day;
import com.example.user.medicalia.models.Hour;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    public String COLOR_AVAILABLE = "#8ED081";
    public String COLOR_LUNCH = "#CC5521";
    public String COLOR_OCCUPIED = "#22406c";

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
        String info = currentHour.getInfo();
        String color = COLOR_AVAILABLE;

        switch (info){
            case Day.AVAILABLE:
                color = COLOR_AVAILABLE;
                break;
            case Day.OCCUPIED:
                color = COLOR_OCCUPIED;
                break;
            case Day.LUNCH:
                color = COLOR_LUNCH;
                break;
        }

        holder.textView_hour.setText(hourFormat);
        holder.textView_am.setText(currentHour.getHour_format());
        holder.textView_available.setText(info);
        holder.background_available.setBackgroundColor(Color.parseColor(color));
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
