package com.group4.togolist.view.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.group4.togolist.R;
import com.group4.togolist.view.activities.FirstActivity;

import androidx.viewpager.widget.PagerAdapter;

/**
 * Class do :
 * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
 */
public class MyViewPagerHowToUseAdapeter extends PagerAdapter {
    private FirstActivity activity;

    public MyViewPagerHowToUseAdapeter(FirstActivity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) activity.getApplicationContext().getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_viewpager, container, false);
        TextView textView = (TextView) itemView.findViewById(R.id.number);
        ImageView imageView7 = itemView.findViewById(R.id.imageView7);

//        textView.setText(String.format("Page: %d", position + 1));
//        imageView7.setImageResource(R.drawable.logo +position +1);
        switch (position){
            case 0:
                textView.setText(R.string.howtousepage1);
                imageView7.setImageResource(R.drawable.howtouse1);
                break;
            case 1:
                textView.setText(R.string.howtousepage2);
                imageView7.setImageResource(R.drawable.howtouse2);
                break;
            case 2:
                textView.setText(R.string.howtousepage3);
                imageView7.setImageResource(R.drawable.howtouse3);
                break;
            case 3:
                textView.setText(R.string.howtousepage4);
                imageView7.setImageResource(R.drawable.howtouse4);
                break;
            case 4:
                textView.setText(R.string.howtousepage5);
                imageView7.setImageResource(R.drawable.howtouse5);
                break;


        }

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }
}
