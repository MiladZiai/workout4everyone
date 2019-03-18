package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static se.ju.student.saro1718.workout4everyone.R.string.fui_required_field;
import static se.ju.student.saro1718.workout4everyone.R.string.navdrawerclose;
import static se.ju.student.saro1718.workout4everyone.R.string.save;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    public static fireBaseApi database;
    private static Fragment _homeFragment;
    private static Fragment _profileFragment;
    public static LocalDB localDatabase;

    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navdraweropen, R.string.navdrawerclose);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new homeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        //setup databases
        database = new fireBaseApi();
        localDatabase = new LocalDB(this,"localDatabase",null,1);

        //set up fragments for onclick
        _homeFragment = new homeFragment();
        _profileFragment = new profileFragment();


    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new homeFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new profileFragment()).commit();
                break;
            case R.id.create_workouts:
                Intent intent = new Intent(this, createWorkoutActivity.class);
                startActivity(intent);
                break;
            case R.id.view_favourites:
                Intent intent1 = new Intent(this,viewWorkoutsListActivity.class);
                intent1.putExtra("global",false);
                startActivity(intent1);
                break;
            case R.id.view_workouts:
                Intent intent2 = new Intent(this, viewWorkoutsListActivity.class);
                intent2.putExtra("global",true);
                startActivity(intent2);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
