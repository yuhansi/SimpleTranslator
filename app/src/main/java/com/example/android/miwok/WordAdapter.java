package com.example.android.miwok;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by hanssi on 8/28/16.
 */

/**
 * WordAdapter is an ArrayAdapter that can provide the layout for each list item based on a list of Word objects
 */
public class WordAdapter extends ArrayAdapter<Word> {
    /**
     * Create a new WordAdapter object
     * @param context is the current context that the adapter is being created in
     * @param words is the list of words to be displayed
     */
    public WordAdapter(Context context, ArrayList<Word> words) {
        super(context, 0, words);
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
        miwokTextView.setText(currentWord.getMiwokTranslation());

        // Find the TextView in the list_item.xml layout with the ID default_text_view
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.default_text_view);

        // Get the default translation from the currentWord object and set its text on default TextView
        defaultTextView.setText(currentWord.getDefaultTranslation());

        // Return the whole list item layout to the ListView
        return listItemView;

    }
}
