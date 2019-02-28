package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import static se.ju.student.saro1718.workout4everyone.MainActivity.database;
import static se.ju.student.saro1718.workout4everyone.MainActivity.localDatabase;

public class viewWorkoutsListActivity extends AppCompatActivity {

    ArrayList<workoutsData.workoutVariables> list;

    SwipeMenuListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workouts_list);

        listView = (SwipeMenuListView) findViewById(R.id.viewAllWorkoutsListView);

        swipeMenuCreator();

        workoutsData.workoutList.clear();

        localDatabase.readData();
        System.out.println(workoutsData.workoutList);
        loadWorkouts(false);
    }


    //creates the swipe menu
    public void swipeMenuCreator(){

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // create "open" item
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(new ColorDrawable(Color.rgb(0xC9, 0xC9,
                        0xCE)));
                // set item width
                openItem.setWidth(170);
                // set item title
                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setTitleSize(18);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);
                // add to menu
                menu.addMenuItem(openItem);

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170  );
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };
        // set creator
        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        // open
                        break;
                    case 1:
                        // delete
                        localDatabase.deleteRow(workoutsData.workoutList.get(position).getOwnerId());

                        Toast.makeText(getApplicationContext(),"Successfully deleted", Toast.LENGTH_SHORT).show();

                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);

                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }

    // load all workouts with custom listview
    private void loadWorkouts(final boolean global){

        ListView workoutsListView = (ListView) findViewById(R.id.viewAllWorkoutsListView);
        workoutsListView.setAdapter(new ArrayAdapter<workoutsData.workoutVariables>(
                this,0,workoutsData.workoutList){

            @Override
            public View getView(int position, View view, ViewGroup parent){
                if(view == null) {
                    LayoutInflater inflater = LayoutInflater.from(getContext());
                    view = inflater.inflate(R.layout.custom_listview, parent, false);
                    ViewHolder viewHolder = new ViewHolder();

                    //ListView cell variables
                    viewHolder.imageOfListCell = (ImageView) view.findViewById(R.id.imageOfListViewCell);
                    viewHolder.titleOfListCell = (TextView) view.findViewById(R.id.titleOfListViewCell);
                    viewHolder.levelOfListCell = (TextView) view.findViewById(R.id.levelOfListViewCell);
                    view.setTag(viewHolder);
                }

                workoutsData.workoutVariables currentWorkout = getItem(position);
                if(global) {
                    ((ViewHolder) view.getTag()).imageOfListCell.setImageBitmap(database.downloadImage(currentWorkout.getWorkoutImage()));
                }else{
                    ((ViewHolder) view.getTag()).imageOfListCell.setImageBitmap(currentWorkout.getWorkoutBitmap());
                }
                ((ViewHolder)view.getTag()).titleOfListCell.setText(currentWorkout.getWorkoutTitle());
                ((ViewHolder)view.getTag()).levelOfListCell.setText(currentWorkout.getWorkoutLevel());

                return view;

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(viewWorkoutsListActivity.this, viewExerciseListActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);

            }
        });

    }


    //ListView cell holder
    public class ViewHolder{
        public ImageView imageOfListCell;
        public TextView titleOfListCell;
        public TextView levelOfListCell;
    }

}
