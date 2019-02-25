package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class homeFragment extends Fragment implements View.OnClickListener {

    private Button viewAllWorkoutsButton;
    private Button createWorkoutButton;
    private Button viewFavoriteWorkoutButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_home,container,false);
        initiateHomeButtons(parentView);
        return parentView;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                BUTTON LISTENERS METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////

    //initiate butotn click listeners
    private void initiateHomeButtons(View view){
        viewAllWorkoutsButton = view.findViewById(R.id.home_activity_viewAllWorkoutsButton);
        viewAllWorkoutsButton.setOnClickListener(this);
        createWorkoutButton = view.findViewById(R.id.home_activity_createWorkoutButton);
        createWorkoutButton.setOnClickListener(this);
        viewFavoriteWorkoutButton = view.findViewById(R.id.home_activity_favoriteWorkoutsButton);
        viewFavoriteWorkoutButton.setOnClickListener(this);
    }


    //switches between what button that was clicked and sends to function specified for which one
    public void onClick(View view){
        switch(view.getId()){
            case R.id.home_activity_viewAllWorkoutsButton:
                viewAllWorkoutsButtonClicked();
                break;
            case R.id.home_activity_createWorkoutButton:
                createWorkoutButtonClicked();
                break;
            case R.id.home_activity_favoriteWorkoutsButton:
                viewFavoriteWorkoutsButtonClicked();
                break;
        }
    }

    public void viewAllWorkoutsButtonClicked(){
        Intent intent = new Intent(this.getActivity(),viewWorkoutsListActivity.class);
        startActivity(intent);
    }
    public void createWorkoutButtonClicked(){
        Intent intent = new Intent(this.getActivity(), createWorkoutActivity.class);
        startActivity(intent);
    }
    public void viewFavoriteWorkoutsButtonClicked(){
        Intent intent = new Intent(this.getActivity(),registerUserActivity.class);
        startActivity(intent);
    }

}
