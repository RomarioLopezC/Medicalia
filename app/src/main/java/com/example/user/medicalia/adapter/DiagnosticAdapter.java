package com.example.user.medicalia.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.user.medicalia.R;
import com.example.user.medicalia.models.Diagnostic;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Kev' on 23/05/2016.
 */
public class DiagnosticAdapter extends RecyclerView.Adapter<DiagnosticAdapter.ViewHolder> {

    private final Context context;
    private List<Diagnostic> diagnosticList;

    public DiagnosticAdapter(Context context, List<Diagnostic> diagnosticList) {
        this.context = context;
        if (diagnosticList == null){
            diagnosticList = new ArrayList<Diagnostic>();
        }
        this.diagnosticList = diagnosticList;
    }

    public void swap(List<Diagnostic> diagnosticList){
        this.diagnosticList.clear();
        this.diagnosticList.addAll(diagnosticList);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_diagnostic, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Diagnostic currentDiagnostic = diagnosticList.get(position);
        holder.text_doctor.setText(currentDiagnostic.getDoctor());
        holder.text_description.setText(currentDiagnostic.getDescription());
        holder.text_treatment.setText(currentDiagnostic.getTreatment());
        holder.text_date.setText(currentDiagnostic.getDateTime());
    }

    @Override
    public int getItemCount() {
        return diagnosticList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.container_diagnostics)
        public LinearLayout rl_container;

        @Bind(R.id.tv_doctor)
        public TextView text_doctor;
        @Bind(R.id.tv_description)
        public TextView text_description;
        @Bind(R.id.tv_treatment)
        public TextView text_treatment;
        @Bind(R.id.tv_datetime)
        public TextView text_date;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
