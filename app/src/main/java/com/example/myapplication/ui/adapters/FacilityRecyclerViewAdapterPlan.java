package com.example.myapplication.ui.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Facility;
import com.example.myapplication.ui.activities.AddPlanActivity;

import java.util.List;

public class FacilityRecyclerViewAdapterPlan extends RecyclerView.Adapter<FacilityRecyclerViewAdapterPlan.MyViewHolder> {

    private Facility chosenFacility;
    private final List<Facility> mFacilityList;
    private final Context mContext;

    public FacilityRecyclerViewAdapterPlan(Context context, List<Facility> facilityList) {
        mFacilityList = facilityList;
        mContext = context;
    }

    public Facility getChosenFacility() {
        return chosenFacility;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facility_item_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Facility facility = mFacilityList.get(position);
        holder.mSelectFacilityName.setText(facility.getName());
//        holder.mSelectFacilityDistance.setText("0.8 km");
        holder.view.setOnClickListener(view -> {
            chosenFacility= facility;
            Intent intent= new Intent(mContext, AddPlanActivity.class);
            intent.putExtra("ChosenFacility", chosenFacility);
            mContext.startActivity(intent);
            ((Activity)mContext).finish();
        });
    }

    @Override
    public int getItemCount() {
        return mFacilityList == null ? 0 : mFacilityList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final View view;
        private final TextView mSelectFacilityName;
        private final ImageView mSelectFacilityImage;
        private final TextView mSelectFacilityDistance;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            mSelectFacilityDistance = itemView.findViewById(R.id.plan_date);
            mSelectFacilityImage = itemView.findViewById(R.id.plan_sport_image);
            mSelectFacilityName = itemView.findViewById(R.id.plan_sport_name);

        }
    }
}
