package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.recommendation.FacilityRecommendation;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters.SportRecyclerViewAdapter;

/**
 * The activity class for showing all sport for making a workout plan, in the making plan task.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class SelectSportActivity extends AppCompatActivity {
    Handler handler, handler2;
    Runnable runnable, runnable2;
    double latitude= 0;
    double longitude= 0;
    public static List<Sport> finalChoice;
    public static List<Sport> ChosenSport1;
    public static List<Sport> ChosenSport2;
    private List<Facility> FinalFacility;
    private List<Sport> RecommendedSport;
    private List<Sport> OtherSport;
    public static Button mSportChoicesConfirmButton;
    private RecyclerView mRecyclerView, mRecyclerView2;
    public static SportRecyclerViewAdapter mAdapter, mAdapter2;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initAdapter();
        initButton();
    }


    private List<Sport> getRecommendedSport() {
        return (List<Sport>) getIntent().getSerializableExtra("RecommendedSports");
    }

    private List<Sport> getOtherSport() {
        return (List<Sport>) getIntent().getSerializableExtra("OtherSports");
    }

    private void initView(){
        finalChoice= new ArrayList<>();
        setContentView(R.layout.activity_select_sport);
        mSportChoicesConfirmButton = findViewById(R.id.sport_choices_confirm_button);
        mRecyclerView = findViewById(R.id.recycler_view);
        RecommendedSport = getRecommendedSport();
        mSportChoicesConfirmButton.setEnabled(false);
        OtherSport = getOtherSport();
        latitude= getLatitude();
        longitude= getLongitude();
        initHandler();
        handler.post(runnable);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Initialize adapter for recyclerview.
     *
     */
    private void initAdapter(){
        mAdapter = new SportRecyclerViewAdapter(RecommendedSport);
        LinearLayoutManager manager = new GridLayoutManager(SelectSportActivity.this, 2);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView2 = findViewById(R.id.recycler_view2);
        mAdapter2 = new SportRecyclerViewAdapter(OtherSport);
        LinearLayoutManager manager2 = new GridLayoutManager(SelectSportActivity.this, 2);
        mRecyclerView2.setHasFixedSize(true);
        mRecyclerView2.setLayoutManager(manager2);
        mRecyclerView2.setAdapter(mAdapter2);
        ChosenSport1 = mAdapter.chosenSportList;
        ChosenSport2 = mAdapter2.chosenSportList;
        finalChoice.clear();
        finalChoice.addAll(ChosenSport1);
        finalChoice.addAll(ChosenSport2);


    }
    private void initHandler2() {
        handler2 = new Handler();
        runnable2 = new Runnable() {
            public void run() {
                if(finalChoice.size()== 0){
                    handler.postDelayed(this, 500);
                }
                else{
                    mSportChoicesConfirmButton.setEnabled(true);
                }
            }
        };
    }

    private void initButton(){
        mSportChoicesConfirmButton.setOnClickListener(view -> {
            Context context = SelectSportActivity.this;
            FinalFacility= FacilityRecommendation.getFacilitiesBySports(SelectSportActivity.this, finalChoice, new Coordinates(latitude, longitude, ""), 20);
            Intent intent = new Intent(context, SelectFacilityPlanActivity.class);
            intent.putExtra("FacilityQualified", (Serializable) FinalFacility);
            intent.putExtra("longitude", (Serializable) longitude);
            intent.putExtra("latitude", (Serializable) latitude);
            startActivity(intent);
        });
    }

    private void initHandler(){
        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                if (latitude== 0) {
                    Toast.makeText(SelectSportActivity.this, "not yet", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(this, 1000);
                }
            }
        };
    }

    public double getLatitude() {
        return (double) getIntent().getSerializableExtra("latitude1");
    }

    public double getLongitude() {
        return (double) getIntent().getSerializableExtra("longitude1");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}