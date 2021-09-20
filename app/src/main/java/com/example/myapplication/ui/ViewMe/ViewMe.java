package com.example.myapplication.ui.ViewMe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.myapplication.R;
import com.example.myapplication.ui.main.FragmentCommunity;
import com.example.myapplication.ui.main.FragmentHome;
import com.example.myapplication.ui.main.FragmentPlan;
import com.example.myapplication.ui.main.MainActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

public class ViewMe extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private HistoryPagerAdapter historyPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_me);

        tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        viewPager=(ViewPager) findViewById(R.id.view_pager);
        historyPagerAdapter= new HistoryPagerAdapter(getSupportFragmentManager());
        historyPagerAdapter.AddFragment(new FragmentMe(), "Me");
        historyPagerAdapter.AddFragment(new FragmentHistory(),"History");

        viewPager.setAdapter(historyPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment= null;
                    switch (item.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment= new FragmentHome();
                            break;
                        case R.id.navigation_plan:
                            selectedFragment= new FragmentPlan();
                            break;
                        case R.id.navigation_community:
                            selectedFragment= new FragmentCommunity();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

}