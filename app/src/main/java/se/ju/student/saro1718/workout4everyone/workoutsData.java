package se.ju.student.saro1718.workout4everyone;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static android.support.constraint.Constraints.TAG;

public class workoutsData{
    public static ArrayList<workoutVariables> workoutList = new ArrayList<>();
    //to reference the Activity

    static {
        String[] exerciseTitle = {"1","2","3"};
        String[] exerciseDescription = {"3","4","5"};
        for(int i = 0; i < 100 ; i++) {
            workoutList.add(new workoutVariables(1, "advancedTitle", exerciseTitle, exerciseDescription, "advanced"));
            workoutList.add(new workoutVariables(2, "mediumTitle", exerciseTitle, exerciseDescription, "medium"));
            workoutList.add(new workoutVariables(3, "simpleTitle", exerciseTitle, exerciseDescription, "simple"));
        }
    }


    public static class workoutVariables{
        private int workoutId;
        private String workoutTitle;
        private String workoutExerciseTitle[];
        private String workoutExerciseDescription[];
        private String workoutLevel;
        private Bitmap workoutImage;


        //returns title of workout
        public String getWorkoutTitle(){
            return this.workoutTitle;
        }

        //returns level of workout
        public String getWorkoutLevel(){
            return this.workoutLevel;
        }

        //returns titles of workout exercises
        public String[] getWorkoutExerciseTitle(){
            return this.workoutExerciseTitle;
        }

        //return descriptions of workout exercises
        public String[] getWorkoutExerciseDescription(){
            return  this.workoutExerciseDescription;
        }

        //returns image of workout
        public Bitmap getWorkoutImage(){
            return this.workoutImage;
        }

        workoutVariables(int id , String title, String[] workoutExerciseTitle , String[] workoutExerciseDescription,String workoutLevel/*,Bitmap workoutImage*/){
            this.workoutId = id;
            this.workoutTitle = title;
            this.workoutExerciseTitle = workoutExerciseTitle;
            this.workoutExerciseDescription = workoutExerciseDescription;
            this.workoutLevel = workoutLevel;
            //this.workoutImage = workoutImage;
        }


    }

}
