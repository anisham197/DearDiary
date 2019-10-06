package com.abhilash1in.icare;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment {


    EditText bodyEditText;
    TextView dateTextView;
    int yy,mm,dd,dayNum;
    String day;
    String data;
    OkHttpClient client;
    static final String TAG="NoteFragment";
    public Double anger,disgust,fear,joy,sadness;
    public NoteFragment() {
        // Required insights public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_note, container, false);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Today's Entry");

        bodyEditText=(EditText)view.findViewById(R.id.bodyEdit);
        dateTextView=(TextView)view.findViewById(R.id.dateTextView);
        bodyEditText.requestFocus();
        //bodyEditText.setBackgroundColor(R.color.yellow);


       client = new OkHttpClient();

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view,"Diary entry saved!",Snackbar.LENGTH_LONG).setAction("Action",null).show();

                Log.d(TAG," data entered : "+bodyEditText.getText().toString());
                if(!bodyEditText.getText().toString().equals(""))
                {
                    Snackbar.make(view,"Diary entry saved!",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                    data = bodyEditText.getText().toString();
                    try {
                        writeToDB();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                else
                {
                    Snackbar.make(view,"Enter something",Snackbar.LENGTH_LONG).setAction("Action",null).show();
                }

            }
        });
        final Calendar c = Calendar.getInstance();
         yy = c.get(Calendar.YEAR);
         mm = c.get(Calendar.MONTH);
         dd = c.get(Calendar.DAY_OF_MONTH);
        dayNum = c.get(Calendar.DAY_OF_WEEK);
        Log.d(TAG,"day "+c.get(Calendar.DAY_OF_WEEK));

        switch (dayNum) {
            case Calendar.SUNDAY:
                day="Sunday";
                break;

            case Calendar.MONDAY:
               day="Monday";
                break;

            case Calendar.TUESDAY:
                day="Tuesday";
                break;
            case Calendar.WEDNESDAY:
                day="Wednesday";
                break;

            case Calendar.THURSDAY:
                day="Thursday";
                break;

            case  Calendar.FRIDAY:
                day="Friday";
                break;

            case Calendar.SATURDAY:
                day="Saturday";
                break;
        }

        String monthName = new DateFormatSymbols().getMonths()[mm];
        Log.d(TAG,"month name  "+monthName);

        // set current date into textview
       dateTextView.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append(" , ").append(dd).append(" ").append(monthName).append(" ").append(yy).append(" :")
                );


        return view;
    }

    public void writeToDB()throws Exception {

            RequestBody formBody = new FormEncodingBuilder()
                    .add("user_id", MainActivity.email )
                    .add("payload", data)
                    .add("day", "" + dd)
                    .add("month", "" + mm)
                    .add("year", "" + yy)
                    .build();
            Request request = new Request.Builder()
                    .url("http://tedxmsrit2016.in:3000/post")
                    .post(formBody)
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Request request, IOException e) {
                    Log.d("Failed", e.getMessage());
                }

                @Override
                public void onResponse(Response response) throws IOException {
                    Log.d(TAG,"on response");
                    if (!response.isSuccessful())
                        Log.v("error","Code: "+response.code()+", Error message: "+response.message());
                    else
                    {
                        String JsonString=response.body().string();
                        Log.d("Success", JsonString);
                        try
                        {
                            JSONObject responseJsonObject=new JSONObject(JsonString);
                            Log.d(TAG,"response jscon object : "+responseJsonObject);
                            String analysisJsonString=responseJsonObject.getString("analysis");
                            JSONObject analysisJSONObject=new JSONObject(analysisJsonString);
                            Log.d("Analysis json :",analysisJSONObject.toString());
                            String emotionString=analysisJSONObject.getString("docEmotions");
                            Log.d("Emotions json :",emotionString);
                            JSONObject emotionJSONObject=new JSONObject(emotionString);
                            anger=emotionJSONObject.getDouble("anger");
                            disgust=emotionJSONObject.getDouble("disgust");
                            fear=emotionJSONObject.getDouble("fear");
                            joy=emotionJSONObject.getDouble("joy");
                            sadness=emotionJSONObject.getDouble("sadness");
                            Log.d(TAG,"anger "+anger.toString());
                        }

                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                   // Log.d("Success",response.body().string());

                }
            });



        }

    }

