package com.example.kmurugesan.audioon;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.util.List;


public class FriendsActivity extends ActionBarActivity {

    protected Button friend_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);

        friend_button = (Button)findViewById(R.id.friend_button);

        ParseUser currentUser = ParseUser.getCurrentUser();
        String currentUserText = currentUser.getUsername();

        ParseQuery<ParseObject> query = ParseQuery.getQuery("Friend");
        query.whereEqualTo("username", currentUserText );
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> friendList, ParseException e) {
                if (e == null) {
                    //Log.d("", "Retrieved " + scoreList.size() + " scores");
                    String friend = friendList.get(0).getString("friend");
                    friend_button.setText(friend);
                } else {
                    //Log.d("score", "Error: " + e.getMessage());
                }
            }
        });



        friend_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(FriendsActivity.this, MessageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("friendName", friend_button.getText());

                startActivity(intent);



            }

        });

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
