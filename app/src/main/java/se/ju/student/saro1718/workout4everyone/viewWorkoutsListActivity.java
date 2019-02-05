package se.ju.student.saro1718.workout4everyone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class viewWorkoutsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_workouts_list);
        loadAllWorkouts();
    }

    // load all workouts from api
    private void loadAllWorkouts(){
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
                ((ViewHolder)view.getTag()).imageOfListCell.setImageBitmap(currentWorkout.workoutImage());
                ((ViewHolder)view.getTag()).titleOfListCell.setText(currentWorkout.workoutTitle());
                ((ViewHolder)view.getTag()).levelOfListCell.setText(currentWorkout.workoutLevel());

                return view;

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
