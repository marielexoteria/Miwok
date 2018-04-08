package com.example.android.miwok;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    //Declaring a variable of object MediaPlayer to play the sound files
    private MediaPlayer mMediaPlayer;

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            releaseMediaPlayer();
        }
    };

    //Handles auto focus when playing a sound file
    private AudioManager mAudioManager;

    //This listener gets triggered whenever the audio focus changes
    //(we gain or lose audio focus because of another app or device).
    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                @Override
                public void onAudioFocusChange(int focusChange) {
                    /*
                     * The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                     * short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                     * our app is allowed to continue playing sound but at a lower volume. We'll
                     * treat both cases the same way because our app is playing short sound files.
                     */

                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                            focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        //Pause playing the sound file
                        mMediaPlayer.pause();

                        //For the user it's better to start hearing the word from the beginning rather
                        //than hearing the rest of the word when the app lost audio focus
                        mMediaPlayer.seekTo(0);

                    } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        //The AUDIOFOCUS_GAIN case means we have regained focus and can
                        //resume playing the sound file
                        mMediaPlayer.start();

                    } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        //The AUDIOFOCUS_LOSS case means we've lost audio focus and we can stop playing
                        //the sound file and clean up resources with our custom made method
                        releaseMediaPlayer();
                    }
                }
            };


    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.word_list, container, false);
        //Creates and sets up the AudioManager variable to request audio focus
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        //Creating an ArrayList of words in English and Miwok
        final ArrayList<Word> words = new ArrayList<Word>();

        //Populating the ArrayList
        words.add(new Word(R.string.default_language_red, R.string.miwok_language_red, R.drawable.color_red, R.raw.color_red));
        words.add(new Word(R.string.default_language_green, R.string.miwok_language_green,
                R.drawable.color_green, R.raw.color_green));
        words.add(new Word(R.string.default_language_brown, R.string.miwok_language_brown,
                R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word(R.string.default_language_gray, R.string.miwok_language_gray,
                R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word(R.string.default_language_black, R.string.miwok_language_black,
                R.drawable.color_black, R.raw.color_black));
        words.add(new Word(R.string.default_language_white, R.string.miwok_language_white,
                R.drawable.color_white, R.raw.color_white));
        words.add(new Word(R.string.default_language_dusty_yellow, R.string.miwok_language_dusty_yellow,
                R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        words.add(new Word(R.string.default_language_mustard_yellow, R.string.miwok_language_mustard_yellow,
                R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        WordAdapter adapter = new WordAdapter(getActivity(), words, R.color.category_colors);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(adapter);

        //Playing the sound files
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Release the media player if it currently exists because we are about to
                // play a different sound file
                releaseMediaPlayer();

                //Declaring a variable that will get the position of the word that has been clicked on
                //to play the pertinent sound file
                Word word = words.get(position);

                //Requesting audio focus for playing the sound file
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        //Use the music stream
                        AudioManager.STREAM_MUSIC,
                        //Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                    //We have audio focus now - start playing the file

                    mMediaPlayer = MediaPlayer.create(getActivity(), word.getAudioResourceID());
                    mMediaPlayer.start();

                    //Setting a listener on the sound file to release the media player resources once
                    //the sound file has completed and stopped playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStop() {
        super.onStop();
        //When the activity is stopped, release the media player resources because we won't
        //be playing any more sounds
        releaseMediaPlayer();
    }

    //Up navigation goes back to the parent activity. This code alters the visual behavior and shows
    //the child activity moving to the right and then the parent activity shows.
    //Using "NavUtils.navigateUpFromSameTask(this);" does the opposite effect
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            //Regardless of whether or not we were granted audio focus, abandon it. This also
            //unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }


}
