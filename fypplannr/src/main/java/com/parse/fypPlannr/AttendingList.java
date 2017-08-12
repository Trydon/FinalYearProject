package com.parse.fypPlannr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AttendingList extends AppCompatActivity {

    ArrayAdapter<String> cantGoAdapter;
    ArrayAdapter<String> canGoAdapter;
    List<String> cantGo = new ArrayList<>();
    List<String> canGo = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attending_list);

        Bundle extras = getIntent().getExtras();
        final String data = extras.getString("text");
        int type = extras.getInt("type");
        int canOrCant = extras.getInt("int");

        if (type==0){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
            query.getInBackground(data, new GetCallback<ParseObject>() {
                ListView cantListView = (ListView) findViewById(R.id.list2);
                ListView canListView = (ListView) findViewById(R.id.list1);
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (object.getList("notAttending") == null){
                        List<String> empty = new ArrayList<>();
                        object.put("notAttending", empty);
                        cantGoAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,cantGo);
                        cantListView.setAdapter(cantGoAdapter);
                        cantGoAdapter.notifyDataSetChanged();

                    }
                    else

                    {
                        cantGo = object.getList("notAttending");
                        cantGoAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, cantGo);
                        cantListView.setAdapter(cantGoAdapter);
                        cantGoAdapter.notifyDataSetChanged();

                    }

                    if (object.getList("attending") == null)
                    {
                        List<String> empty = new ArrayList<>();
                        object.put("attending", empty);
                    }
                    else
                    {
                        canGo = object.getList("attending");
                        canGoAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, canGo);
                        canListView.setAdapter(canGoAdapter);
                        canGoAdapter.notifyDataSetChanged();

                    }

                }
            });
        }else if (type == 1){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("PrivateEvents");
            query.getInBackground(data, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (object.getList("attending") == null){
                        List<String> empty = new ArrayList<>();
                        object.put("attending", empty);}
                    cantGo = object.getList("attending");

                    if (object.getList("notAttending")==null){
                        List<String> empty = new ArrayList<>();
                        object.put("notAttending", empty);}
                    canGo = object.getList("notAttending");
                }
            });
        }








        //cantListView.setAdapter(cantGoAdapter);
        //canListView.setAdapter(canGoAdapter);


//Adding to the database works fine

        if (canOrCant == 2 && type == 0){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
            query.getInBackground(data, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {


                    if (object.getList("attending") == null){
                        List<String> empty = new ArrayList<>();
                        object.put("attending", empty);}

                    if (!object.getList("attending").contains(ParseUser.getCurrentUser().getUsername()))
                        if (object.getList("attending") == null){
                            List<String> empty = new ArrayList<>();
                            object.put("attending", empty);}


                    { object.add("attending", ParseUser.getCurrentUser().getUsername());
                        if (object.getList("notAttending").contains(ParseUser.getCurrentUser().getUsername())){
                            object.remove(ParseUser.getCurrentUser().getUsername());
                        }

                        object.saveInBackground();}
                }
            });
        }else if (canOrCant == 3 && type ==0)
        {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
            query.getInBackground(data, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {


                    if (object.getList("notAttending") == null){
                        List<String> empty = new ArrayList<>();
                        object.put("notAttending", empty);}

                    if (!object.getList("notAttending").contains(ParseUser.getCurrentUser().getUsername()))


                    { object.add("notAttending", ParseUser.getCurrentUser().getUsername());
                        if (object.getList("attending").contains(ParseUser.getCurrentUser().getUsername())){
                            object.remove(ParseUser.getCurrentUser().getUsername());
                        }

                        cantGo = object.getList("notAttending");
                        cantGoAdapter.notifyDataSetChanged();

                        object.saveInBackground();}
                }




                //Log.i("test", data + eventType);
            });

        } else if (canOrCant == 2 && type == 1){
            ParseQuery<ParseObject> query = ParseQuery.getQuery("PrivateEvents");
            query.getInBackground(data, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {


                    if (object.getList("attending") == null){
                        List<String> empty = new ArrayList<>();
                        object.put("attending", empty);}

                    if (!object.getList("attending").contains(ParseUser.getCurrentUser().getUsername()))


                    { object.add("attending", ParseUser.getCurrentUser().getUsername());
                        if (object.getList("notAttending").contains(ParseUser.getCurrentUser().getUsername())){
                            object.remove(ParseUser.getCurrentUser().getUsername());
                        }
                        object.saveInBackground();}
                }
            });
        }else if (canOrCant == 3 && type ==1)
        {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Events");
            query.getInBackground(data, new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {


                    if (object.getList("notAttending") == null){
                        List<String> empty = new ArrayList<>();
                        object.put("notAttending", empty);}

                    if (!object.getList("notAttending").contains(ParseUser.getCurrentUser().getUsername()))


                    { object.add("notAttending", ParseUser.getCurrentUser().getUsername());
                        if (object.getList("attending").contains(ParseUser.getCurrentUser().getUsername())){
                            object.remove(ParseUser.getCurrentUser().getUsername());
                        }
                        object.saveInBackground();}
                }


            });

        }
    }
}
