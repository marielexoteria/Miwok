package com.example.android.miwok;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class PhrasesActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_list);

        //Creates and sets up the AudioManager variable to request audio focus
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        //Creating an ArrayList of words in English and Miwok
        final ArrayList<Word> words = new ArrayList<Word>();

        //Populating the ArrayList
        words.add(new Word("Where are you going?", "minto wuksus",
                R.raw.phrase_where_are_you_going));
        words.add(new Word("What is your name?", "tinnә oyaase'nә",
                R.raw.phrase_what_is_your_name));
        words.add(new Word("My name is...", "oyaaset...",
                R.raw.phrase_my_name_is));
        words.add(new Word("How are you feeling?", "michәksәs?",
                R.raw.phrase_how_are_you_feeling));
        words.add(new Word("I'm feeling good.", "kuchi achit",
                R.raw.phrase_im_feeling_good));
        words.add(new Word("Are you coming?", "әәnәs'aa?",
                R.raw.phrase_are_you_coming));
        words.add(new Word("Yes, I'm coming.", "hәә’ әәnәm",
                R.raw.phrase_yes_im_coming));
        words.add(new Word("I'm coming.", "әәnәm",
                R.raw.phrase_im_coming));
        words.add(new Word("Let's go.", "yoowutis",
                R.raw.phrase_lets_go));
        words.add(new Word("Come here.", "әnni'nem",
                R.raw.phrase_come_here));

        WordAdapter adapter = new WordAdapter(this, words, R.color.category_phrases);
        ListView listView = (ListView) findViewById(R.id.list);
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
<<<<<<< HEAD

                //Requesting audio focus for playing the sound file
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                        //Use the music stream
                        AudioManager.STREAM_MUSIC,
                        //Request permanent focus
                        AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //mAudioManager.registerMediaButtonEventReceiver(RemoteControlReceiver);
                    //We have audio focus now - start playing the file

                    mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceID());
                    mMediaPlayer.start();

                    //Setting a listener on the sound file to release the media player resources once
                    //the sound file has completed and stopped playing
                    mMediaPlayer.setOnCompletionListener(mCompletionListener);
                }
||||||| merged common ancestors
                mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceID());
                mMediaPlayer.start();
=======
                mMediaPlayer = MediaPlayer.create(PhrasesActivity.this, word.getAudioResourceID());
                mMediaPlayer.start();
                Log.v("File being played is: ", String.valueOf(word.getAudioResourceID()));
                Log.v("Current Miwok word is: ", word.getMiwokTranslation());
>>>>>>> 4a0e4b935e3a419650ebdd7b55fcf3e2db77a253
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //When the activity is stopped, release the media player resources because we won't
        //be playing any more sounds
        releaseMediaPlayer();
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
