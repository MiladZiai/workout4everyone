package se.ju.student.saro1718.workout4everyone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;

//database from main
import static se.ju.student.saro1718.workout4everyone.MainActivity.database;

public class createWorkoutActivity extends AppCompatActivity {

    ImageView imageView;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    ProgressBar saveProgressBar;
    Button button;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        //sets default values for animation
        saveProgressBar = (ProgressBar) findViewById(R.id.saveProgressBar);
        button = (Button) findViewById(R.id.saveWorkoutButton);
        button.setVisibility(View.VISIBLE);
        saveProgressBar.setVisibility(View.GONE);

        //image view on create
        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               openGallery();
            }
        });

    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }

    public void addButtonClicked(View view){

        EditText editText = (EditText) findViewById(R.id.exerciseInput);
        EditText editText1 = (EditText) findViewById(R.id.descInput);
        String exerciseTitleInput = editText.getText().toString();
        String exerciseDescInput = editText1.getText().toString();
        if(exerciseTitleInput.length() == 0 || exerciseDescInput.length() == 0){
            new AlertDialog.Builder(this).setTitle("Add Exercise").setMessage("Input both Exercise and Description!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int whichButton){
                            dialog.dismiss();
                        }
                    }).show();
        }else{
            workoutsData.exercises.add(new workoutsData.Exercise(exerciseTitleInput));
            workoutsData.descriptions.add(new workoutsData.Desc(exerciseDescInput));

            editText.setText("");
            editText1.setText("");
        }
    }

    public void viewCurrentListButtonClicked(View view){
        Intent intent = new Intent(this, viewExerciseListActivity.class);
        startActivity(intent);
    }


    //last step in create workout, saves everything to database
    public void saveButtonClicked(View view){
        animateButton();


        //values for workout
        //String ownerId = database.getUser();
        EditText titleInput = (EditText) findViewById(R.id.titleInput);
        String title = titleInput.getText().toString();

        ArrayList<String> exerciseTitles = new ArrayList<>();
        ArrayList<String> exerciseDescriptions = new ArrayList<>();

        for(int i = 0 ; i < workoutsData.exercises.size();i++){
            exerciseTitles.add(workoutsData.exercises.get(i).toString());
            exerciseDescriptions.add(workoutsData.descriptions.get(i).toString());
        }

        String level = "advanced";

        workoutsData.workoutVariables workoutToBeCreated = new workoutsData.workoutVariables("test",title,exerciseTitles,exerciseDescriptions,level,"");

        imageView.invalidate();
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        database.createWorkout(workoutToBeCreated,bitmap,saveProgressBar,this);

    }
    private void animateButton(){
        final Animation fade = AnimationUtils.loadAnimation(this,R.anim.fade);
        button.startAnimation(fade);
        button.setVisibility(View.GONE);

        ProgressBar saveProgressBar = (ProgressBar) findViewById(R.id.saveProgressBar);
        saveProgressBar.setVisibility(View.VISIBLE);


    }

}
