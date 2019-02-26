package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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
import com.google.android.gms.tasks.OnFailureListener;
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

    private CallbackManager mCallbackManager;

    private FirebaseAuth mAuth = database.getFirebaseAuth();

    //not logged in buttons
    private LoginButton loginButton;
    private Button signInButton;
    private Button registerButton;

    //logged in buttons
    private Button signOutButton;

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
     @Override
     public void onActivityResult(int requestCode, int resultCode, Intent data) {
         super.onActivityResult(requestCode, resultCode, data);

         // Pass the activity result back to the Facebook SDK
         mCallbackManager.onActivityResult(requestCode, resultCode, data);
     }

     public void faceBookButtonOnClicked() {
         FacebookSdk.sdkInitialize(this.getContext());

         mCallbackManager = CallbackManager.Factory.create();
         loginButton.setReadPermissions("email", "public_profile");
         loginButton.setFragment(this);
         loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
             @Override
             public void onSuccess(LoginResult loginResult) {
                 handleFacebookAccessToken(loginResult.getAccessToken());
             }
             @Override
             public void onCancel() {
                 updateUI(null);
             }
             @Override
             public void onError(FacebookException error) {
                 updateUI(null);
             }
        });
    }



    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            verifySignIn(true);

                        } else {
                            // If sign in fails, display a message to the user.
                            verifySignIn(false);
                        }
                        // ...
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                updateUI(null);
            }
        });
    }


    //facebook login methods end!

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                BUTTON LISTENERS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    //initiating buttons for logged in users listeners
    private void initiateLogggedInButtons(View view){
        signOutButton = view.findViewById(R.id.signed_in_activity_signOutButton);
        signOutButton.setOnClickListener(this);
    }

    //initiating buttons for NOT logged in users listeners
    private void initiateNotLoggedButtons(View view){
        signInButton = view.findViewById(R.id.sign_in_activity_signInButton);
        signInButton.setOnClickListener(this);
        registerButton = view.findViewById(R.id.sign_in_activity_registerButton);
        registerButton.setOnClickListener(this);
    }

    /*
        switch used for each button since fragment are usually an container
        for one framelayout (in this case mainActivity) and will not recognize onClickmethods unless
        they are in MainActivity.
    */
    @Override
    public void onClick(View view){
        switch(view.getId()){
            case R.id.signed_in_activity_signOutButton:
                signOutButtonClicked();
                break;
            case R.id.sign_in_activity_signInButton:
                signInButtonClicked(view);
                break;
            case R.id.sign_in_activity_registerButton:
                registerButtonClicked();
                break;
            case R.id.sign_in_activity_facebookLoginButton:
                faceBookButtonOnClicked();
                break;
        }
    }
    //signOutButtonClicked signs user out when clicked and calls updateUI
    public void signOutButtonClicked(){
        FirebaseAuth.getInstance().signOut();
        updateUI(null);
    }
    //signInButtonClicked checks with firebase if inputed values are correctly for one user then updates ui
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

    //registerButtonClicked reacts when register button clicked, opens new activity window for register
    public void registerButtonClicked(){
        Intent intent = new Intent(this.getActivity(),registerUserActivity.class);
        startActivity(intent);
    }


    /*
        Updates UI depending if youre logged in or not, takes in one firebaseUser named user and checks if this is null or not.
        if it is will it reload fragment that then adapt fragment to user if logged in or not.
    */
    private void updateUI(FirebaseUser user){
        if(user != null){
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }else{
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(this).attach(this).commit();
        }
    }

    //verifies to user if (true) signed in or (false) not signed in
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

}