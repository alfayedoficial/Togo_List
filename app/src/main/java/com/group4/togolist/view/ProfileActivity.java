package com.group4.togolist.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.group4.togolist.R;
import com.group4.togolist.viewmodel.AddFormViewModel;
import com.group4.togolist.viewmodel.LoginViewModel;
import com.group4.togolist.viewmodel.ProfileViewModel;

public class ProfileActivity extends AppCompatActivity  implements View.OnClickListener {

    private EditText eTxtUserName , eTxtEmail  , eTxtPassword , eTxtConfirmPassword;
    private Button btnEdit , btnLogout , btnUpdate;
    private TextView txtHopeComeBack , textViewPofilePassword , textViewPofileConfirmPassword;
    private ProfileViewModel profileViewModel ;

    private ImageButton imgBtnHome , imgBtnProfile;
    private com.google.android.material.floatingactionbutton.FloatingActionButton fltBtnAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initComponent();
        profileViewModel = ViewModelProviders.of(this, new MyViewModelFactory(this)).get(ProfileViewModel.class);
    }

    private void initComponent() {

        eTxtUserName = findViewById(R.id.editTextPofileUsername);
        eTxtEmail = findViewById(R.id.editTextPofileEmail);
        eTxtPassword = findViewById(R.id.editTextPofilePassword);
        eTxtConfirmPassword = findViewById(R.id.editTextPofileConfirmPassword);
        txtHopeComeBack = findViewById(R.id.textView10);
        textViewPofilePassword  = findViewById(R.id.textViewPofilePassword);
        textViewPofileConfirmPassword = findViewById(R.id.textViewPofileConfirmPassword);

        btnEdit = findViewById(R.id.btnEdit);
        btnLogout = findViewById(R.id.btnLogout);
        btnUpdate = findViewById(R.id.btnProfileUpdate);

        imgBtnHome = findViewById(R.id.imageBtnHome);
        imgBtnProfile = findViewById(R.id.imageBtnProfile);
        fltBtnAdd = findViewById(R.id.fABtnAddNote);

        imgBtnProfile.setEnabled(false);

        btnEdit.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnLogout.setOnClickListener(this);

        imgBtnHome.setOnClickListener(this);
        fltBtnAdd.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnEdit:
                eTxtPassword.setVisibility(View.VISIBLE);
                eTxtConfirmPassword.setVisibility(View.VISIBLE);
                btnUpdate.setVisibility(View.VISIBLE);
                textViewPofilePassword.setVisibility(View.VISIBLE);
                textViewPofileConfirmPassword.setVisibility(View.VISIBLE);
//                can edit
                eTxtPassword.setClickable(true);
                eTxtPassword.setFocusable(true);
                eTxtPassword.setFocusableInTouchMode(true);
                eTxtConfirmPassword.setClickable(true);
                eTxtConfirmPassword.setFocusable(true);
                eTxtConfirmPassword.setFocusableInTouchMode(true);
                textViewPofilePassword.setClickable(true);
                textViewPofilePassword.setFocusable(true);
                textViewPofilePassword.setFocusableInTouchMode(true);
                textViewPofileConfirmPassword.setClickable(true);
                textViewPofileConfirmPassword.setFocusable(true);
                textViewPofileConfirmPassword.setFocusableInTouchMode(true);

                btnEdit.setVisibility(View.GONE);
                btnLogout.setVisibility(View.GONE);
                txtHopeComeBack.setVisibility(View.GONE);
                break;
            case R.id.btnProfileUpdate:

                profileViewModel.updateUser(eTxtPassword.getText().toString() , eTxtConfirmPassword.getText().toString());
                afterUpdate();
                break;

            case R.id.btnLogout:
                profileViewModel.logOut();

                break;
            case R.id.imageBtnHome:
                profileViewModel.goToHome();
                break;

            case R.id.fABtnAddNote:
                profileViewModel.goToAddForm();
                break;
        }
    }
   private void afterUpdate(){
       eTxtPassword.setVisibility(View.GONE);
       eTxtConfirmPassword.setVisibility(View.GONE);
       btnUpdate.setVisibility(View.GONE);
       textViewPofilePassword.setVisibility(View.GONE);
       textViewPofileConfirmPassword.setVisibility(View.GONE);

       btnEdit.setVisibility(View.VISIBLE);
       btnLogout.setVisibility(View.VISIBLE);
       txtHopeComeBack.setVisibility(View.VISIBLE);
    }

    public void setUserName(String userName){
        eTxtUserName.setText(userName);
    }

    public void setEmail(String email){
        eTxtEmail.setText(email);
    }
    public void setPassword(String password){
        eTxtPassword.setText(password);
    }

    public void setConfirmedPassword(String confirmedPassword){
        eTxtConfirmPassword.setText(confirmedPassword);
    }

    /**
     *  ProfileViewModelFactory
     */

    class MyViewModelFactory implements ViewModelProvider.Factory {
        private ProfileActivity mActivity;


        public MyViewModelFactory(ProfileActivity activity) {
            mActivity = activity;
        }


        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            return (T) new ProfileViewModel(mActivity);
        }
    }
}
