package com.group4.togolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.group4.togolist.R;

import com.group4.togolist.fragments.HistoryFragment;
import com.group4.togolist.fragments.MyViewPagerAdapter;
import com.group4.togolist.fragments.UpcomingFragment;
import com.group4.togolist.model.Trip;
import com.group4.togolist.viewmodel.HomeViewModel;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Class do :
     * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
     */
    private HomeViewModel homeViewModel;
    private Button btnUpcoming, btnPastTrip;
   private ViewPager viewPager;
   private MyViewPagerAdapter myViewPagerAdapter;
    private List<Trip> upcomingTrip ;
    private List<Trip> pastTrips;

    private ImageButton imgBtnHome , imgBtnProfile;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fltBtnAdd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initcomponent();
        homeViewModel = ViewModelProviders.of(this , new MyViewModelFactory(HomeActivity.this)).get(HomeViewModel.class);

        btnUpcoming = findViewById(R.id.btnUpcoming);
        btnPastTrip = findViewById(R.id.btnPastTrip);

        btnUpcoming.setOnClickListener(this);
        btnPastTrip.setOnClickListener(this);

        viewPager = findViewById(R.id.viewPager);
        Fragment[] fragments = new Fragment[2];

        try {
            upcomingTrip = homeViewModel.getUpcomingTrip();
            pastTrips = homeViewModel.getEndedTrip();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        UpcomingFragment upcomingFragment = new UpcomingFragment(this ,upcomingTrip ,homeViewModel);
        fragments[0] = upcomingFragment;

        fragments[1] = new HistoryFragment(this,pastTrips,homeViewModel);


        FragmentManager fm = getSupportFragmentManager();
        myViewPagerAdapter = new MyViewPagerAdapter(fm,0,fragments);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        btnUpcoming.setEnabled(false);
                        btnPastTrip.setEnabled(true);
                        break;
                    case 1:
                        btnUpcoming.setEnabled(true);
                        btnPastTrip.setEnabled(false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initcomponent() {
        imgBtnHome = findViewById(R.id.imageBtnHome);
        imgBtnProfile = findViewById(R.id.imageBtnProfile);
        fltBtnAdd = findViewById(R.id.fABtnAddNote);

        imgBtnHome.setEnabled(false);


        imgBtnProfile.setOnClickListener(this);
        fltBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        }
    }


    /**
     *  HomeViewModelFactory
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
}
