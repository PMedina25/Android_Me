package com.example.android_me.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.example.android_me.R;
import com.example.android_me.data.AndroidImageAssets;

public class AndroidMeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_me);

        // Only create new fragments when there is no previously saved state
        if (savedInstanceState == null) {

            // Create a new BodyPartFragment instance and display it using the FragmentManager
            BodyPartFragment headFragment = new BodyPartFragment();
            BodyPartFragment bodyFragment = new BodyPartFragment();
            BodyPartFragment legFragment = new BodyPartFragment();

            // Set the list of image id's for the head fragment and set the position to the second image in the list
            headFragment.setImageIds(AndroidImageAssets.getHeads());
            // Get the correct index to access in the array of head images from the intent
            // Set the default value to 0
            int headIndex = getIntent().getIntExtra("headIndex", 0);
            headFragment.setListIndex(headIndex);

            // Set the list of image id's for the body fragment and set the position to the second image in the list
            bodyFragment.setImageIds(AndroidImageAssets.getBodies());
            // Get the correct index to access in the array of body images from the intent
            // Set the default value to 0
            int bodyIndex = getIntent().getIntExtra("bodyIndex", 0);
            bodyFragment.setListIndex(bodyIndex);

            // Set the list of image id's for the leg fragment and set the position to the second image in the list
            legFragment.setImageIds(AndroidImageAssets.getLegs());
            // Get the correct index to access in the array of head images from the intent
            // Set the default value to 0
            int legIndex = getIntent().getIntExtra("legIndex", 0);
            legFragment.setListIndex(legIndex);

            // Use a FragmentManager and transaction to add the fragment to the screen
            FragmentManager fragmentManager = getSupportFragmentManager();

            // Fragment transactions
            fragmentManager.beginTransaction().add(R.id.head_container, headFragment).commit();
            fragmentManager.beginTransaction().add(R.id.body_container, bodyFragment).commit();
            fragmentManager.beginTransaction().add(R.id.leg_container, legFragment).commit();
        }
    }
}