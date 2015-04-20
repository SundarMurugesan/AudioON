package com.example.kmurugesan.audioon;

import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.io.IOException;


public class MessageActivity extends ActionBarActivity {


    protected Button message_button;
    protected String friendName;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        message_button = (Button)findViewById(R.id.message_button);

        Intent myIntent = getIntent(); // gets the previously created intent
        this.friendName = myIntent.getStringExtra("friendName");

        message_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordAudio(MessageActivity.this.friendName);
                Intent intent = new Intent(MessageActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }

        });
    }

    private void recordAudio(String friendName) {

        ParseUser currentUser = ParseUser.getCurrentUser();
        String currentUserText = currentUser.getUsername();

        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/messagerecording.mp4";
        //File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC);


        MediaRecorder recorder = new MediaRecorder();
        recorder.reset();

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);

        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        recorder.setOutputFile(fileName);
        //recorder.setMaxDuration(10000);
        try {
            recorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        recorder.start();   // Recording is now started

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recorder.stop();

        recorder.reset();   // You can reuse the object by going back to setAudioSource() step
        recorder.release(); // Now the object cannot be reused

        //File file = new File(fileName);
        byte[] audioFileBytesData = fileName.getBytes();
        ParseFile audioFile = new ParseFile("message.mp4", audioFileBytesData);
        audioFile.saveInBackground();
        ParseObject audioProfile = new ParseObject("Message");
        audioProfile.put("sender", currentUserText);
        audioProfile.put("receiver", friendName);
        audioProfile.put("messageRecording", audioFile);
        audioProfile.saveInBackground();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_message, menu);
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
