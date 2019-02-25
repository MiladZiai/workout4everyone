package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    private BottomNavigationView bottomNavigationView;
    public static fireBaseApi database;
    private static Fragment _homeFragment;
    private static Fragment _profileFragment;
    public static LocalDB localDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setup databases
        database = new fireBaseApi();
        localDatabase = new LocalDB(this,"localDatabase",null,1);

        //set up fragments for onclick
        _homeFragment = new homeFragment();
        _profileFragment = new profileFragment();


        //bottom navigation bar
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavbar);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        if(savedInstanceState == null) {
            switchFragment(R.id.nav_home);
        }

    }

    //switch fragment
    public void switchFragment(int id){

        Fragment fragment = null;

        switch(id){
            case R.id.nav_home:
                fragment = _homeFragment;
                break;
            case R.id.nav_profile:
                fragment = _profileFragment;
                break;
            default:
                fragment = _homeFragment;
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,fragment).commit();
    }

    //bottom navigation view listener
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switchFragment(menuItem.getItemId());
            return true;
        }
    };




}
