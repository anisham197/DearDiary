package com.abhilash1in.icare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ViewEntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_entry);

        String date = getIntent().getExtras().getString("date");

        TextView tv = (TextView)findViewById(R.id.date);
        tv.setText(date);

        TextView text = (TextView)findViewById(R.id.text);

        //TODO: set text dynamically


    }
}
