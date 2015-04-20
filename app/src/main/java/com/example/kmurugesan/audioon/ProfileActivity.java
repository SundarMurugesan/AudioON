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

import java.io.File;
import java.io.IOException;


public class ProfileActivity extends ActionBarActivity {

    protected Button profile_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_button = (Button)findViewById(R.id.profile_button);

        profile_button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                recordAudio();
                Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }

        });
    }

    private void recordAudio() {

        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/myprofilerecording.mp4";
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

        //Try to upload to Parse
        ParseUser currentUser = ParseUser.getCurrentUser();
        String usernameText = currentUser.getUsername();

        //File file = new File(fileName);
        byte[] audioFileBytesData = fileName.getBytes();
        ParseFile audioFile = new ParseFile("myprofile.mp4", audioFileBytesData);
        audioFile.saveInBackground();
        ParseObject audioProfile = new ParseObject("AudioProfile");
        audioProfile.put("username", usernameText);
        audioProfile.put("audioRecording", audioFile);
        audioProfile.saveInBackground();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
