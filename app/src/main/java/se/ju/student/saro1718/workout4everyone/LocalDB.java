package se.ju.student.saro1718.workout4everyone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

import static se.ju.student.saro1718.workout4everyone.MainActivity.database;

public class LocalDB extends SQLiteOpenHelper {

    private static SQLiteDatabase database;

    public LocalDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        if (database == null) {

            database =  getWritableDatabase();

         /*   String droptable1 = "DROP TABLE WORKOUT";
            String droptable2 = "DROP TABLE EXERCISES";
            database.execSQL(droptable1);
            database.execSQL(droptable2);
            */


            String workoutTableQuery = "CREATE TABLE IF NOT EXISTS WORKOUT (postID VARCHAR UNIQUE, title VARCHAR, image BLOG)";
            String exerciseTableQuery = "CREATE TABLE IF NOT EXISTS EXERCISES (ownerID VARCHAR , place INTEGER, title VARCHAR, description VARCHAR, CONSTRAINT fk_column FOREIGN KEY(ownerID) REFERENCES WORKOUT(id))";

            database.execSQL(workoutTableQuery);
            database.execSQL(exerciseTableQuery);
        }
    }


    public void insertData(String title, byte[] image, ArrayList<String> exerciseTitleArray, ArrayList<String> exerciseDescriptionArray,String postID){

        ContentValues workoutTableValues = new ContentValues();

        workoutTableValues.put("postID",postID);
        workoutTableValues.put("title",title);
        workoutTableValues.put("image",image);

        long id = database.insert("WORKOUT",null,workoutTableValues);
        if(id != -1) {
            for (int i = 0; i < exerciseTitleArray.size(); i++) {
                ContentValues exerciseTableValues = new ContentValues();
                exerciseTableValues.put("ownerID", postID);
                exerciseTableValues.put("place", i + 1);
                exerciseTableValues.put("title", exerciseTitleArray.get(i));
                exerciseTableValues.put("description", exerciseDescriptionArray.get(i));
                database.insert("EXERCISES", null, exerciseTableValues);
            }
        }
    }

    public void readData(){
        String query = "SELECT * FROM WORKOUT";
        Cursor cursorWorkoutTable = database.rawQuery(query,null);

        while(cursorWorkoutTable.moveToNext()){

            String id = cursorWorkoutTable.getString(0);

            ArrayList<String> exerciseTitleArray = null;
            ArrayList<String> exerciseDescriptionArray = null;
            try {
                String exerciseQuery = "SELECT * FROM EXERCISES WHERE ownerID = " + id;

                Cursor cursorExerciseTable = database.rawQuery(exerciseQuery,null);

                exerciseTitleArray = new ArrayList<>();
                exerciseDescriptionArray = new ArrayList<>();
                //exercises read
                while(cursorExerciseTable.moveToNext()){
                    exerciseTitleArray.add(cursorExerciseTable.getString(2));
                    exerciseDescriptionArray.add(cursorExerciseTable.getString(3));
                }
                cursorExerciseTable.close();
            } catch (Exception e) {
                //no rows in this
            }

            String title = null;
            byte[] image = new byte[0];

            title = cursorWorkoutTable.getString(1);
            image = cursorWorkoutTable.getBlob(2);


            Bitmap imageBitmap = BitmapFactory.decodeByteArray(image,0, image.length);
            workoutsData.workoutVariables workout = new workoutsData.workoutVariables(id,title,exerciseTitleArray,exerciseDescriptionArray,"advanced",null);
            workout.setWorkoutBitmap(imageBitmap);

            workoutsData.workoutList.add(workout);

            cursorWorkoutTable.close();

        }

    }


    public void deleteRow(String id){


        //deletes all exercises from exercises table
        String deleteExercisesRows = "DELETE FROM EXERCISES WHERE ownerID = ?";
        SQLiteStatement deleteExerciseStatement = database.compileStatement(deleteExercisesRows);
        deleteExerciseStatement.bindString(1,id);
        deleteExerciseStatement.execute();

        //delete workout from workout table
        String deleteWorkoutRow = "DELETE FROM WORKOUT WHERE postID = ?";
        SQLiteStatement deleteWorkoutStatement = database.compileStatement(deleteWorkoutRow);
        deleteWorkoutStatement.bindString(1,id);
        deleteWorkoutStatement.execute();

        deleteExerciseStatement.close();
        deleteWorkoutStatement.close();

    }


    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
