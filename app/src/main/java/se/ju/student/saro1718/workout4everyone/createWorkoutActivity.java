package se.ju.student.saro1718.workout4everyone;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class createWorkoutActivity extends AppCompatActivity {

    ImageView imageView;
    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_CAPTURE = 1;
    Uri imageUri;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        //image view on create
        imageViewClickListener();
    }

    public void imageViewClickListener(){
        imageView = (ImageView) findViewById(R.id.imageView);
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

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
        if(resultCode == RESULT_OK && requestCode == REQUEST_CAPTURE){
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            imageView.setImageBitmap(photo);
        }
    }

    public void addButtonClicked(View view){

        EditText editText = (EditText) findViewById(R.id.exerciseInput);
        EditText editText1 = (EditText) findViewById(R.id.descInput);
        String exerciseInput = editText.getText().toString();
        String descInput = editText1.getText().toString();
        if(exerciseInput.length() == 0 || descInput.length() == 0){
            new AlertDialog.Builder(this).setTitle("Add Exercise").setMessage("Input both Exercise and Description!")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int whichButton){
                            dialog.dismiss();
                        }
                    }).show();
        }else{
            exerciseData.exercises.add(new exerciseData.Exercise(exerciseInput));
            exerciseData.descriptions.add(new exerciseData.Desc(descInput));
            editText.setText("");
            editText1.setText("");
        }
    }

    public void viewListButtonClicked(View view){
        Intent intent = new Intent(this, viewExerciseListActivity.class);
        startActivity(intent);
    }




}
