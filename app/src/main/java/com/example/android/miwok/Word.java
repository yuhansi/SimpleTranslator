package com.example.android.miwok;

/**
 * Created by hanssi on 8/28/16.
 */

/* Word class represents a vocabulary word which contains a default translation,
 * a Miwok translation, and an image for that word
 */
public class Word {
    /* Default translation for the word */
    private String mDefaultTranslation;

    /* Miwok translation for the word */
    private String mMiwokTranslation;

    /* Audio resource ID for the word */
    private int mAudioResourceId;

    /* Image resource ID for the word */
    private int mImageResourceId = No_IMAGE_PROVIDED;

    /* Constant value that represents no image was provided for this word */
    private static final int No_IMAGE_PROVIDED = -1;

    /**
     * Create a new word object
     * @param defaultTranslation is the word the user is familiar with
     * @param miwokTranslation is the word in Miwok language
     * @param audioResourceId is the resource ID for the audio file associated with the word
     */
    public Word(String defaultTranslation, String miwokTranslation, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Create a new word object
     * @param defaultTranslation is the word the user is familiar with
     * @param miwokTranslation is the word in Miwok language
     * @param imageResourceId is the drawable resource ID for the image associated with the word
     * @param audioResourceId is the resource ID for the audio file associated with the word
     */
    public Word(String defaultTranslation, String miwokTranslation, int imageResourceId, int audioResourceId) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Get the default translation of the word
     */
    public String getDefaultTranslation() {

        return mDefaultTranslation;
    }

    /**
     * Get the Miwok translation of the word
     */
    public String getMiwokTranslation() {

        return mMiwokTranslation;
    }

    /**
     * Return the image resource ID of the word
     */
    public int getmImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != No_IMAGE_PROVIDED;
    }

    /**
     * Return the audio resource ID of the word
     */
    public int getAudioResourceId() {
        return mAudioResourceId;
    }

}
