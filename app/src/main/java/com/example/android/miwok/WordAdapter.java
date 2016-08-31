package com.example.android.miwok;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.ImageView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by hanssi on 8/28/16.
 */

/**
 * WordAdapter is an ArrayAdapter that can provide the layout for each list item based on a list of Word objects
 */
public class WordAdapter extends ArrayAdapter<Word> {
    /* Resource ID for the background color for this list of words */
    private int mColorResourceId;
    /**
     * Create a new WordAdapter object
     * @param context is the current context that the adapter is being created in
     * @param words is the list of words to be displayed
     * @param colorResourceId is the resource ID for the background color for this list of words
     */
    public WordAdapter(Context context, ArrayList<Word> words, int colorResourceId) {
        super(context, 0, words);
        mColorResourceId = colorResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, if not, inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // Get the Word object located at this position in the list
        Word currentWord = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view
        TextView miwokTextView = (TextView) listItemView.findViewById(R.id.miwok_text_view);

        // Get the Miwok translation from the currentWord object and set its text on Miwok TextView
        miwokTextView.setText(currentWord.getMiwokTranslationId());

        // Find the TextView in the list_item.xml layout with the ID default_text_view
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);

        // Get the default translation from the currentWord object and set its text on default TextView
        defaultTextView.setText(currentWord.getDefaultTranslationId());

        // Find the ImageView in the list_item.xml layout with the ID image
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.image);

        // Check is an image is provided for this word or not
        if(currentWord.hasImage()) {
            // If an image is available, display the provided image based on the resource ID
            imageView.setImageResource(currentWord.getmImageResourceId());
            // Make the view visible
            imageView.setVisibility(View.VISIBLE);
        }
        else {
            // Otherwise make the view invisible
            imageView.setVisibility(View.GONE);
        }

        // Set the theme color for the list item
        View textContainer = listItemView.findViewById(R.id.text_container);
        // Find the color that the resource ID maps to
        int color = ContextCompat.getColor(getContext(), mColorResourceId);
        // Set the background color of the text container View
        textContainer.setBackgroundColor(color);

        // Return the whole list item layout to the ListView
        return listItemView;

    }
}
