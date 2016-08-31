package com.example.android.miwok;

/**
 * Created by hanssi on 8/28/16.
 */

/* Word class represents a vocabulary word which contains the resource ID for the default
 * translation, a Miwok translation, audio file, and an image file for that word
 */
public class Word {
    /** String resource ID for the default translation of the word */
    private int mDefaultTranslationId;

    /** String resource ID for the Miwok translation of the word */
    private int mMiwokTranslationId;

    /* Audio resource ID for the word */
    private int mAudioResourceId;

    /* Image resource ID for the word */
    private int mImageResourceId = No_IMAGE_PROVIDED;

    /* Constant value that represents no image was provided for this word */
    private static final int No_IMAGE_PROVIDED = -1;

    /**
     * Create a new word object
     * @param defaultTranslationId is the string resource ID for the word in default language
     * @param miwokTranslationId is the string resource Id for the word in the Miwok language
     * @param audioResourceId is the resource ID for the audio file associated with the word
     */
    public Word(int defaultTranslationId, int miwokTranslationId, int audioResourceId) {
        mDefaultTranslationId = defaultTranslationId;
        mMiwokTranslationId = miwokTranslationId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Create a new word object
     * @param defaultTranslationId is the string resource ID for the word in default language
     * @param miwokTranslationId is the string resource Id for the word in the Miwok language
     * @param imageResourceId is the drawable resource ID for the image associated with the word
     * @param audioResourceId is the resource ID for the audio file associated with the word
     */
    public Word(int defaultTranslationId, int miwokTranslationId, int imageResourceId,
                int audioResourceId) {
        mDefaultTranslationId = defaultTranslationId;
        mMiwokTranslationId = miwokTranslationId;
        mImageResourceId = imageResourceId;
        mAudioResourceId = audioResourceId;
    }

    /**
     * Get the string resource ID for the default translation of the word
     */
    public int getDefaultTranslationId() {

        return mDefaultTranslationId;
    }

    /**
     * Get the string resource ID for the Miwok translation of the word
     */
    public int getMiwokTranslationId() {

        return mMiwokTranslationId;
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
