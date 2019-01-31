package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //view all workouts button clicked
    public void viewAllWorkoutsButtonClicked(View view){
        System.out.println("view all clicked");
        Intent intent = new Intent(this, viewAllWorkoutsActivity.class);
        startActivity(intent);
    }

    //create workout button clicked
    public void createWorkoutButtonClicked(View view){
        System.out.println("create clicked");
    }

    //view favorite workouts button clicked
    public void viewFavoriteWorkoutsButtonClicked(View view){
        System.out.println("favorite clicked");
    }

}
