/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.fypPlannr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener  {

    @Override
    public void onBackPressed() {

    }




    Boolean logInMode = true;
    TextView signLogView;
    @Override
    public void onClick(View view) {
        if (view.getId()== R.id.signLogView){

            Button signLogButton = (Button) findViewById(R.id.signLog);
            if (logInMode){
                logInMode = false;
                signLogButton.setText("Sign Up");
                signLogView.setText("Already have an account? Log in!");
            } else {
                logInMode = true;
                signLogButton.setText("Log In");
                signLogView.setText("Don't have an account? Sign up!");

            }

        }
    }

    public void eventListActivity(){

        Intent intent = new Intent(getApplicationContext(),eventsListActivity.class);
        startActivity(intent);



    }

    public void signUp (View view) {
        EditText userEdittxt = (EditText) findViewById(R.id.userText);
        EditText passwordEditTxt = (EditText) findViewById(R.id.pwText);

        if (passwordEditTxt.getText().toString().matches("") || userEdittxt.getText().toString().matches(""))
        {
            Toast.makeText(this, "Please enter a username and/or Password.", Toast.LENGTH_SHORT).show();
        } else {
            if (logInMode) {ParseUser.logInInBackground(userEdittxt.getText().toString(), passwordEditTxt.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {
                    if (user != null){
                        Log.i("Sign up", "Login Successful");
                        eventListActivity();
                    }else{
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });

            }else{
                ParseUser user = new ParseUser();
                user.setUsername(userEdittxt.getText().toString());
                user.setPassword(passwordEditTxt.getText().toString());

                user.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.i("Sign Up", "Successful");
                            eventListActivity();
                        } else {
                            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setTitle("Plannr App");
        signLogView = (TextView) findViewById(R.id.signLogView);
        signLogView.setOnClickListener(this);

        if (ParseUser.getCurrentUser()!=null){
            eventListActivity();
        }

        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }

}
