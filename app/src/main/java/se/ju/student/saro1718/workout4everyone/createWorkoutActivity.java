package se.ju.student.saro1718.workout4everyone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


import static se.ju.student.saro1718.workout4everyone.MainActivity.localDatabase;

public class createWorkoutActivity extends AppCompatActivity {

    //capture,gallery image variables
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CAPTURE = 1;


    private ImageView imageView;
    private Uri imageUri;
    private ProgressBar saveProgressBar;
    private Button saveWorkoutButton;
    private int difficultyCounter = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        //sets default values for animation
        saveProgressBar = (ProgressBar) findViewById(R.id.saveProgressBar);
        saveWorkoutButton = (Button) findViewById(R.id.saveWorkoutButton);
        saveProgressBar.setVisibility(View.GONE);

        imageView = (ImageView) findViewById(R.id.imageView);

        //image view on create
        imageViewClickListener();
    }

    /*
       runtime configuration changes methods
    */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(imageUri != null) {
            //bitmap to byte array
            byte[] data = imageViewToByte(imageView);

            //imageUri to string
            String uri = imageUri.toString();

            outState.putByteArray("image", data);
            outState.putString("imageUri",uri);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        byte[] data = savedInstanceState.getByteArray("image");
        String imageUriSaved = savedInstanceState.getString("imageUri");
        if(imageUriSaved != null)  {
            Bitmap savedImage = BitmapFactory.decodeByteArray(data, 0, data.length);
            imageView.setImageBitmap(savedImage);
            imageUri = Uri.parse(imageUriSaved);
        }

    }


    //runtime configuration changes END

    /*
        camera, gallery methods
    */
    public void imageViewClickListener(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPictureDialog();
            }
        });
    }

    private void showPictureDialog(){
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "select photo from gallery",
                "capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case 0:
                        openGallery();
                        break;
                    case 1:
                        launchCamera();
                        break;
                }
            }
        });
        pictureDialog.show();
    }

    public void launchCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAPTURE);
    }

    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK ){
            imageUri = null;
            imageUri = data.getData();
            //null captured, not null gallery
            if(imageUri != null) {
                imageView.setImageURI(imageUri);
            }else{
                imageUri = Uri.parse("capturedImageUri");
                Bundle extras = data.getExtras();
                Bitmap photo = (Bitmap) extras.get("data");
                imageView.setImageBitmap(photo);
            }
        }
    }

    //camera,gallery methods END

    /*
        exercises (title,description) methods, listview buttons
    */
    public void addButtonClicked(View view){

        EditText exerciseTitleEditText = (EditText) findViewById(R.id.exerciseInput);
        EditText exerciseDescEditText = (EditText) findViewById(R.id.descInput);
        String exerciseTitleInput = exerciseTitleEditText.getText().toString();
        String exerciseDescInput = exerciseDescEditText.getText().toString();
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
            exerciseTitleEditText.setText("");
            exerciseDescEditText.setText("");
        }
    }

    public void viewCurrentListButtonClicked(View view){
        Intent intent = new Intent(this, viewExerciseListActivity.class);
        startActivity(intent);
    }

    //exercises (title,description) methods, listview buttons END

    /*
        swap difficulty method, swaps difficulty after pressed button
    */
    public void swapDifficultyButtonClicked(View view){
        final String[] difficultyString = {getString(R.string.easy),getString(R.string.medium),getString(R.string.hard)};
        final int[] difficultyColor = {ContextCompat.getColor(this,R.color.green),ContextCompat.getColor(this,R.color.blue),ContextCompat.getColor(this,R.color.red)};
        Button difficultyButton = (Button) view.findViewById(R.id.create_workout_activity_difficultyButton);

        difficultyCounter++;

        difficultyCounter = difficultyCounter % 3;

        difficultyButton.setText(difficultyString[difficultyCounter]);
        difficultyButton.setBackgroundColor(difficultyColor[difficultyCounter]);


    }
    // swap difficulty method END

    //last step in create workout, saves everything to database
    public void saveButtonClicked(View view){

        EditText titleInput = (EditText) findViewById(R.id.titleInput);

        String title = titleInput.getText().toString();
        if(title.length() == 0 || imageUri == null){
            Toast.makeText(this,"please make sure that you have a title and image!",Toast.LENGTH_LONG).show();
            return;
        }

        byte[] data = imageViewToByte(imageView);

        animateButton();

        ArrayList<String> exerciseTitles = new ArrayList<>();
        ArrayList<String> exerciseDescriptions = new ArrayList<>();

        for(int i = 0 ; i < workoutsData.exercises.size();i++){
            exerciseTitles.add(workoutsData.exercises.get(i).toString());
            exerciseDescriptions.add(workoutsData.descriptions.get(i).toString());
        }

        Button difficultyButton = (Button) findViewById(R.id.create_workout_activity_difficultyButton);
        String level = difficultyButton.getText().toString();

        imageView.invalidate();

        workoutsData.workoutVariables workoutToBeCreated = new workoutsData.workoutVariables("test",title,exerciseTitles,exerciseDescriptions,level, "");
        
        //send all information to database
        //database.createWorkout(workoutToBeCreated,data,this);

        //database.createWorkout(workoutToBeCreated,bitmap,this);
        localDatabase.insertData(workoutToBeCreated.getWorkoutTitle(),data,exerciseTitles,exerciseDescriptions,"hej5");

    }

    public void verifyInsert(boolean success,Exception e){
        if(success){
            //toast ok
            Intent intent = new Intent(createWorkoutActivity.this,viewWorkoutsListActivity.class);
            finish();
            //intent putextra
            startActivity(intent);
        }else{
            //toast not ok e.getMessage();
            saveProgressBar.setVisibility(View.GONE);
            saveWorkoutButton.setVisibility(View.VISIBLE);
        }
    }


    private void animateButton(){
        final Animation fade = AnimationUtils.loadAnimation(this,R.anim.fade);
        saveWorkoutButton.startAnimation(fade);
        saveWorkoutButton.setVisibility(View.GONE);

        ProgressBar saveProgressBar = (ProgressBar) findViewById(R.id.saveProgressBar);
        saveProgressBar.setVisibility(View.VISIBLE);


    }

    private byte[] imageViewToByte(ImageView image){
        Bitmap savedImage = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        savedImage.compress(Bitmap.CompressFormat.JPEG, 10, baos);
        byte[] byteArr = baos.toByteArray();
        return byteArr;
    }





}
