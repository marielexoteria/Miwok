package com.example.android.miwok;

/**
 * Created by marielbackman on 14/03/2018.
 *
 * Contains a default translation (English in this case, since the course was made in English) and its Miwok equivalent.
 */

public class Word {
    //Default translation for the word
    private int mDefaultTranslation;

    //Miwok translation for the word
    private int mMiwokTranslation;

    //Descriptive picture of the word (the resource ID will link to the actual file)
    private int mImageResourceID = NO_IMAGE_PROVIDED;
    //When mImageResourceID is updated with something else via the methods below, then we know the word has an image

    //Constant that will help to determine whether a specific word has an image to display or not
    //-1 because it's out of the range of all the possible valid resource IDs
    private static final int NO_IMAGE_PROVIDED = -1;

    //Sound file with the pronunciation for the word
    private int mAudioResourceID;

    /*
     * Creating the constructor to use in the activities with pictures
     * @param defaultTranslation     the word in the default language (English in this case)
     * @param miwokTranslation       the word in its Miwok equivalent
     * @param imageResourceID        the ID of the picture associated with the word
     * @param audioResourceID        the ID of the sound file associated with the word
    */
    public Word(int defaultTranslation, int miwokTranslation, int imageResourceID, int audioResourceID) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mImageResourceID = imageResourceID;
        mAudioResourceID = audioResourceID;
    }

    /*
     * Creating the constructor to use in the activities with pictures
     * @param defaultTranslation     the word in the default language (English in this case)
     * @param miwokTranslation       the word in its Miwok equivalent
     * @param audioResourceID        the ID of the sound file associated with the word
    */
    public Word(int defaultTranslation, int miwokTranslation, int audioResourceID) {
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mAudioResourceID = audioResourceID;
    }

    //Returns the default translation of the word
    public int getDefaultTranslation() {
        return mDefaultTranslation;
    }

    //Returns the Miwok translation of the word
    public int getMiwokTranslation() {
        return mMiwokTranslation;
    }

    //Returns the image resource ID
    public int getImageResourceID() {
        return mImageResourceID;
    }

    //Returns the sound file resource ID
    public int getAudioResourceID() {
        return mAudioResourceID;
    }

    //Checking if the word in the position of the ArrayList has an image or not.
    //If it doesn't have an image, then it'll display only the 2 textViews, otherwise it'll display everything
    public boolean hasImage() {
        return mImageResourceID != NO_IMAGE_PROVIDED; //if there is a valid image
    }

}

