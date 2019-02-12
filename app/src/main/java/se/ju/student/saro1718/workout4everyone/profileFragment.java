package se.ju.student.saro1718.workout4everyone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class profileFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }
    fireBaseApi firebaseapi = new fireBaseApi();
    @Override
    public void onStart() {
        super.onStart();
        //null or not
        FirebaseAuth currentUser = firebaseapi.getFirebaseAuth();

        
    }

}
