package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    //view all workouts button clicked
    public void viewAllWorkoutsButtonClicked(View view){
        Intent intent = new Intent(this, viewAllWorkoutsActivity.class);
        startActivity(intent);
    }

    //create workout button clicked
    public void createWorkoutButtonClicked(View view){
        Intent intent = new Intent(this, createWorkoutActivity.class);
        startActivity(intent);


    }

    //view favorite workouts button clicked
    public void viewFavoriteWorkoutsButtonClicked(View view){
    }

}
