package se.ju.student.saro1718.workout4everyone;

import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    public static fireBaseApi database;
    public static LocalDB localDatabase;


    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawer = findViewById(R.id.drawer_layout);

        navigationView = findViewById(R.id.nav_view);
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

        validateCreateWorkout();


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
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new homeFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new signInFragment()).commit();
                break;
            case R.id.create_workouts:
                Intent intent = new Intent(this, createWorkoutActivity.class);
                startActivity(intent);
                break;
            case R.id.view_favourites:
                Intent intent1 = new Intent(this,registerUserActivity.class);
                startActivity(intent1);
                break;
            case R.id.view_workouts:
                Intent intent2 = new Intent(this, viewWorkoutsListActivity.class);
                startActivity(intent2);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void validateCreateWorkout(){
        NavigationView navMenu = findViewById(R.id.nav_view);
        Menu menu = navMenu.getMenu();
        MenuItem item = (MenuItem) menu.findItem(R.id.create_workouts);

        if(database.getFirebaseAuth().getCurrentUser() == null) {
            item.setEnabled(false);
        }else{
            item.setEnabled(true);
        }
    }


}
