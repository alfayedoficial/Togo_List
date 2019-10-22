package com.group4.togolist.viewmodel;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.group4.togolist.R;
import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.model.User;
import com.group4.togolist.view.ForgetPasswordActivity;
import com.group4.togolist.view.HomeActivity;
import com.group4.togolist.repository.FirebaseHandler;
import com.group4.togolist.view.FirstActivity;

import static io.fabric.sdk.android.Fabric.TAG;

/**
 * Class do : Handle Sign In Activity
 * Created by Group 4 ITI (Eng/Bassen - Eng Fatma - Eng Ali)
 */


public class LoginViewModel extends ViewModel {



    private Activity loginActivity;
    private FirebaseHandler firebaseHandler;
    private DatabaseHandler databaseHandler;
    private User user;
    /**
     * Login View Model Constructor Method it takes the activity as input parameter
     */
    public LoginViewModel(Activity loginActivity){
        this.loginActivity = loginActivity;
        firebaseHandler = new FirebaseHandler(loginActivity,this);
        databaseHandler = new DatabaseHandler(loginActivity);
    }

    /**
     * this method called when Sign in Button in the Log In activity, it check firebase for your email address
     */
    public void signIn(String username, String password){
        if(username != null && !username.isEmpty() && password != null && !password.isEmpty()){
            firebaseHandler.signIn(username,password);
        }
        else {
            Toast.makeText(loginActivity, "Please Enter your Username and Password", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     *  this method call when sign in with google button in the log in activity ,
     */
    public void signInWithGoogle(){
       firebaseHandler.signinWithGoogle();
    }

    /**
     *  Go To Home Screen
     */
    public void loginToHomeScreen(int loginResult){
        if(loginResult == FirebaseHandler.ACCESS_GRANTED){
            Intent loginIntent = new Intent(loginActivity, HomeActivity.class);
            loginActivity.startActivity(loginIntent);
            Log.i("user",user.getEmail());
        }else{
            Toast.makeText(loginActivity, "Your Email or Password is incorrect", Toast.LENGTH_SHORT).show();
            user = null;
        }
    }

    public void firebaseSignInWithGoogle( GoogleSignInAccount account){
        firebaseHandler.firebaseAuthWithGoogle(account);
    }


    /**
     * go to Forget Password Activity
     */
    public void forgetPassword(){
        Intent intent = new Intent(loginActivity, ForgetPasswordActivity.class);
        loginActivity.startActivity(intent);
    }

}
