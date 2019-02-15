package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class viewExerciseListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_exercise);

        //Intiating list
        initList();
    }

    public void initList(){
        System.out.println("4");
        ListView listView = (ListView) findViewById(R.id.exerciseListView);
        System.out.println("5");
        listView.setAdapter(new ArrayAdapter<workoutsData.Exercise>(
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
}
