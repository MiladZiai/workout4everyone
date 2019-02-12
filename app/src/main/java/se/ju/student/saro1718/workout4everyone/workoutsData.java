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
    public static ArrayList<workoutVariables> specoficSearchedWorkoutList = new ArrayList<>();

    static {
        String[] exerciseTitle = {"1","2","3"};
        String[] exerciseDescription = {"3","4","5"};
        /*for(int i = 0; i < 100 ; i++) {
            workoutList.add(new workoutVariables("1", "advancedTitle", exerciseTitle, exerciseDescription, "advanced"));
            workoutList.add(new workoutVariables("2", "mediumTitle", exerciseTitle, exerciseDescription, "medium"));
            workoutList.add(new workoutVariables("3", "simpleTitle", exerciseTitle, exerciseDescription, "simple"));
        }*/
    }


    public static class workoutVariables{
        private String ownerId;
        private String workoutTitle;
        private ArrayList<String[]> workoutExerciseTitle;
        private ArrayList<String[]> workoutExerciseDescription;
        private String workoutLevel;
        private String workoutImage;


        //returns id of workout
        public String getOwnerId(){
            return this.ownerId;
        }
        //returns title of workout
        public String getWorkoutTitle(){
            return this.workoutTitle;
        }

        //returns level of workout
        public String getWorkoutLevel(){
            return this.workoutLevel;
        }

        //returns titles of workout exercises
        public ArrayList<String[]> getWorkoutExerciseTitle(){
            return this.workoutExerciseTitle;
        }

        //return descriptions of workout exercises
        public ArrayList<String[]> getWorkoutExerciseDescription(){
            return  this.workoutExerciseDescription;
        }

        //returns image of workout
        public String getWorkoutImage(){
            return this.workoutImage;
        }

        workoutVariables(String id , String title, ArrayList<String[]> workoutExerciseTitle , ArrayList<String[]> workoutExerciseDescription,String workoutLevel,String workoutImage){
            this.ownerId = id;
            this.workoutTitle = title;
            this.workoutExerciseTitle = workoutExerciseTitle;
            this.workoutExerciseDescription = workoutExerciseDescription;
            this.workoutLevel = workoutLevel;
            this.workoutImage = workoutImage;
        }


    }

}
