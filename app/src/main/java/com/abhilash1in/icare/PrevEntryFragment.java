package com.abhilash1in.icare;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrevEntryFragment extends Fragment {


    public PrevEntryFragment() {
        // Required insights public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.prev_entry, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_calendar) {
            datePicker();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void datePicker()
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {

        String date = setDateFormat(year, month, day);
        Intent intent2 = new Intent();
        intent2.setClass(getActivity(), ViewEntryActivity.class);
        intent2.putExtra("date", date);
        startActivity(intent2);
    }

    String setDateFormat(int year, int month, int day)
    {
        String date = "";
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat df = new SimpleDateFormat("d MMMM yyyy");
        date = df.format(cal.getTime());
        return date;
    }*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] days =  new String[7];

        for ( int i = 0 ; i < 7; i++){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DATE, -i);
            SimpleDateFormat df = new SimpleDateFormat("EEEE , d MMMM yyyy");
            days[i] = df.format(cal.getTime());
        }


        // String[] days = { "Saturday", "Friday", "Thursday", "Wednesday", "Tuesday", "Monday", "Sunday"};
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prev_entry, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Previous Entries");
        // The adapter is initialised with department names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.single_entry, days);

        // Adapter mapped to listView
        final ListView listView = (ListView) view.findViewById(R.id.prev_entry_list);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                TextView tv = (TextView)view;
                String date = tv.getText().toString();
                Intent intent2 = new Intent();
                intent2.setClass(getContext(), ViewEntryActivity.class);
                intent2.putExtra("date", date);
                startActivity(intent2);
            }
        });

        listView.setAdapter(adapter);
        return view;
    }
}
