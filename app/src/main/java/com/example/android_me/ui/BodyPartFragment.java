package com.example.android_me.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android_me.R;

import java.util.ArrayList;
import java.util.List;


public class BodyPartFragment extends Fragment {

    // Create final Strings to store state information about the list of images and list index
    public static final String IMAGE_ID_LIST = "image_ids";
    public static final String LIST_INDEX = "list_index";

    public static final String TAG = "BodyPartFragment";

    // Create a setter method and class variable to set and store of a list of image resources
    private List<Integer> mImageIds;
    private int mListIndex;

    // Create another setter method and variable to track and set the index of the list item to display
    // ex. index = 0 is the first image id in the given list , index 1 is the second, and so on

    public void setImageIds(List<Integer> mImageIds) {
        this.mImageIds = mImageIds;
    }

    public void setListIndex(int mListIndex) {
        this.mListIndex = mListIndex;
    }

    // Mandatory constructor for instantiating the fragment
    public BodyPartFragment() {}

    /**
     * Inflates the fragment layout and sets any image resources
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Load the saved state (the list of images and list index) if there is one
        if (savedInstanceState != null) {
            mImageIds = savedInstanceState.getIntegerArrayList(IMAGE_ID_LIST);
            mListIndex = savedInstanceState.getInt(LIST_INDEX);
        }

        // Inflate the Android-Me fragment layout
        View rootView = inflater.inflate(R.layout.fragment_body_part, container, false);

        // Get a reference to the ImageView in the fragment layout
        final ImageView imageView = (ImageView) rootView.findViewById(R.id.body_part_image_view);

        // If a list of image ids exists, set the image resource to the correct item in that list
        // Otherwise, create a Log statement that indicates that the list was not found
        if (mImageIds != null) {
            // Set the image resource to display
            imageView.setImageResource(mImageIds.get(mListIndex));

            // Set a click listener on the image view and on a click increment the list index and set the image resource
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListIndex++;
                    // If you reach the end of a list of images, set the list index back to 0 (the first item in the list)
                    if (mListIndex >= mImageIds.size()) {
                        mListIndex = 0;
                    }
                    imageView.setImageResource(mImageIds.get(mListIndex));
                }
            });
        } else {
            // Log a message saying the image id list is null
            Log.v(TAG, "This fragment has a null list of image id's");
        }

        // Return the rootView
        return rootView;
    }

    // Override onSaveInstanceState and save the current state of this fragment
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntegerArrayList(IMAGE_ID_LIST, (ArrayList<Integer>) mImageIds);
        outState.putInt(LIST_INDEX, mListIndex);
    }
}
