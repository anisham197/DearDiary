package com.abhilash1in.icare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;


public class SignInActivity extends AppCompatActivity implements
        View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private EditText mEmailField;
    private EditText mPasswordField;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    ProgressDialog progressDialog;

    String email;
    SharedPreferences pref;
    SharedPreferences.Editor editor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        progressDialog = new ProgressDialog(SignInActivity.this);

        // Views
        mEmailField = (EditText) findViewById(R.id.field_email);
        mPasswordField = (EditText) findViewById(R.id.field_password);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);
        findViewById(R.id.sign_up_text_view).setOnClickListener(this);
        //findViewById(R.id.sign_out_button).setOnClickListener(this);

        // Initialize auth
       /* mAuth = FirebaseAuth.getInstance();

        // Initialize auth state listener
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                    // If user is logged in, redirects to DepartmentList page
                    Intent intent = new Intent();
                    intent.setClass(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };*/
    }

    @Override
    public void onStart() {
        super.onStart();
       // mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
       /* if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }*/
    }

    // Authenticates user and signs in
  /*  private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        progressDialog.show();
        progressDialog.setMessage("Loading");

        // sign in with email
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        } else {

                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                            editor = pref.edit();
                            editor.putString("uid", user.getUid());
                            editor.apply();

                            Log.d("Anisha", "USER ID " + user.getUid() );

                            Intent intent = new Intent();
                            intent.setClass(SignInActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });
    }

    // Checking if username and password are entered
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            // Switches to the Sign up Page on clicking create new account
            case R.id.sign_up_text_view:
                Intent intent = new Intent();
                intent.setClass(SignInActivity.this, NewSignUpActivity.class);
                startActivity(intent);
                break;
            // Signs in the user by checkig with database
            case R.id.email_sign_in_button:
                Intent intent2 = new Intent();
                intent2.setClass(SignInActivity.this, MainActivity.class);
                email = mEmailField.getText().toString();
                intent2.putExtra("email", email);
                intent2.putExtra("name", " ");
                startActivity(intent2);
                break;
                //signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());


        }
    }
}
