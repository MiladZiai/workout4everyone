package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

//firebase
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static se.ju.student.saro1718.workout4everyone.MainActivity.database;

public class profileFragment extends Fragment {

    FacebookLoginSupporter facebookLoginSupporter;
    LoginButton loginButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        FirebaseUser user = database.getFirebaseAuth().getCurrentUser();
        
        if(user != null) {
            return inflater.inflate(R.layout.fragment_profile_logged_in, container, false);
        }else{
            System.out.println("1");
            loginButton = (LoginButton) loginButton.findViewById(R.id.facebook_login_button);
            System.out.println("1");
            facebookLoginSupporter = new FacebookLoginSupporter(loginButton);
            return inflater.inflate(R.layout.fragment_profile_sign_in,container,false);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookLoginSupporter.callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void signOut(){
        AuthUI.getInstance()
                .signOut(this.getContext())
            .addOnCompleteListener(new OnCompleteListener<Void>() {
            public void onComplete(@NonNull Task<Void> task) {
                // ...
            }
        });
    }

}
