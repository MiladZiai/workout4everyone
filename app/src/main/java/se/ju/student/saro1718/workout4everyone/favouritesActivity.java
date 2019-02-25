package se.ju.student.saro1718.workout4everyone;

import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;



public class favouritesActivity extends AppCompatActivity {

    /*EditText edtTitle;
    Button btnSave;
    ImageView imageView;
    */


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        //init();
        //loadAllWorkouts();




        /*
        //tillfälligt här
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    localDB.insertData(
                            edtTitle.getText().toString().trim(),
                            imageViewToByte(imageView)
                    );
                    Toast.makeText(getApplicationContext(), "Added successfully!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        */


    }


    /*
    private byte[] imageViewToByte(ImageView image){
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArr = stream.toByteArray();
        return byteArr;

    }
    */

    /*
    private void init(){
        edtTitle = (EditText) findViewById(R.id.titleInput);
        btnSave = (Button) findViewById(R.id.saveWorkoutButton);
        imageView = (ImageView) findViewById(R.id.imageView);
    }
    */


    /*
    //ListView cell holder
    public class ViewHolder{
        public ImageView imageOfListCell;
        public TextView titleOfListCell;
    }

    public void loadAllWorkouts(){
        ListView workoutsListView = (ListView) findViewById(R.id.favouritesListView);
        workoutsListView.setAdapter(new ArrayAdapter<workoutsData.workoutVariables>(
                this,0, workoutsData.workoutList){

            @Override
            public View getView(int position, View view, ViewGroup parent) {

                ViewHolder viewHolder = new ViewHolder();
                if(view == null){
                    LayoutInflater inflater =  LayoutInflater.from(getContext());
                    view = inflater.inflate(R.layout.custom_listview, parent, false);

                    //ListView cell variables
                    viewHolder.imageOfListCell = (ImageView) findViewById(R.id.imageOfListViewCell);
                    viewHolder.titleOfListCell = (TextView) findViewById(R.id.titleOfListViewCell);
                    view.setTag(viewHolder);


                } else{
                    viewHolder = (ViewHolder) view.getTag();
                }

                workoutsData.workoutVariables currentWorkout = getItem(position);

                viewHolder.titleOfListCell.setText(currentWorkout.getWorkoutTitle());

                byte[] workoutImage = Base64.decode(currentWorkout.getWorkoutImage(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(workoutImage, 0, workoutImage.length);

                viewHolder.imageOfListCell.setImageBitmap(bitmap);

                return view;
            }
        });
    }
    */

}
