package com.group4.togolist.view.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group4.togolist.R;
import com.group4.togolist.view.fragments.HistoryFragment;
import com.group4.togolist.view.adapters.MyViewPagerAdapter;
import com.group4.togolist.view.fragments.UpcomingFragment;
import com.group4.togolist.model.Trip;
import com.group4.togolist.viewmodel.HomeViewModel;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Class do :
     * Created by Group 4 ITI (Eng/Bassem - Eng Fatma - Eng Ali)
     */
    private HomeViewModel homeViewModel;
    private Button btnUpcoming, btnPastTrip;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private List<Trip> upcomingTrip;
    private List<Trip> pastTrips;



    private ImageButton imgBtnHome, imgBtnProfile , imageBtnapp , imageBtnSync;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fltBtnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initcomponent();
        homeViewModel = ViewModelProviders.of(this, new MyViewModelFactory(HomeActivity.this)).get(HomeViewModel.class);


        viewPager = findViewById(R.id.viewPager);
        Fragment[] fragments = new Fragment[2];



        UpcomingFragment upcomingFragment = new UpcomingFragment(this, homeViewModel);
        fragments[0] = upcomingFragment;

        fragments[1] = new HistoryFragment(this, homeViewModel);


        FragmentManager fm = getSupportFragmentManager();
        myViewPagerAdapter = new MyViewPagerAdapter(fm, 0, fragments);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        btnUpcoming.setEnabled(false);
                        btnUpcoming.setBackground(getDrawable(R.drawable.buttonbarhome));
                        btnUpcoming.setTextColor(getColor(R.color.background_offwhite));
                        btnPastTrip.setEnabled(true);
                        btnPastTrip.setBackground(getDrawable(R.drawable.buttonbarhome2));
                        btnPastTrip.setTextColor(getColor(R.color.colorPrimary));
/*
                        btnUpcoming.setTextColor(getResources().getColor(R.color.background_offwhite));
                        btnUpcoming.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        btnUpcoming.setBackgroundResource((R.drawable.buttonbarhome));

                        btnPastTrip.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        btnPastTrip.setBackgroundColor(getResources().getColor(R.color.background_offwhite));
                        btnPastTrip.setBackgroundResource((R.drawable.buttonbarhome2));*/
                        break;
                    case 1:
                        btnUpcoming.setEnabled(true);
                        btnUpcoming.setBackground(getDrawable(R.drawable.buttonbarhome2right));
                        btnUpcoming.setTextColor(getColor(R.color.colorPrimary));
                        btnPastTrip.setEnabled(false);
                        btnPastTrip.setBackground(getDrawable(R.drawable.buttonbarhomeright));
                        btnPastTrip.setTextColor(getColor(R.color.background_offwhite));
                      /*
                        btnUpcoming.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        btnUpcoming.setBackgroundColor(getResources().getColor(R.color.background_offwhite));
                        btnUpcoming.setBackgroundResource((R.drawable.buttonbarhome));

                        btnPastTrip.setTextColor(getResources().getColor(R.color.background_offwhite));
                        btnPastTrip.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                        btnPastTrip.setBackgroundResource((R.drawable.buttonbarhome2));
                        break;*/
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    /**
     * set up views object
     */
    private void initcomponent() {


        btnUpcoming = findViewById(R.id.btnUpcoming);
        btnPastTrip = findViewById(R.id.btnPastTrip);

        imgBtnHome = findViewById(R.id.imageBtnHome);
        imgBtnProfile = findViewById(R.id.imageBtnProfile);
        imageBtnapp = findViewById(R.id.imageBtnapp);
        imageBtnSync = findViewById(R.id.imageBtnSync);
        fltBtnAdd = findViewById(R.id.fABtnAddNote);

        btnUpcoming.setOnClickListener(this);
        btnPastTrip.setOnClickListener(this);

        imgBtnHome.setEnabled(false);


        imageBtnapp.setOnClickListener(this);
        imgBtnProfile.setOnClickListener(this);
        imageBtnSync.setOnClickListener(this);
        fltBtnAdd.setOnClickListener(this);
    }

    /**
     *
     */

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpcoming:
                viewPager.setCurrentItem(0);
                btnUpcoming.setEnabled(false);
                btnPastTrip.setEnabled(true);
                break;
            case R.id.btnPastTrip:
                viewPager.setCurrentItem(1);
                btnUpcoming.setEnabled(true);
                btnPastTrip.setEnabled(false);
                break;
            case R.id.imageBtnProfile:
                homeViewModel.goToProfile();
                break;
            case R.id.fABtnAddNote:
                homeViewModel.goToAddForm();
                break;
            case  R.id.imageBtnapp:
                homeViewModel.goToApp();
                break;
            case R.id.imageBtnSync:
                homeViewModel.syncFirebase();
        }
    }


    /**
     * HomeViewModelFactory
     */

    class MyViewModelFactory implements ViewModelProvider.Factory {
        private Activity mActivity;


        public MyViewModelFactory(Activity activity) {
            mActivity = activity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new HomeViewModel(mActivity);
        }
    }

    @Override
    public void onBackPressed() {
//        finishAffinity();

        homeViewModel.exit();

    }
}
