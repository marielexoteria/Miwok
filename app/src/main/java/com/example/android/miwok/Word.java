package com.example.android.miwok;

/**
 * Created by marielbackman on 14/03/2018.
 *
 * Contains a default translation (English in this case, since the course was made in English) and its Miwok equivalent.
 */

public class Word {
    //Default translation for the word
    private String mDefaultTranslation;

    //Miwok translation for the word
    private String mMiwokTranslation;

    //Creating the constructor
    public Word(String defaultTranslation, String miwokTranslation) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    //Gets the default translation of the word
    public String getDefaultTranslation() {

        return mDefaultTranslation;
    }

    //Gets the Miwok translation of the word
    public String getMiwokTranslation() {

        return mMiwokTranslation;
    }


}

