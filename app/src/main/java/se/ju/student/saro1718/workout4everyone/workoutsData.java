package se.ju.student.saro1718.workout4everyone;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class workoutsData{
    public static ArrayList<workoutVariables> workoutList = new ArrayList<>();
    public static ArrayList<workoutVariables> specoficSearchedWorkoutList = new ArrayList<>();


    public static class workoutVariables{
        private String postId;
        private String ownerId;
        private String workoutTitle;
        private ArrayList<String> workoutExerciseTitle;
        private ArrayList<String> workoutExerciseDescription;
        private String workoutLevel;
        private String workoutImage;
        private Bitmap workoutBitmap;

        /////////       returns       /////////

        //returns postId of workout from firebase
        public String getPostId(){return this.postId;}

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
        public ArrayList<String> getWorkoutExerciseTitle(){
            return this.workoutExerciseTitle;
        }

        //return descriptions of workout exercises
        public ArrayList<String> getWorkoutExerciseDescription(){
            return  this.workoutExerciseDescription;
        }

        public Bitmap getWorkoutBitmap(){
            return this.workoutBitmap;
        }

        //returns image of workout
        public String getWorkoutImage(){
            return this.workoutImage;
        }

        /////////       sets        /////////

        //set postId to inserted id in parameters
        public void setPostId(String id){this.postId = id;}

        //sets owner id to inserted id in parameters
        public void setOwnerId(String id) {
            this.ownerId = id;
        }

        //sets workoutTitle to inserted title in parameters
        public void setWorkoutTitle(String title){
            this.workoutTitle = title;
        }

        //appends exercise title to workoutExerciseTitle array
        public void addWorkoutExerciseTitle(String workoutExerciseTitle){
            this.workoutExerciseTitle.add(workoutExerciseTitle);
        }

        //appends exercise description to workoutExerciseDescription array
        public void addWorkoutExerciseDescription(String workoutExerciseDescription){
            this.workoutExerciseDescription.add(workoutExerciseDescription);
        }

        //sets workoutLevel to inserted level in parameters (describes the level of this workout)
        public void setWorkoutLevel(String level){
            this.workoutLevel = level;
        }

        //sets workoutImage to inserted imageUrl in parameters (used by global database since we store the image as the post id)
        public void setWorkoutImage(String imageUrl){
            this.workoutImage = imageUrl;
        }

        //sets workoutBitmap to inserted bitmap in parameters
        public void setWorkoutBitmap(Bitmap bitmap){
            this.workoutBitmap = bitmap;
        }

        workoutVariables(String id , String title, ArrayList<String> workoutExerciseTitle , ArrayList<String> workoutExerciseDescription, String workoutLevel, String workoutImage){
            this.ownerId = id;
            this.workoutTitle = title;
            this.workoutExerciseTitle = workoutExerciseTitle;
            this.workoutExerciseDescription = workoutExerciseDescription;
            this.workoutLevel = workoutLevel;
            this.workoutImage = workoutImage;
        }
    }


    ////////////////////////////////////////////////////////////////////
    //                      used for listviews                        //
    ////////////////////////////////////////////////////////////////////

    public static ArrayList<listViewHelper> exercises = new ArrayList<>();

    public static class listViewHelper{
        public String title;
        public String desc;

        public String toString(){
            return this.title;
        }

        public String getTitle(){return this.title;}
        public String getDesc(){return this.desc;}

        public listViewHelper(String title,String desc){
            this.title = title;
            this.desc = desc;
        }
    }





}
