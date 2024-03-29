package com.group4.togolist.viewmodel;

import android.content.Intent;
import android.util.Log;


import androidx.lifecycle.ViewModel;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.group4.togolist.R;
import com.group4.togolist.repository.DatabaseHandler;
import com.group4.togolist.model.User;
import com.group4.togolist.view.activities.ForgetPasswordActivity;
import com.group4.togolist.view.activities.HomeActivity;
import com.group4.togolist.repository.FirebaseHandler;
import com.group4.togolist.view.activities.SignInActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

/**
 * Class do : Handle Sign In Activity
 * Created by Group 4 ITI (Eng/Bassem - Eng Fatma - Eng Ali)
 */


public class LoginViewModel extends ViewModel {



    private SignInActivity loginActivity;
    private FirebaseHandler firebaseHandler;
    private DatabaseHandler databaseHandler;
    private User user;
    /**
     * Login View Model Constructor Method it takes the activity as input parameter
     */
    public LoginViewModel(SignInActivity loginActivity){
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
                loginActivity.disableLayout(false);

            }
            else {
               // Toast.makeText(loginActivity, R.string.messigninpleaseenter, Toast.LENGTH_SHORT).show();
                FancyToast.makeText(loginActivity ,  loginActivity.getString(R.string.messigninpleaseenter) , FancyToast.DEFAULT , FancyToast.ERROR ,false).show();
                loginActivity.disableLayout(true);

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
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            User user = User.getUserInstance(firebaseUser.getDisplayName() ,firebaseUser.getEmail() );
            //User user = User.getUserInstance();
          //  user.setName(firebaseUser.getDisplayName());
           // user.setEmail(firebaseUser.getEmail());

//            String  uID = firebaseUser.getUid();
//
//            if(uID!= null) {
//
//                databaseHandler.loadFromFireBase(uID);
//            }else
//                Log.i("user","user ID is Null");

            loginActivity.startActivity(loginIntent);
            Log.i("user",user.getEmail());

        }else{
            //Toast.makeText(loginActivity, R.string.messigninincorrect, Toast.LENGTH_SHORT).show();
            FancyToast.makeText(loginActivity , loginActivity.getString(R.string.messigninincorrect), FancyToast.LENGTH_LONG,FancyToast.ERROR,false).show();
            user = null;
            loginActivity.disableLayout(true);
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
