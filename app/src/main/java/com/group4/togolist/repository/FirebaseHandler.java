package com.group4.togolist.repository;

import android.app.Activity;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FirebaseHandler {

    private FirebaseAuth mAuth;
    private static final String TAG = "FirebaseHandler";
    public static final int ACCESS_DENIED = 2001;
    public static final int ACCESS_GRANTED = 2002;
    public static final int NEW_ACCOUNT_CREATED = 3001;
    public static final int NEW_ACCOUNT_FAILED = 3002;

    int signInResult;
    int registerResult;
    Activity activity;


    public FirebaseHandler(Activity activity){
        mAuth = FirebaseAuth.getInstance();
        this.activity = activity;
    }

    public int signIn(String email, String password){

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    signInResult = ACCESS_DENIED;
                }else{
                    signInResult = ACCESS_GRANTED;
                }
            }
        });
        return signInResult;
    }

    public int signUp(String email, String password){
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(activity, "Please Try again with a different username", Toast.LENGTH_SHORT).show();
                            registerResult = NEW_ACCOUNT_FAILED;
                        }
                        else {
                            Toast.makeText(activity, "Success", Toast.LENGTH_SHORT).show();
                            registerResult = NEW_ACCOUNT_CREATED;
                        }
                    }
                });
        return registerResult;
    }
}
