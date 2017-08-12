package com.parse.fypPlannr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class eventInfo extends AppCompatActivity {


    /* String a;
     String b;
     String c;
    // String d;*/
    String name;
    int type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_info);
        Bundle extras = getIntent().getExtras();
        final String data = extras.getString("TEXT");

        int eventType = extras.getInt("ev");
        type = eventType;
        setTitle(data);
        final Button ebutton;
        ebutton = (Button)findViewById(R.id.editButton);
        // ebutton.setVisibility(View.INVISIBLE);

        final TextView descriptionText = (TextView) findViewById(R.id.textDescription);
        final TextView dateText = (TextView) findViewById(R.id.textDate);
        final TextView timeText = (TextView) findViewById(R.id.textTime);
        final TextView hostText = (TextView) findViewById(R.id.textHost);
        final TextView addressText = (TextView) findViewById(R.id.textAddress);

//TODO Query one of the databases for data, if it contains it use that database else query the other one.

        if (eventType < 1){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
            query.whereEqualTo("eventname", data);
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size() > 0) {

                        for (int i = 0; i < objects.size(); i++) {
                            ParseObject p = objects.get(i);
                            descriptionText.setText(p.getString("eventdescription"));
                            dateText.setText(p.get("date").toString());
                            timeText.setText(p.get("time").toString());
                            hostText.setText(p.getString("Host"));
                            addressText.setText(p.getString("address"));
                            name = p.getObjectId().toString();
                        }
                    }

                }
            });  if (hostText.getText().toString().equals(ParseUser.getCurrentUser().toString())){
                ebutton.setVisibility(View.VISIBLE);
            }
        } else
        {
            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("PrivateEvents");
            query1.whereEqualTo("eventname", data);
            query1.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> objects, ParseException e) {
                    if (objects.size() > 0) {

                        for (int i = 0; i < objects.size(); i++) {
                            ParseObject p = objects.get(i);
                            descriptionText.setText(p.getString("eventdescription"));
                            dateText.setText(p.get("date").toString());
                            timeText.setText(p.get("time").toString());
                            hostText.setText(p.getString("Host"));
                            addressText.setText(p.getString("address"));
                            name = p.getObjectId().toString();

                        }

                    }

                }
            });
         /*   if (hostText.getText().toString().equals(ParseUser.getCurrentUser().toString())){
                ebutton.setVisibility(View.VISIBLE);
            }*/

        }
    }


    public void report (View view) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.getInBackground(name, new GetCallback<ParseObject>() {
            @Override
            public void done(ParseObject object, ParseException e) {
                object.add("complaints", "event reported by" +ParseUser.getCurrentUser().getUsername().toString());

                object.saveInBackground();
            }
        });


    }

    public void imGoing (View view){
        Intent i = new Intent(getApplicationContext(), AttendingList.class);
        i.putExtra("text", name);
        i.putExtra("type", type);
        i.putExtra("int",2);
        startActivity(i);

    }

    public void notGoing (View view){
        Intent i = new Intent(getApplicationContext(), AttendingList.class);
        i.putExtra("text", name);
        i.putExtra("type", type);
        i.putExtra("int",3);
        startActivity(i);
    }

}



