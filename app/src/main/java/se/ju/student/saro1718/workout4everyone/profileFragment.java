package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.concurrent.Executor;

import static se.ju.student.saro1718.workout4everyone.MainActivity.database;

public class profileFragment extends Fragment implements View.OnClickListener {

    private LoginButton loginButton;
    private CallbackManager mCallbackManager;

    private FirebaseAuth mAuth = database.getFirebaseAuth();

    private Button signInButton;
    private Button registerButton;
    private ProgressBar signInProgressBar;
    @Override
    public void onStart() {
        super.onStart();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //FacebookSdk.sdkInitialize(getApplicationContext());
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            View parentView = inflater.inflate(R.layout.fragment_profile_is_signed_in, container, false);
            initiateLogggedInButtons(parentView);
            return parentView;
        } else {
            View parentView = inflater.inflate(R.layout.fragment_profile_sign_in, container, false);
            initiateNotLoggedButtons(parentView);

            signInProgressBar = parentView.findViewById(R.id.sign_in_activity_signInProgressBar);
            signInProgressBar.setVisibility(View.GONE);

            loginButton = parentView.findViewById(R.id.sign_in_activity_facebookLoginButton);
            loginButton.setOnClickListener(this);
            return parentView;
        }

    }

     /***********************************************************
     *
     *  Facebook login methods!
     *
     ***********************************************************/

    public void faceBookButtonOnClick() {
        System.out.println("fbclicked");
        FacebookSdk.sdkInitialize(this.getContext());

        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");
        
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("sucess");
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                System.out.println("calcen");
                // ...
            }
            @Override
            public void onError(FacebookException error) {
                System.out.println(error.getMessage());
                // ...
            }
        });
    }


    private void handleFacebookAccessToken(AccessToken token) {
        System.out.println("handleFacebookAcessToken initiated");
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener((Executor) this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            System.out.println(task.getException().getMessage());
                            updateUI(null);
                        }
                        // ...
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    //facebook login methods end!

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                BUTTON LISTENERS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    //initiating buttons for logged in users listeners
    private void initiateLogggedInButtons(View view){

    }

    //initiating buttons for NOT logged in users listeners
    private void initiateNotLoggedButtons(View view){
        signInButton = view.findViewById(R.id.sign_in_activity_signInButton);
        signInButton.setOnClickListener(this);
        registerButton = view.findViewById(R.id.sign_in_activity_registerButton);
        registerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.sign_in_activity_signInButton:
                signInButtonClicked(view);
                break;
            case R.id.sign_in_activity_registerButton:
                registerButtonCLicked();
                break;
            case R.id.sign_in_activity_facebookLoginButton:
                faceBookButtonOnClick();
                break;
        }
    }

    public void signInButtonClicked(View view){
        final Animation fade = AnimationUtils.loadAnimation(this.getContext(),R.anim.fade);

        signInButton.setAnimation(fade);
        signInButton.setVisibility(View.GONE);
        signInProgressBar.setVisibility(View.VISIBLE);


        //this one crash!!!
        //String emailInput = ((EditText) view.findViewById(R.id.sign_in_activity_EmailInput)).getText().toString().trim();
        //String passwordInput = ((EditText) view.findViewById(R.id.sign_in_activity_passwordInput)).getText().toString().trim();

        System.out.println(database);
        database.login("ald","ald2",this);
    }
    //connected to signInButtonClicked
    public void verifySignIn(boolean success){
        signInProgressBar.setVisibility(View.GONE);
        if(success){
            Toast.makeText(this.getActivity(),"sucess signing in!",Toast.LENGTH_SHORT).show();
            updateUI(mAuth.getCurrentUser());
        }else{
            signInButton.setVisibility(View.VISIBLE);
            Toast.makeText(this.getActivity(),"failed to sign in, please check that email and password is right",Toast.LENGTH_LONG).show();
            updateUI(null);
        }
    }

    public void registerButtonCLicked(){
        Intent intent = new Intent(this.getActivity(),registerUserActivity.class);
        startActivity(intent);
    }

    private void updateUI(FirebaseUser user){
        if(user != null){
            System.out.println("logged in");
            //logged in
        }else{
            System.out.println("not logged in");
            //not logged in
        }
    }
}