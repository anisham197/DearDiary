package com.abhilash1in.icare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class NewSignUpActivity extends AppCompatActivity implements
        View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private EditText mEmailField;
    private EditText mNameField;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private DatabaseReference mDatabase;
    private FirebaseUser user;

    String name, email;
    ProgressDialog progressDialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sign_up);
        progressDialog = new ProgressDialog(NewSignUpActivity.this);

        Log.d(TAG," sign up");
        // Views
        mEmailField = (EditText) findViewById(R.id.field_email);
        mNameField = (EditText) findViewById(R.id.field_name);

        // Buttons
        findViewById(R.id.email_create_account_button).setOnClickListener(this);

        client = new OkHttpClient();

    }

    @Override
    public void onStart() {
        super.onStart();
        //mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
       /* if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }*/
    }




    // On clicking sign up button, the account is created (Added to database)
    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        intent.setClass(NewSignUpActivity.this, MainActivity.class);
        email = mEmailField.getText().toString();
        name = mNameField.getText().toString();

        try {
            writeToDB();
        } catch (Exception e) {
            Log.d(TAG," catch block");
            e.printStackTrace();
        }

        intent.putExtra("name", name);
        intent.putExtra("email", email);
        startActivity(intent);
        //createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
    }


    public void writeToDB()throws Exception {

        RequestBody formBody = new FormEncodingBuilder()
                .add("user_id", email )
                .add("name",name)
                .build();
        Request request = new Request.Builder()
                .url("http://tedxmsrit2016.in:3000/init")
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
                  /*  ToastHandler mToastHandler = new ToastHandler(getApplicationContext());
                    mToastHandler.showToast("Registered!", Toast.LENGTH_SHORT);
                   */
                    /*
                     new NewSignUpActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(NewSignUpActivity.this, "Registered!", Toast.LENGTH_SHORT).show();
                        }
                    });*/
                    String JsonString=response.body().string();
                    Log.d(TAG,"Success"+ JsonString);
                    try
                    {
                        JSONObject responseJsonObject=new JSONObject(JsonString);
                        Log.d(TAG," json object status"+ responseJsonObject.getString("status"));
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
