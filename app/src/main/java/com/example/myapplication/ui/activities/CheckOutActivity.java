package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.beans.Sport;

import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity {
    private ImageView profileView, sportView;
    private Button shareButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        shareButton=findViewById(R.id.shareButton);
        profileView=findViewById(R.id.checkoutProfile);

    }
}