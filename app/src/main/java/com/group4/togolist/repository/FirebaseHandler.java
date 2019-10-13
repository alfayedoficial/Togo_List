package com.group4.togolist.repository;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.group4.togolist.viewmodel.ForgetPassViewModel;
import com.group4.togolist.viewmodel.LoginViewModel;
import com.group4.togolist.viewmodel.RegisterViewModel;

public class FirebaseHandler {

    /**
     * Class do : This class is Designed to handle Firebase Login And Register
     * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
     */

    private FirebaseAuth mAuth;
    private static final String TAG = "FirebaseHandler";
    public static final int ACCESS_DENIED = 2001;
    public static final int ACCESS_GRANTED = 2002;
    public static final int NEW_ACCOUNT_CREATED = 3001;
    public static final int NEW_ACCOUNT_FAILED = 3002;


    private Activity activity;

    private LoginViewModel loginViewModel;
    private RegisterViewModel registerViewModel;

    public FirebaseHandler(Activity activity){
        mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
    }

    public FirebaseHandler(Activity activity,RegisterViewModel viewModel){
        mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
        registerViewModel = viewModel;
    }

    public FirebaseHandler(Activity activity,LoginViewModel viewModel){
        mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
        loginViewModel = viewModel;
    }

    public void signIn(String email, String password){

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    loginViewModel.loginToHomeScreen(ACCESS_DENIED);
                }else{
                    loginViewModel.loginToHomeScreen(ACCESS_GRANTED);
                }
            }
        });
    }

    public void signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(activity, "Please Try again with a different username", Toast.LENGTH_SHORT).show();
                            registerViewModel.signUpToHomeScreen(NEW_ACCOUNT_FAILED);
                        }
                        else {
                            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                            registerViewModel.signUpToHomeScreen(NEW_ACCOUNT_CREATED);
                        }
                    }
                });
    }

    public void resetPassword(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(activity, "Email Sent", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
