package com.group4.togolist.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.group4.togolist.R;
import com.group4.togolist.viewmodel.AppViewModel;
import com.group4.togolist.viewmodel.HomeViewModel;

public class AppActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnPrivacyLayout, btnInfoLayout;
    //    private ImageButton imgBtnHome, imgBtnProfile ;
//    private FloatingActionButton  fltBtnAdd;
    private AppViewModel appViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        initComponent();
        appViewModel = ViewModelProviders.of(this, new MyViewModelFactory(AppActivity.this)).get(AppViewModel.class);
    }

    private void initComponent() {
//        imgBtnHome = findViewById(R.id.imageBtnHome);
//        imgBtnProfile = findViewById(R.id.imageBtnProfile);
//        fltBtnAdd = findViewById(R.id.fABtnAddNote);
        btnPrivacyLayout = findViewById(R.id.btnPrivacyLayout);
        btnInfoLayout = findViewById(R.id.btnInfoLayout);

//        imgBtnHome.setOnClickListener(this);
//        imgBtnProfile.setOnClickListener(this);
//        fltBtnAdd.setOnClickListener(this);
        btnPrivacyLayout.setOnClickListener(this);
        btnInfoLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
//            case R.id.imageBtnHome:
//                appViewModel.goToHome();
//                break;
//            case R.id.imageBtnProfile:
//              appViewModel.goToProfile();
//                break;
//            case R.id.fABtnAddNote:
//                appViewModel.goToAddForm();
//                break;
            case R.id.btnPrivacyLayout:
                appViewModel.goToPrivacyLayout();
                break;
            case R.id.btnInfoLayout:
                appViewModel.goToInfoLayout();
                break;
        }
    }

    class MyViewModelFactory implements ViewModelProvider.Factory {
        private AppActivity mActivity;

        public MyViewModelFactory(AppActivity activity) {
            mActivity = activity;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new AppViewModel(mActivity);
        }
    }
}
