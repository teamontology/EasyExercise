package com.example.myapplication.ui.addPlan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ui.checkin.CheckIn;
import com.example.myapplication.classes.Facility;
import com.example.myapplication.R;

import java.util.List;

public class FacilityRecyclerViewAdapter2 extends RecyclerView.Adapter<FacilityRecyclerViewAdapter2.MyViewHolder> {

    private List<Facility> mFacilityList;
    private Context mContext;

    public FacilityRecyclerViewAdapter2(Context context, List<Facility> facilityList) {
        mFacilityList = facilityList;
        mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facility_item_row2, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Facility facility = mFacilityList.get(position);
        holder.mSelectFacilityName.setText(facility.getName());
        holder.mSelectFacilityImage.setImageResource(R.drawable.tanjong);
        holder.mSelectFacilityDistance.setText("0.6 km away");
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Class destinationActivity = CheckIn.class;
                Intent startChildActivityIntent = new Intent(mContext, destinationActivity);
                mContext.startActivity(startChildActivityIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFacilityList == null ? 0 : mFacilityList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private View view;
        private TextView mSelectFacilityName;
        private ImageView mSelectFacilityImage;
        private TextView mSelectFacilityDistance;

        private MyViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            mSelectFacilityDistance = (TextView) itemView.findViewById(R.id.plan_date);
            mSelectFacilityImage = (ImageView) itemView.findViewById(R.id.plan_sport_image);
            mSelectFacilityName = (TextView) itemView.findViewById(R.id.plan_sport_name);

        }
    }
}
