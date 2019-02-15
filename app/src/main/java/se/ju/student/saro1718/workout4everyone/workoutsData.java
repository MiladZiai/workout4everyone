package se.ju.student.saro1718.workout4everyone;

import java.util.ArrayList;

public class workoutsData{
    public static ArrayList<workoutVariables> workoutList = new ArrayList<>();
    public static ArrayList<workoutVariables> specoficSearchedWorkoutList = new ArrayList<>();


    public static class workoutVariables{
        private String ownerId;
        private String workoutTitle;
        private ArrayList<String> workoutExerciseTitle;
        private ArrayList<String> workoutExerciseDescription;
        private String workoutLevel;
        private String workoutImage;

        /////////       returns       /////////

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

        //returns image of workout
        public String getWorkoutImage(){
            return this.workoutImage;
        }

        /////////       sets        /////////

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
            this.workoutLevel = workoutLevel;
        }

        //sets workoutImage to inserted imageUrl in parameters
        public void setWorkoutImage(String imageUrl){
            this.workoutImage = imageUrl;
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
    //                      used by create workout                    //
    ////////////////////////////////////////////////////////////////////

    public static ArrayList<Exercise> exercises = new ArrayList<>();
    public static ArrayList<Desc> descriptions = new ArrayList<>();

    public static class Exercise{
        public String title;

        public Exercise(String title){
            this.title = title;
        }

        public String toString(){
            return title;
        }
    }

    public static class Desc{
        public String desc;

        public Desc(String description){
            this.desc = description;
        }

        public String toString(){
            return desc;
        }
    }


}
