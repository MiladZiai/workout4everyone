package se.ju.student.saro1718.workout4everyone;

import java.util.ArrayList;

public class exerciseData {

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

