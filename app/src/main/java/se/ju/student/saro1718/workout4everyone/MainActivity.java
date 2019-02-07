package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //bottom navigation bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        if(savedInstanceState == null) {
            switchFragment(R.id.nav_home);
        }

    }

    public void switchFragment(int id){

        Fragment fragment = null;

        switch(id){
            case R.id.nav_home:
                fragment = new homeFragment();
                break;
            case R.id.nav_profile:
                fragment = new profileFragment();
                break;
            default:
                fragment = new homeFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switchFragment(menuItem.getItemId());
            return true;
        }
    };


    //////////////////////////////////////////////
    //                 buttons
    //////////////////////////////////////////////

    //view all workouts button clicked
    public void viewAllWorkoutsButtonClicked(View view){
        Intent intent = new Intent(this, viewAllWorkoutsActivity.class);
        startActivity(intent);
    }

    //create workout button clicked
    public void createWorkoutButtonClicked(View view){
        Intent intent = new Intent(this, createWorkoutActivity.class);
        startActivity(intent);


    }

    //view favorite workouts button clicked
    public void viewFavoriteWorkoutsButtonClicked(View view){
    }

}
