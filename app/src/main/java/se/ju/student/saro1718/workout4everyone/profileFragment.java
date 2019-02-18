package se.ju.student.saro1718.workout4everyone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.microedition.khronos.egl.EGLDisplay;

import static se.ju.student.saro1718.workout4everyone.MainActivity.database;

public class profileFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FirebaseUser user = database.getFirebaseAuth().getCurrentUser();
        if(user != null) {
            return inflater.inflate(R.layout.fragment_profile_logged_in, container, false);
        }else{
            return inflater.inflate(R.layout.fragment_profile_sign_in,container,false);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}
