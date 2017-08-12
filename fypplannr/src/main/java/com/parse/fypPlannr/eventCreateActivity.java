package com.parse.fypPlannr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parse.ParseAnalytics;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class eventCreateActivity extends AppCompatActivity {



    public void cancelPressed (View view){
        onBackPressed();

    }

    public void createEvent(View view){
        EditText eventName = (EditText) findViewById(R.id.eventNameEditText);
        EditText eDescription = (EditText) findViewById(R.id.eventDescriptionEditText);
        EditText eAddress = (EditText) findViewById(R.id.addressEditText);
        EditText eDate = (EditText) findViewById(R.id.dateEditText);
        EditText eTime = (EditText) findViewById(R.id.timeEditText);
        EditText eCity = (EditText) findViewById(R.id.cityEditText);

        CheckBox privateBox = (CheckBox) findViewById(R.id.checkBox);


        if (privateBox.isChecked()){

            ParseObject privateEvents = new ParseObject("PrivateEvents");

            privateEvents.put("eventname", eventName.getText().toString() );

            privateEvents.put("eventdescription", eDescription.getText().toString() );

            privateEvents.put("address", eAddress.getText().toString());

            privateEvents.put("date", eDate.getText().toString() );

            privateEvents.put("time", eTime.getText().toString() );

            privateEvents.put("Host", ParseUser.getCurrentUser().getUsername());

            privateEvents.put("cityname", eCity.getText().toString());


            privateEvents.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        Log.i("SaveInBackground", "successful");
                    } else {
                        Log.i("SaveInBackground","Failed. Error: "+ e.toString());
                    }
                }
            });

            onBackPressed();


        } else{

            ParseObject events = new ParseObject("Events");

            events.put("eventname", eventName.getText().toString() );

            events.put("eventdescription", eDescription.getText().toString() );

            events.put("address", eAddress.getText().toString() );

            events.put("date", eDate.getText().toString() );

            events.put("time", eTime.getText().toString() );

            events.put("Host", ParseUser.getCurrentUser().getUsername());

            events.put("cityname", eCity.getText().toString());


            events.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null){
                        Log.i("SaveInBackground", "successful");
                    } else {
                        Log.i("SaveInBackground","Failed. Error: "+ e.toString());
                    }
                }
            });

            onBackPressed();
        }
    }






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);


        ParseAnalytics.trackAppOpenedInBackground(getIntent());
    }
}
