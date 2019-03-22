package se.ju.student.saro1718.workout4everyone;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import static se.ju.student.saro1718.workout4everyone.MainActivity.database;

public class profileFragment extends Fragment implements View.OnClickListener {
    public profileFragment(){}

    private Button signOutButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        super.onCreate(savedInstanceState);
        initiateButtons(view);
        return view;
    }

    private void initiateButtons(View view){
        signOutButton = view.findViewById(R.id.signed_in_activity_signOutButton);
        signOutButton.setOnClickListener(this);
    }

    private void signOutButtonClicked(){
        database.getFirebaseAuth().signOut();
        NavigationView navView = getActivity().findViewById(R.id.nav_view);
        Menu menu = navView.getMenu();
        MenuItem item = (MenuItem) menu.findItem(R.id.create_workouts);
        item.setEnabled(false);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new signInFragment()).addToBackStack(null).commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signed_in_activity_signOutButton:
                signOutButtonClicked();
                break;

        }
    }
}
