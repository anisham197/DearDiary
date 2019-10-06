package com.abhilash1in.icare;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

// Main
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,DatePickerDialog.OnDateSetListener{
         //DialogFragmentClass.DoctorRequestListener

    int flag=0;

    TextView nameTextView, emailTextView;
    public static String email;
    static final String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String name = getIntent().getExtras().getString("name");
        email = getIntent().getExtras().getString("email");


        Log.d("main" , name + " " + email);

      /*  DialogFragment newFragment = new DialogFragmentClass();
        newFragment.show(getSupportFragmentManager(), "region");
        newFragment.setCancelable(false);*/

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_main, new NoteFragment() ).commit();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        nameTextView =(TextView)navigationView.getHeaderView(0).findViewById(R.id.name);
        emailTextView=(TextView)navigationView.getHeaderView(0).findViewById(R.id.email);

        nameTextView.setText(name);
        emailTextView.setText(email);


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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.prev_entry, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calendar_prev) {
            datePicker();
            flag=1;
            return true;
        }

        if(id==R.id.action_calendar_insights)
        {
            datePicker();
            flag=2;
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void datePicker()
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        String date = setDateFormat(year, month, day);
        if(flag==1)
        {
            Intent intent2 = new Intent();
            intent2.setClass(this, ViewEntryActivity.class);
            intent2.putExtra("date", date);
            startActivity(intent2);
        }
       else
        {
            Log.d("trial","else part");
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_note) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new NoteFragment() ).commit();
        }
        else if (id == R.id.nav_prev_entry) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new PrevEntryFragment() ).commit();

        } else if (id == R.id.nav_insights) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new InsightsFragment() ).commit();

        } else if (id == R.id.nav_doc_remark) {
            fragmentManager.beginTransaction().replace(R.id.content_main, new DoctorAdviceFragment() ).commit();

        } else if (id == R.id.nav_logout) {
            Intent intent2 = new Intent();
            intent2.setClass(this, SignInActivity.class);
            intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent2);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    String setDateFormat(int year, int month, int day)
    {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);

        SimpleDateFormat df = new SimpleDateFormat("EEEE , d MMMM yyyy");
        String date = df.format(cal.getTime());
        return date;
    }

    /*@Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }*/


}
