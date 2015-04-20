package com.example.kmurugesan.audioon;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    //protected Button login_button;
    protected Button username_button;
    protected String username_value="";
    protected String password_value="";
    protected Button password_button;
    private final int REQ_CODE_SPEECH_INPUT_USERNAME = 100;
    private final int REQ_CODE_SPEECH_INPUT_PASSWORD = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //login_button = (Button)findViewById(R.id.login_button);
        username_button = (Button)findViewById(R.id.username_button);
        password_button = (Button)findViewById(R.id.password_button);

        username_button.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        promptSpeechInput(REQ_CODE_SPEECH_INPUT_USERNAME);

                    }

        }


        );


        password_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                promptSpeechInput(REQ_CODE_SPEECH_INPUT_PASSWORD);


            }
        });



    }

    private void loginParse(final String username_value, String password_value){
        //this.username_button.setText(username_value);
        //this.password_button.setText(password_value);

        ParseUser.logInInBackground(username_value, password_value, new LogInCallback() {
            public void done(ParseUser user, ParseException e) {
                if (user != null) {
                    // Hooray! The user is logged in.
                    /*ParseQuery<ParseObject> query = ParseQuery.getQuery("AudioProfile");
                    query.getInBackground(username_value, new GetCallback<ParseObject>() {
                        public void done(ParseObject object, ParseException e) {
                            if (e == null) {
                                //no exception, and there was a record for audio profile
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);

                            }
                        }
                    });*/


                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else {
                    // Signup failed. Look at the ParseException to see what happened.
                }
            }
        });

    }

    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput(int requestCode) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {

            startActivityForResult(intent, requestCode);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT_USERNAME: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    this.username_value = (result.get(0)).toLowerCase();


                    //storeUserNameInParse(username_value);
                    /*ParseObject testObject = new ParseObject("TestObject");
                    testObject.put("foo", "bar");
                    testObject.saveInBackground();*/

                }
                break;
            }
            case REQ_CODE_SPEECH_INPUT_PASSWORD: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    this.password_value = (result.get(0)).toLowerCase();
                    loginParse(username_value, password_value);

                    /*ParseObject testObject = new ParseObject("TestObject");
                    testObject.put("foo", "bar");
                    testObject.saveInBackground();*/

                }
                break;
            }

        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
