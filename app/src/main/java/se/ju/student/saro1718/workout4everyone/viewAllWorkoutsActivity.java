package se.ju.student.saro1718.workout4everyone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

public class viewAllWorkoutsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_workouts);

        loadAllWorkouts();
        
    }

    // load all workouts from api
    private void loadAllWorkouts(){
        ListView listview = (ListView) findViewById(R.id.viewAllWorkoutsListView);

    }
}
