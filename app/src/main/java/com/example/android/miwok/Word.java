package com.example.android.miwok;

/**
 * Created by hanssi on 8/28/16.
 */

/* Word class represents a vocabulary word which contains a default translation and its Miwok translation */
public class Word {
    /* Default translation for the word */
    private String mDefaultTranslation;

    /* Miwok translation for the word */
    private String mMiwokTranslation;

    /**
     * Create a new word object
     * @param defaultTranslation is the word the user is familiar with
     * @param miwokTranslation is the word in Miwok language
     */
    public Word(String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
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

}
