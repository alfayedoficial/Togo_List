package com.group4.togolist.repository;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.group4.togolist.model.User;
import com.group4.togolist.viewmodel.ForgetPassViewModel;
import com.group4.togolist.viewmodel.LoginViewModel;
import com.group4.togolist.viewmodel.ProfileViewModel;
import com.group4.togolist.viewmodel.RegisterViewModel;

public class FirebaseHandler {

    /**
     * Class do : This class is Designed to handle Firebase Login And Register
     * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
     */

    private FirebaseAuth mAuth;
    FirebaseUser user;
    User appUser = User.getUserInstance();
    private static final String TAG = "FirebaseHandler";
    public static final int ACCESS_DENIED = 2001;
    public static final int ACCESS_GRANTED = 2002;
    public static final int NEW_ACCOUNT_CREATED = 3001;
    public static final int NEW_ACCOUNT_FAILED = 3002;
    public static final int RESET_EMAIL_SENT = 4001;
    public static final int RESET_EMAIL_FAILED = 4002;
    public static final int USER_PASSWORD_UPDATED = 5001;
    public static final int USER_PASSWORD_UPDATE_FAILED = 5002;


    private Activity activity;

    private LoginViewModel loginViewModel;
    private RegisterViewModel registerViewModel;
    private ForgetPassViewModel forgetPassViewModel;
    private ProfileViewModel profileViewModel;

    /**
     * Firebase Handler Constructors
     */

    public FirebaseHandler(Activity activity,ForgetPassViewModel forgetPassViewModel){
        createFirebaseAuth();
        this.activity = activity;
        this.forgetPassViewModel = forgetPassViewModel;
    }

    public FirebaseHandler(Activity activity,RegisterViewModel viewModel){
        createFirebaseAuth();
        this.activity = activity;
        registerViewModel = viewModel;
    }

    public FirebaseHandler(Activity activity,LoginViewModel viewModel){
        createFirebaseAuth();
        this.activity = activity;
        loginViewModel = viewModel;
    }

    public FirebaseHandler(Activity activity, ProfileViewModel viewModel){
        createFirebaseAuth();
        this.activity = activity;
        profileViewModel = viewModel;
    }


    /**
     * this method request sign in using email and password
     */
    public void signIn(String email, String password){
        user = FirebaseAuth.getInstance().getCurrentUser();
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


    /**
     * this method request sign up using email and password
     */
    public void signUp(String email, String password){
        user = FirebaseAuth.getInstance().getCurrentUser();
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


    /**
     *  method reset password and send email to the user
     */
    public void resetPassword(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            forgetPassViewModel.onResult(RESET_EMAIL_SENT);
                        }
                        else{
                            forgetPassViewModel.onResult(RESET_EMAIL_FAILED);
                        }
                    }
                });
    }

    public void changeUserPassword(final String password){
        user = FirebaseAuth.getInstance().getCurrentUser();
        // Get auth credentials from the user for re-authentication. The example below shows
        // email and password credentials but there are multiple possible providers,
       // such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(appUser.getEmail(), appUser.getPassword());

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        appUser.setPassword(password);
                                        Log.d(TAG, "Password updated");
                                    } else {
                                        Log.d(TAG, "Error password not updated");
                                    }
                                }
                            });
                        } else {
                            Log.d(TAG, "Error auth failed");
                        }
                    }
                });
    }





    public void createFirebaseAuth(){
        if(mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
    }
}
