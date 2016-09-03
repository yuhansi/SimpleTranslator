package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
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

    /* Handles audio focus when playing an audio file */
    private AudioManager mAudioManager;

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

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // AUDIOFOCUS_LOSS_TRANSIENT: audio focus was lost for a short amount of time
                // AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK: system is allowed to continue playing sound at a lower level

                // Pause playback and reset player to the start of the file (replay the file after resuming playback)
                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // Audio focus has been regained and playback can be resumed
                mMediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // Audio focus has been lost, stop playback and clean up media player resources
                mMediaPlayer.release();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        // Create and setup AudioManager to request audio focus
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(R.string.color_red, R.string.miwok_color_red, R.drawable.color_red, R.raw.color_red));
        words.add(new Word(R.string.color_mustard_yellow, R.string.miwok_color_mustard_yellow, R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));
        words.add(new Word(R.string.color_dusty_yellow, R.string.miwok_color_dusty_yellow, R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word(R.string.color_green, R.string.miwok_color_green, R.drawable.color_green, R.raw.color_green));
        words.add(new Word(R.string.color_brown, R.string.miwok_color_brown, R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word(R.string.color_gray, R.string.miwok_color_gray, R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word(R.string.color_black, R.string.miwok_color_black, R.drawable.color_black, R.raw.color_black));
        words.add(new Word(R.string.color_white, R.string.miwok_color_white, R.drawable.color_white, R.raw.color_white));

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

                // Get the Word object at the given position the user clicked on
                Word word = words.get(position);

                // Request audio focus with short amount of time AUDIOFOCUS_GAIN_TRANSIENT in order to play file
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    // we have audio focus now

                    // Create and setup the MediaPlayer for the audio resource associated with the current word
                    mMediaPlayer = MediaPlayer.create(ColorsActivity.this, word.getAudioResourceId());
                    // Start the audio file
                    mMediaPlayer.start();
                    // Setup a listener on the media player
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }

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

            // Regardless of whether audio focus was granted, abandon it and unregister AudioFocusChangeListener
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
