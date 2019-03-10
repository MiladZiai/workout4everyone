package se.ju.student.saro1718.workout4everyone;

import android.media.Image;
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

public class workoutDetailView extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail_viw);


        //loadWorkoutDetailView();
    }

    /*
    private void loadWorkoutDetailView(){

        ImageView workoutImage = (ImageView) findViewById(R.id.imageViewDetail);
        ListView exerxiseListView = (ListView) findViewById(R.id.detailViewListView);
        exerxiseListView.setAdapter(new ArrayAdapter<workoutsData.workoutVariables>(
                this, 0, workoutsData.workoutList
        ));

        @Override
        public View getView(int position, View view, ViewGroup parent){
            if(view == null){
                LayoutInflater inflater = LayoutInflater.from(getContext());
                view = inflater.inflate(R.layout.activity_workout_detail_viw, parent, false);
                ViewHolder viewHolder = new ViewHolder();

                viewHolder.imageView = (ImageView) findViewById(R.id.imageView);
                viewHolder.workoutTitle = (TextView) findViewById(R.id.titleTextViewDetail);

            }
        }

    }


    //ListView cell holder
    public class ViewHolder{
        public ImageView imageView;
        public TextView workoutTitle;
        public TextView workoutLevel;
        public ListView exerciseListView;

    }*/



}
