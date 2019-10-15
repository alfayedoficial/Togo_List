package com.group4.togolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.group4.togolist.R;
import com.group4.togolist.fragments.UpcomingFragment;
import com.group4.togolist.fragments.HistoryFragment;
import com.group4.togolist.fragments.MyViewPagerAdapter;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * Class do :
     * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
     */

    Button btn1,btn2;
    ViewPager viewPager;
    MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btn1 = findViewById(R.id.btnUpcoming);
        btn2 = findViewById(R.id.btnPastTrip);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);

        viewPager = findViewById(R.id.viewPager);
        Fragment[] fragments = new Fragment[2];
        fragments[0] = new UpcomingFragment();
        fragments[1] = new HistoryFragment();
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
                        btn1.setEnabled(false);
                        btn2.setEnabled(true);
                        break;
                    case 1:
                        btn1.setEnabled(true);
                        btn2.setEnabled(false);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnUpcoming:
                viewPager.setCurrentItem(0);
                btn1.setEnabled(false);
                btn2.setEnabled(true);
                break;
            case R.id.btnPastTrip:
                viewPager.setCurrentItem(1);
                btn1.setEnabled(true);
                btn2.setEnabled(false);
                break;
        }
    }
}
