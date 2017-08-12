package com.parse.fypPlannr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.CountCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FriendsList extends AppCompatActivity {

    List<String> list = new ArrayList<>();
    ArrayAdapter<String> friendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_list);


        if (ParseUser.getCurrentUser().get("friendList")==null){
            List<String> empty = new ArrayList<>();
            ParseUser.getCurrentUser().put("friendList", empty);

        }
        list = ParseUser.getCurrentUser().getList("friendList");
        ListView friendListView = (ListView) findViewById(R.id.listview);
        friendAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        friendListView.setAdapter(friendAdapter);

    }



/*    public void addFriend (View view){
        EditText mText = (EditText) findViewById(R.id.editText);
        ParseUser.getCurrentUser().getList("friendList").add(mText.getText().toString());
        ParseUser.getCurrentUser().saveInBackground();

    }*/

    public void addFriend(View view) {

        final EditText mText = (EditText) findViewById(R.id.editText);

        final ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereEqualTo("username", mText.getText().toString());

        query.countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                if (e == null) {
                    if (count == 0) {
                        Toast.makeText(getApplicationContext(), "User Doesn't Exist", Toast.LENGTH_LONG).show();
                    } else if (ParseUser.getCurrentUser().getList("friendList").contains(mText.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "User is already a friend", Toast.LENGTH_LONG).show();
                    } else {

                        ParseUser.getCurrentUser().add("friendList",(mText.getText().toString()));
                        ParseUser.getCurrentUser().saveInBackground();
                        Intent I = new Intent (getApplicationContext(),FriendsList.class);
                        startActivity(I);

                        try {
                            ParseUser.getCurrentUser().save();

                        } catch (ParseException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}



