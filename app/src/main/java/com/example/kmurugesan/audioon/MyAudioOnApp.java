package com.example.kmurugesan.audioon;

import android.app.Application;
import com.parse.Parse;

/**
 * Created by kmurugesan on 15-04-20.
 */
public class MyAudioOnApp extends Application {

    public void onCreate(){

        super.onCreate();

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "sr8HlpB7C8imQtC8UkGWZ8Z2qOsWJmT5myIhuBS8", "sAtJDdHeNSPruwsI5lG3GA0L9uTxbvRmMIG6kRX4");

    }


}