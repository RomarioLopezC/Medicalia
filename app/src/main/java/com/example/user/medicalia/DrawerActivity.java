package com.example.user.medicalia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.user.medicalia.Utils.Utils;
import com.example.user.medicalia.fragments.DoctorsFragment;
import com.example.user.medicalia.fragments.ProfileFragment;
import com.example.user.medicalia.models.Patient;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private String jsonCurrentUser;

    @Bind(R.id.textView_pacient_name)
    TextView textView_pacient_name;

    @Bind(R.id.textView_pacient_email)
    TextView textView_pacient_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        ButterKnife.bind(this);

         jsonCurrentUser = getSharedPreferences(getString(R.string.name_shared_preferences), Context.MODE_APPEND)
                .getString(getString(R.string.current_user_key), "{\n" +
                        "  \"blood_type\": \"B++\",\n" +
                        "  \"birthday\": \"9999-12-12\",\n" +
                        "  \"height\": 1,\n" +
                        "  \"weight\": 69,\n" +
                        "  \"allergies\": \"none\",\n" +
                        "  \"gender\": \"macho\",\n" +
                        "  \"user\": {\n" +
                        "    \"email\": \"test@hotmail.com\",\n" +
                        "    \"name\": \"Test\",\n" +
                        "    \"lastname\": \"Perez\",\n" +
                        "    \"mobile\": \"9993939393\",\n" +
                        "    \"token\": \"22c07aa8cca26a484b707e363dd90f3d\",\n" +
                        "    \"user_type\": null\n" +
                        "  }\n" +
                        "}");

        Patient currentUser = Utils.toUserAtributtes(jsonCurrentUser);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textView_pacient_name.setText(currentUser.getUserAttributes().getName());
        textView_pacient_email.setText(currentUser.getUserAttributes().getEmail());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        boolean fragmentTransaction = false;
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putString("user", jsonCurrentUser);

        if (id == R.id.nav_camera) {
            fragment = new ProfileFragment();
            fragment.setArguments(bundle);
            fragmentTransaction = true;

        } else if (id == R.id.find_by_name) {
            fragment = new DoctorsFragment();
            fragment.setArguments(bundle);
            fragmentTransaction = true;

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_logout) {

            SharedPreferences.Editor edit = getSharedPreferences(getString(R.string.name_shared_preferences), Context.MODE_APPEND).edit();
            edit.clear();
            edit.apply();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        if (fragmentTransaction){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_drawer, fragment)
                    .commit();

            item.setChecked(true);
            getSupportActionBar().setTitle(item.getTitle());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
