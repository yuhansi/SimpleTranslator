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

public class PhrasesActivity extends AppCompatActivity {
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
        words.add(new Word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        words.add(new Word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        words.add(new Word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        // Create a WordAdapter whose data source is a list of Words
        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);

        // Find the ListView object in the view hierarchy of the PhrasesActivity
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
                    // We have audio focus now

                    // Create and setup the MediaPlayer for the audio resource associated with the current word
                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceId());
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
            // Regardless of the current state of the media player, release its resources since they are no longer needed
            mMediaPlayer.release();
            // Set the media player back to null
            mMediaPlayer = null;

            // Regardless of whether audio focus was granted, abandon it and unregister AudioFocusChangeListener
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }
}
