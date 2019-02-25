package se.ju.student.saro1718.workout4everyone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
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
        database =  getWritableDatabase();

        String workoutTableQuery = "CREATE TABLE IF NOT EXISTS WORKOUT (id INTEGER PRIMARY KEY AUTOINCREMENT, title VARCHAR, image BLOG)";
        String exerciseTableQuery = "CREATE TABLE IF NOT EXISTS EXERCISES (ownerID INTEGER , place INTEGER, title VARCHAR, description VARCHAR, CONSTRAINT fk_column FOREIGN KEY(ownerID) REFERENCES WORKOUT(id))";

        database.execSQL(workoutTableQuery);
        database.execSQL(exerciseTableQuery);
    }


    public void insertData(String title, byte[] image, ArrayList<String> exerciseTitleArray, ArrayList<String> exerciseDescriptionArray){

        System.out.println(exerciseTitleArray);
        System.out.println(exerciseDescriptionArray);

        ContentValues workoutTableValues = new ContentValues();

        workoutTableValues.put("title",title);
        workoutTableValues.put("image",image);

        long id = database.insert("WORKOUT",null,workoutTableValues);
        if(id != -1) {
            for (int i = 0; i < exerciseTitleArray.size(); i++) {
                ContentValues exerciseTableValues = new ContentValues();
                exerciseTableValues.put("ownerID", id);
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
            String id = Integer.toString(cursorWorkoutTable.getInt(0));
            String exerciseQuery = "SELECT * FROM EXERCISES WHERE ownerID = " + id;

            Cursor cursorExerciseTable = database.rawQuery(exerciseQuery,null);

            ArrayList<String> exerciseTitleArray = new ArrayList<>();
            ArrayList<String> exerciseDescriptionArray = new ArrayList<>();

            while(cursorExerciseTable.moveToNext()){
                exerciseTitleArray.add(cursorExerciseTable.getString(2));
                exerciseDescriptionArray.add(cursorExerciseTable.getString(3));
            }

            String title = cursorWorkoutTable.getString(1);
            byte[] image = cursorWorkoutTable.getBlob(2);

            Bitmap imageBitmap = BitmapFactory.decodeByteArray(image,0, image.length);
            workoutsData.workoutVariables workout = new workoutsData.workoutVariables(id,title,exerciseTitleArray,exerciseDescriptionArray,"advanced",null);
            workout.setWorkoutBitmap(imageBitmap);

            workoutsData.workoutList.add(workout);

        }

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
