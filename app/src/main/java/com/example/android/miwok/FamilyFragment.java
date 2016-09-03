package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.ArrayList;

/**
 * Created by hanssi on 8/31/16.
 */

/**
 * Fragment that displays a list of family vocabulary words
 */
public class FamilyFragment extends Fragment{
    /* Handles playback of all audio files */
    private MediaPlayer mMediaPlayer;

    /* Handles audio focus when playing an audio file */
    private AudioManager mAudioManager;

    /**
     * This listener get triggered whenever the audio focus changes
     */
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

    public FamilyFragment() {
        // Requires empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);

        // Create and setup AudioManager to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(R.string.family_father, R.string.miwok_family_father, R.drawable.family_father, R.raw.family_father));
        words.add(new Word(R.string.family_mother, R.string.miwok_family_mother, R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word(R.string.family_son, R.string.miwok_family_son, R.drawable.family_son, R.raw.family_son));
        words.add(new Word(R.string.family_daughter, R.string.miwok_family_daughter , R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word(R.string.family_older_brother, R.string.miwok_family_older_brother, R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word(R.string.family_younger_brother, R.string.miwok_family_younger_brother, R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word(R.string.family_older_sister, R.string.miwok_family_older_sister, R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word(R.string.family_younger_sister, R.string.miwok_family_younger_sister, R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word(R.string.family_grandmother, R.string.miwok_family_grandmother, R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word(R.string.family_grandfather, R.string.miwok_family_grandfather, R.drawable.family_grandfather, R.raw.family_grandfather));

        // Create a WordAdapter whose data source is a list of Words
        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_family);

        // Find the ListView object in the view hierarchy of the Activity
        ListView listView = (ListView) rootView.findViewById(R.id.list);

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
                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceId());
                    // Start the audio file
                    mMediaPlayer.start();
                    // Setup a listener on the media player
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }

            }
        });

        return rootView;

    }

    @Override
    public void onStop() {
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
