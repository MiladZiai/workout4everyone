package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import static se.ju.student.saro1718.workout4everyone.MainActivity.localDatabase;

public class viewExerciseListActivity extends AppCompatActivity {


    SwipeMenuListView listView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);

        listView = (SwipeMenuListView) findViewById(R.id.exerciseListView);

        swipeMenuCreator();

    }


    //creates the swipe menu
    public void swipeMenuCreator(){

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

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
                        // delete
                        removeExercise(position);

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

    @Override
    protected void onResume() {
        super.onResume();

        //Intiating listView
        initList();
    }

    public void initList(){
        ListView listView = (ListView) findViewById(R.id.exerciseListView);
        listView.setAdapter(new ArrayAdapter<workoutsData.listViewHelper>(
                this,
                android.R.layout.simple_list_item_1,
                workoutsData.exercises
        ));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(viewExerciseListActivity.this, exercisePopUp.class);
                intent.putExtra("position",position);
                startActivity(intent);

            }
        });
    }

    //removes exercise from listView
    private void removeExercise(int position){
        workoutsData.exercises.remove(position);
    }

}


