package sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import sg.edu.ntu.scse.cz2006.ontology.easyexercise.R;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Coordinates;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.location.Facility;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.Sport;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.beans.sport.PrivateWorkoutPlan;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.database.WorkoutDatabaseManager;
import sg.edu.ntu.scse.cz2006.ontology.easyexercise.ui.adapters.AddPlanAdapter;

/**
 * The activity class for adding exercise plan in the making plan task.
 *
 * @author Ruan Donglin
 * @author Mao Yiyun
 */

public class AddPlanActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnMapReadyCallback {
    private Button button;
    private RecyclerView rv_test;
    private Facility facility;
    private GoogleMap mMap;
    private AddPlanAdapter firstAdapter;
    private TextView facilityView;
    private TextView addressView;
    private TextView postalView;
    private Sport finalSport;
    private PrivateWorkoutPlan workoutPlan;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initMap();
        initAdapter();
        initButton();
    }

    private Facility getFacility() {
        return (Facility) getIntent().getSerializableExtra("ChosenFacility");
    }

    /**
     * Set google map and add marker for the designated location
     *
     * @param googleMap the main class of the Google Maps SDK for Android
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        Coordinates c = facility.getCoordinates();
        LatLng cur = new LatLng(c.getLatitude(), c.getLongitude());
        mMap.addMarker(new MarkerOptions()
                .position(cur)
                .title(facility.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cur, 15f));
    }

    private void initView() {
        setContentView(R.layout.activity_add_plan);
        facility = getFacility();
        rv_test = findViewById(R.id.check_in_sport_recycler);
        button = findViewById(R.id.publish_plan_button);
        facilityView = findViewById(R.id.location_view);
        addressView = findViewById(R.id.address_view);
        postalView = findViewById(R.id.postal_view);
        facilityView.setText(facility.getName());
        addressView.setText(facility.getAddress());
        postalView.setText(facility.getPostalCode());
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Initialize map fragment for displaying google map.
     */
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapview);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }

    private void initAdapter() {
        rv_test.setLayoutManager(new LinearLayoutManager(AddPlanActivity.this, LinearLayoutManager.VERTICAL, false));
        firstAdapter = new AddPlanAdapter(AddPlanActivity.this, facility);
        rv_test.setAdapter(firstAdapter);
        firstAdapter.setOnItemClickListener(this);
    }

    private void initButton() {
        button.setOnClickListener(view -> {
            if (finalSport == null) {
                Toast.makeText(AddPlanActivity.this, "Please select a sport", Toast.LENGTH_SHORT).show();
            } else {
                workoutPlan = new PrivateWorkoutPlan(finalSport, facility, "");
                FirebaseDatabase database = FirebaseDatabase.getInstance("https://cz2006-9c928-default-rtdb.asia-southeast1.firebasedatabase.app/");
                DatabaseReference mDatabase = database.getReference().child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("WorkoutPlan");
                String postId = mDatabase.push().getKey();
                WorkoutDatabaseManager.FirebasePrivateWorkoutPlan firebasePlan = new WorkoutDatabaseManager.FirebasePrivateWorkoutPlan(workoutPlan, postId);
                mDatabase.child(postId).setValue(firebasePlan);

                Intent intent = new Intent(AddPlanActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        finalSport = firstAdapter.finalChoice;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        this.finish();
        return super.onOptionsItemSelected(item);
    }
}