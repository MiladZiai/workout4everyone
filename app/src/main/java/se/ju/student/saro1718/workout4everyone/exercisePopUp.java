package se.ju.student.saro1718.workout4everyone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

public class exercisePopUp extends AppCompatActivity {


    public int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_popup);

        displayPopup();

        fetchDescText();
    }

    //displays popup of description
    public void displayPopup(){
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int) (width*.7), (int) (height*.6));
    }


    //fetches the text from description input
    public void fetchDescText(){
        this.position = getIntent().getIntExtra("position", this.position);
        TextView textview = (TextView) findViewById(R.id.descTextView);
        textview.setText(exerciseData.descriptions.get(getIntent().getIntExtra("position",position)).toString());
    }


    //removes exercise from listView
    public void removeButtonClicked(View view){
        exerciseData.exercises.remove(this.position);
        exerciseData.descriptions.remove(this.position);
        finish();
    }

}
