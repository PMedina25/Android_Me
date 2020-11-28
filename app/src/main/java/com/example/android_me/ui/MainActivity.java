package com.example.android_me.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android_me.R;
import com.example.android_me.data.AndroidImageAssets;


public class MainActivity extends AppCompatActivity implements MasterListFragment.OnImageClickListener {

    private int headIndex;
    private int bodyIndex;
    private int legIndex;

    private Button mButtonNext;

    // Create a variable to track whether to display a two-pane or single-pane UI
    // A single-pane display refers to phone screens, and two-pane to larger tablet screens
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // If you are making a two-pane display, add new BodyPartFragments to create an initial Android-Me image
        // Also, for the two-pane display, get rid of the "Next" button in the master list fragment
        if (findViewById(R.id.android_me_linear_layout) != null) {
            // This LinearLayout will only initially exist in the two-pane tablet case
            mTwoPane = true;

            // Getting rid fo the "Next" button that appears on phones for launching a separate activity
            Button nextButton = (Button) findViewById(R.id.buttonNext);
            nextButton.setVisibility(View.GONE);

            // Change the GridView to space out the images mor on tablet
            GridView gridView = (GridView) findViewById(R.id.images_grid_view);
            gridView.setNumColumns(2);

            // Create new body part fragments
            if (savedInstanceState == null) {

                // Create a new BodyPartFragment instance and display it using the FragmentManager
                BodyPartFragment headFragment = new BodyPartFragment();
                BodyPartFragment bodyFragment = new BodyPartFragment();
                BodyPartFragment legFragment = new BodyPartFragment();

                // Set the list of image id's for the head fragment and set the position to the second image in the list
                headFragment.setImageIds(AndroidImageAssets.getHeads());

                // Set the list of image id's for the body fragment and set the position to the second image in the list
                bodyFragment.setImageIds(AndroidImageAssets.getBodies());

                // Set the list of image id's for the leg fragment and set the position to the second image in the list
                legFragment.setImageIds(AndroidImageAssets.getLegs());

                // Use a FragmentManager and transaction to add the fragment to the screen
                FragmentManager fragmentManager = getSupportFragmentManager();

                // Fragment transactions
                fragmentManager.beginTransaction().add(R.id.head_container, headFragment).commit();
                fragmentManager.beginTransaction().add(R.id.body_container, bodyFragment).commit();
                fragmentManager.beginTransaction().add(R.id.leg_container, legFragment).commit();
            }
        } else {
            // We are in single-pane mode and displaying fragments on a phone in separate activities
            mTwoPane = false;
        }

    }

    // Define the behaviour for onImageSelected
    @Override
    public void onImageSelected(int position) {
        // Create a Toast that displays the position that was clicked
        Toast.makeText(this, "Position clicked = " + position, Toast.LENGTH_SHORT).show();

        // bodyPartNumber will be = 0 for the head fragment, 1 for the body, and 2 for the leg fragment
        // Dividing by 12 gives us these integer values because each list of images resources has a size of 12
        int bodyPartNumber = position / 12;

        // Store the correct list index no matter where in the image list has been clicked
        // This ensures that the index will always be a value between 0-11
        int listIndex = position - 12 * bodyPartNumber;

        // Handle the two-pane case and replace existing fragments right when a new image is selected from the master list
        if (mTwoPane) {
            // Create two=pane interaction
            BodyPartFragment newFragment = new BodyPartFragment();

            // Set the currently displayed item for the correct body part fragment
            switch (bodyPartNumber) {
                case 0:
                    // A head image has been clicked
                    // Give the correct image resources to the new fragment
                    newFragment.setImageIds(AndroidImageAssets.getHeads());
                    newFragment.setListIndex(listIndex);
                    // Replace the old head fragment with a new one
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.head_container, newFragment)
                            .commit();
                    break;
                case 1:
                    newFragment.setImageIds(AndroidImageAssets.getBodies());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.body_container, newFragment)
                            .commit();
                    break;
                case 2:
                    newFragment.setImageIds(AndroidImageAssets.getLegs());
                    newFragment.setListIndex(listIndex);
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.leg_container, newFragment)
                            .commit();
                    break;
                default:
                    break;
            }
        } else {
            // Handle the single-pane phone case by passing information in a Bundle attached to an Intent

            switch (bodyPartNumber) {
                case 0:
                    headIndex = listIndex;
                    break;
                case 1:
                    bodyIndex = listIndex;
                    break;
                case 2:
                    legIndex = listIndex;
                    break;
                default:
                    break;
            }

            // Put this information in a Bundle and attach it to an Intent that will launch an AndroidMeActivity
            Bundle b = new Bundle();
            b.putInt("headIndex", headIndex);
            b.putInt("bodyIndex", bodyIndex);
            b.putInt("legIndex", legIndex);

            // Attach the Bundle to an intent
            final Intent intent = new Intent(this, AndroidMeActivity.class);
            intent.putExtras(b);

            mButtonNext = (Button) findViewById(R.id.buttonNext);

            // Get a reference to the "Next" button and launch the intent when this button is clicked
            mButtonNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(intent);
                }
            });
        }
    }
}