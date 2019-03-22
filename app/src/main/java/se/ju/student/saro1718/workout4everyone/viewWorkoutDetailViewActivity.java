package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

import static se.ju.student.saro1718.workout4everyone.MainActivity.localDatabase;

public class viewWorkoutDetailViewActivity extends AppCompatActivity implements View.OnClickListener {

    private workoutsData.workoutVariables workout;
    private Button saveToLocalDbButton;
    private boolean saved = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workout_detail_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        loadWorkoutDetailView();

    }

    private void loadWorkoutDetailView(){

        int position = 0;

        //initiate save to local db button
        saveToLocalDbButton = (Button) findViewById(R.id.view_workout_detail_saveToLocalDbButton);
        saveToLocalDbButton.setOnClickListener(this);

        //gets position and saves it to local variable workout
        position = getIntent().getIntExtra("position",position);
        workout = workoutsData.workoutList.get(position);

        boolean globalOrNot = false;
        globalOrNot = getIntent().getBooleanExtra("global",globalOrNot);
        if(globalOrNot) {
            verifySavedWorkout();
        }else{
            saveToLocalDbButton.setVisibility(View.GONE);
        }
        //inserts image to placeholder
        ImageView workoutImage = (ImageView) findViewById(R.id.activity_view_workout_detail_imageView);
        workoutImage.setImageBitmap(workout.getWorkoutBitmap());

        //inserts title to placeHolder
        TextView workoutTitle = findViewById(R.id.activity_view_workout_detail_title);
        workoutTitle.setText(workout.getWorkoutTitle());

        /*
        * Had to make an custom listview since collapseBarLayout doesn't support scrollable views,
        * This one appends custom made views with click listeners
        */
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.placeForExercises);
        workoutsData.exercises.clear();
        for(int i = 0 ; i < workout.getWorkoutExerciseTitle().size(); i++){
            String title = workout.getWorkoutExerciseTitle().get(i);
            String desc = workout.getWorkoutExerciseDescription().get(i);
            workoutsData.exercises.add(new workoutsData.listViewHelper(title,desc));
            View child =  getLayoutInflater().inflate(R.layout.workout_title_child,null);
            ((TextView) child.findViewById(R.id.workout_title_child_childTitle)).setText(title);

            final int positionOfExercise = i;

            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(viewWorkoutDetailViewActivity.this, exercisePopUp.class);
                    intent.putExtra("position",positionOfExercise);
                    startActivity(intent);
                }
            });
            linearLayout.addView(child);
        }

    }


    @Override
    public void onClick(View v) {
        if(saved){
            localDatabase.deleteRow(workout.getPostId());
        }else {
            String title = workout.getWorkoutTitle();
            byte[] image = BitmapToByte(workout.getWorkoutBitmap());
            localDatabase.insertData(title, image, workout.getWorkoutExerciseTitle(), workout.getWorkoutExerciseDescription(), workout.getPostId());
        }
        verifySavedWorkout();
    }

    //checks if workout is stored in favorites or not
    private void verifySavedWorkout(){
        if(localDatabase.readSpecificRow(workout.getPostId()) == null){
            saved = false;
            saveToLocalDbButton.setBackground(getResources().getDrawable(R.drawable.ic_star_border_black_24dp));
        }else{
            saved = true;
            saveToLocalDbButton.setBackground(getResources().getDrawable(R.drawable.ic_star_black_24dp));
        }
    }

    private byte[] BitmapToByte(Bitmap image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        return baos.toByteArray();
    }
}

