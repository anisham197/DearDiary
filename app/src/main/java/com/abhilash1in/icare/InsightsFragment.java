package com.abhilash1in.icare;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;


import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.NumberFormat;

public class InsightsFragment extends Fragment {

    final String url = "http://tedxmsrit2016.in:3000/insights";
    static final String TAG="InsightsFragment";
    OkHttpClient client;
    GraphView graph;
    LineGraphSeries<DataPoint> angerSeries, joySeries, fearSeries, sadnessSeries;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.insights, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_insights, container, false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Today's Entry");


        client = new OkHttpClient();

        try {
            retrieveFromDB();
        } catch (Exception e) {
            e.printStackTrace();
        }


        graph = (GraphView) view.findViewById(R.id.graph);

        graph.setTitle("Insights of Past Week");
        graph.setTitleTextSize(40);
        graph.setTitleColor(Color.BLACK);

        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(0);
        nf.setMinimumIntegerDigits(1);

        NumberFormat nf2 = NumberFormat.getInstance();
        nf2.setMinimumFractionDigits(1);
        nf2.setMinimumIntegerDigits(1);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter(nf, nf2));

        graph.getGridLabelRenderer().setHorizontalAxisTitle("Daily Values");
        graph.getGridLabelRenderer().setVerticalAxisTitle("Percentage of Emotion");
        graph.getGridLabelRenderer().setNumVerticalLabels(6);
        graph.getGridLabelRenderer().setNumHorizontalLabels(8);
        graph.getGridLabelRenderer().setPadding(5);

        /*graph.getViewport().setScrollable(true); // enables horizontal scrolling
        graph.getViewport().setScrollableY(true); // enables vertical scrolling
        graph.getViewport().setScalable(true); // enables horizontal zooming and scrolling
        graph.getViewport().setScalableY(true); // en*/

        graph.getViewport().setMaxX(8);
        graph.getViewport().setMinX(1);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMaxY(1);
        graph.getViewport().setMinY(0);
        graph.getViewport().setYAxisBoundsManual(true);



        return view;
    }

    public void retrieveFromDB(){

        RequestBody formBody = new FormEncodingBuilder()
                .add("user_id", MainActivity.email )
                .build();
        Request request = new Request.Builder()
                .url(url)
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
                    String JsonString = response.body().string();
                    Log.d("Success", JsonString);
                    try
                    {
                        JSONObject responseJsonObject = new JSONObject(JsonString);
                        Log.d(TAG,"response jscon object : "+responseJsonObject);

                        JSONObject resValues = responseJsonObject.getJSONObject("res");
                        JSONArray anger = resValues.getJSONArray("anger");

                        DataPoint angerArray[] = new DataPoint[anger.length()];
                        for (int i = 0; i < anger.length(); i++) {
                            angerArray[i] = new DataPoint(i+1,Double.parseDouble(anger.getString(i)));
                        }

                        angerSeries = new LineGraphSeries<>(angerArray);
                        angerSeries.setTitle("Anger");
                        angerSeries.setColor(Color.parseColor("#B71C1C"));
                        angerSeries.setDrawDataPoints(true);
                        angerSeries.setDataPointsRadius(6);
                        angerSeries.setThickness(4);


                        JSONArray joy = resValues.getJSONArray("joy");

                        DataPoint joyArray[] = new DataPoint[joy.length()];
                        for (int i = 0; i < anger.length(); i++) {
                            joyArray[i] = new DataPoint(i+1,Double.parseDouble(anger.getString(i)));
                        }

                        joySeries = new LineGraphSeries<>(joyArray);
                        joySeries.setTitle("Joy");
                        joySeries.setColor(Color.parseColor("#F9A825"));
                        joySeries.setDrawDataPoints(true);
                        joySeries.setDataPointsRadius(6);
                        joySeries.setThickness(4);


                        JSONArray fear = resValues.getJSONArray("fear");

                        DataPoint fearArray[] = new DataPoint[fear.length()];
                        for (int i = 0; i < anger.length(); i++) {
                            fearArray[i] = new DataPoint(i+1,Double.parseDouble(anger.getString(i)));
                        }

                        fearSeries = new LineGraphSeries<>(fearArray);
                        fearSeries.setTitle("Fear");
                        fearSeries.setColor(Color.parseColor("#4CAF50"));
                        fearSeries.setDrawDataPoints(true);
                        fearSeries.setDataPointsRadius(6);
                        fearSeries.setThickness(4);


                        JSONArray sadness = resValues.getJSONArray("sadness");

                        DataPoint sadnessArray[] = new DataPoint[anger.length()];
                        for (int i = 0; i < sadness.length(); i++) {
                            sadnessArray[i] = new DataPoint(i+1,Double.parseDouble(anger.getString(i)));
                        }

                        sadnessSeries = new LineGraphSeries<>(sadnessArray);
                        sadnessSeries.setTitle("Sadness");
                        sadnessSeries.setColor(Color.parseColor("#0D47A1"));
                        sadnessSeries.setDrawDataPoints(true);
                        sadnessSeries.setDataPointsRadius(6);
                        sadnessSeries.setThickness(4);


                        graph.addSeries(angerSeries);
                        graph.addSeries(joySeries);
                        graph.addSeries(fearSeries);
                        graph.addSeries(sadnessSeries);
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



