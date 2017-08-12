package com.parse.fypPlannr;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class eventsListActivity extends AppCompatActivity  {





    ArrayList<String> eventsList = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String selectedItem = "";



    @Override
    public void onBackPressed() {

    }

    @Override
    public void onResume(){
        super.onResume();
        spinnerMeth();
        //refreshList();
    }



    public void refreshList(){

        final   ListView eventListView = (ListView) findViewById(R.id.eventList);


        /*eventListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(eventsListActivity.this,"Hi " + ParseUser.getCurrentUser().toString() + " this will show you event information or allow you to edit events", Toast.LENGTH_SHORT).show();
            }
        });*/

        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,eventsList);

        eventsList.clear();
        arrayAdapter.notifyDataSetChanged();
        final List<String> publicEvents = new ArrayList<String>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
        query.whereEqualTo("cityname", selectedItem);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    Log.i("findInbackground", "retrieved " + objects.size() + " objects");
                    if (objects.size() > 0){
                        for (ParseObject object : objects){
                            publicEvents.add(object.getString("eventname"));

                        }
                        eventsList.addAll(publicEvents);
                        eventListView.setAdapter(arrayAdapter);
                        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent i = new Intent(getApplicationContext(), eventInfo.class);
                                i.putExtra("TEXT", eventsList.get(position));
                                if (publicEvents.contains(eventsList.get(position))){
                                    i.putExtra("ev", 0);
                                }
                                //i.putExtra("ev",0);
                                startActivity(i);

                            }
                        });




                    }
                }
            }

        });

        final List<String> privateList = new ArrayList<String>();
        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("PrivateEvents");
        if (ParseUser.getCurrentUser().get("friendList")==null){
            List<String> empty = new ArrayList<>();
            ParseUser.getCurrentUser().put("friendList", empty);

        }
        query1.whereContainedIn("Host", ParseUser.getCurrentUser().getList("friendList"));
        query1.whereEqualTo("cityname", selectedItem);

        //query.orderByDescending("createdAt");
        //query.setLimit(20);

        query1.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null) {

                    if (objects.size() > 0) {

                        List<Map<String, String>> eventData = new ArrayList<Map<String, String>>();

                        for (ParseObject event : objects) {
                            privateList.add(event.getString("eventname"));

                        }
                        eventsList.addAll(privateList);
                        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent i = new Intent(getApplicationContext(), eventInfo.class);
                                i.putExtra("TEXT", eventsList.get(position));
                                if (privateList.contains(eventsList.get(position))){
                                    i.putExtra("ev", 1);
                                }
                                //i.putExtra("ev", 1);
                                startActivity(i);

                            }
                        });

                       /* SimpleAdapter simpleAdapter = new SimpleAdapter(Feed.this, eventData, android.R.layout.simple_list_item_2, new String[] {"eventName", "username"}, new int[] {android.R.id.text1, android.R.id.text2});

                        feedListView.setAdapter(simpleAdapter);*/

                    }



                }
            }
        });
        arrayAdapter.notifyDataSetChanged();





        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(false);
        }

    }





    public void createEventActivity(){

        Intent eventCreateIntent = new Intent(getApplicationContext(),eventCreateActivity.class);
        startActivity(eventCreateIntent);

    }



    public void createEvent(View view){
        createEventActivity();

    }



    public void mapView (View view){
        Intent mapScreenIntent = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(mapScreenIntent);

    }

    public void friendsList(View view){
        Intent fintent = new Intent(getApplicationContext(), FriendsList.class);
        startActivity(fintent);

    }

    public void logOut(View view){
        ParseUser.logOutInBackground();
        Intent logInScreenIntent = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(logInScreenIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        //refreshList();
        spinnerMeth();











        //setTitle("Plannr App");
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.Swiper);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshList();
            }

        });

    }
    public void spinnerMeth(){



        final Spinner sp = (Spinner) findViewById(R.id.citySpinner);

        final List<String> citys = new ArrayList<String>();
        final ArrayAdapter<String> cityAdapter;

        cityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,citys);


        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("Events");

        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null){
                    Log.i("findInbackground", "retrieved " + objects.size() + " objects");
                    if (objects.size() > 0){
                        citys.add("Select your city");
                        for (ParseObject object : objects){
                            if (!citys.contains(object.getString("cityname")))
                            {
                                citys.add(object.getString("cityname"));
                            }

                        }
                        sp.setAdapter(cityAdapter);


                    }
                }
            }

        }); sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                selectedItem = parent.getItemAtPosition(position).toString();
                refreshList();


            } // to close the onItemSelected
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

    }
}
