package com.example.android.miwok;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {
    /* Handles playback of all audio files */
    private MediaPlayer mMediaPlayer;

    /**
     * This listener gets triggered when the MediaPlayer has completed playing the audio file
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Release the media player resources after audio file has finished playing
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        words.add(new Word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        words.add(new Word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        words.add(new Word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        words.add(new Word("white", "kelelli", R.drawable.color_white, R.raw.color_white));

        // Create a WordAdapter whose data source is a list of Words
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_colors);

        // Find the ListView object in the view hierarchy of the ColorsActivity
        ListView listView = (ListView) findViewById(R.id.list);

        // Make the ListView by using the WordAdapter, so that the ListView will display list item for each Word in the list
        listView.setAdapter(adapter);

        // Set a click listener to play the audio file when the list item is clicked on
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Release the media player if it currently exists and prepare to play another audio file
                releaseMediaPlayer();

                // Get the Word object at the given position the user clciked on
                Word word = words.get(position);
                // Create and setup the MediaPlayer for the audio resource associated with the current word
                mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());
                // Start playback
                mMediaPlayer.start();

                // Setup a listener on the media player
                mMediaPlayer.setOnCompletionListener(mCompletionListener);
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        // When the activity is stopped, release the media player resources
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources
     */
    private void releaseMediaPlayer() {
        // Check the current state of the media player
        if(mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources since they are no longer nedded
            mMediaPlayer.release();
            // Set the media player back to null
            mMediaPlayer = null;
        }
    }
}
