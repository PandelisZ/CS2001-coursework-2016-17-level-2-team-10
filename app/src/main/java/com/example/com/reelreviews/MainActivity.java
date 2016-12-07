package com.example.com.reelreviews;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by subam on 07/12/16.
 */

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //Directs to the Individual Page activity
    public void openPage (View view){
        Intent intent = new Intent(this,IndividualPage.class);
        startActivity(intent);
    }
}
