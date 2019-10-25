package com.group4.togolist.util;

import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.group4.togolist.R;

public class MediaplayerHelper extends ContextWrapper {

    private Context context;
    private MediaPlayer mMediaPlayer;
    private static AudioManager mAudioManager;
    private AudioManager.OnAudioFocusChangeListener changeListener;

    public MediaplayerHelper(Context context){
        super(context);
        this.context = context;
        getManager();
        getMediaPlayer();
        changeListener = new AudioManager.OnAudioFocusChangeListener() {
            @Override
            public void onAudioFocusChange(int focusChange) {
                switch (focusChange){
                    case AudioManager.AUDIOFOCUS_GAIN:
                        mMediaPlayer.start();
                        mAudioManager.adjustVolume(AudioManager.ADJUST_UNMUTE,AudioManager.FLAG_VIBRATE);
                        break;
                    case AudioManager.AUDIOFOCUS_GAIN_TRANSIENT:
                        mMediaPlayer.start();
                        mAudioManager.adjustVolume(AudioManager.ADJUST_UNMUTE,AudioManager.FLAG_VIBRATE);
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                        mAudioManager.adjustVolume(AudioManager.ADJUST_MUTE,AudioManager.FLAG_VIBRATE);
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                        mMediaPlayer.pause();
                        break;
                    case AudioManager.AUDIOFOCUS_LOSS:
                        mMediaPlayer.stop();
                        releaseMediaPlayer();
                        break;
                }
            }
        };
    }

    public void getMediaPlayer(){
        if(mMediaPlayer == null) {
            Uri alarm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            mMediaPlayer = MediaPlayer.create(context, alarm);
        }
    }

    public void playAlarm(){
        getMediaPlayer();
            if(requestAudioFocus(changeListener,AudioManager.STREAM_ALARM,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)) {
                mMediaPlayer.setLooping(true);
                mMediaPlayer.start();
            }
//            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//                @Override
//                public void onCompletion(MediaPlayer mp) {
//                    releaseMediaPlayer();
//                }
//            });


    }

    public void getManager() {
        if (mAudioManager == null) {
            mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        }
    }

    public void releaseMediaPlayer(){
        mMediaPlayer.release();
        releaseAudioFocus(changeListener,AudioManager.STREAM_ALARM,AudioManager.AUDIOFOCUS_LOSS);
    }

    public boolean requestAudioFocus(AudioManager.OnAudioFocusChangeListener focusChangeListener,int streamType,int audioFocusGain){
        int r;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            r = mAudioManager.requestAudioFocus(new AudioFocusRequest.Builder(audioFocusGain)
                    .setAudioAttributes(
                            new AudioAttributes.Builder()
                                    .setLegacyStreamType(streamType)
                                    .build())
                    .setOnAudioFocusChangeListener(focusChangeListener)
                    .build());

        }
        else {
            r = mAudioManager.requestAudioFocus(focusChangeListener,streamType,audioFocusGain);
        }

        return (r == AudioManager.AUDIOFOCUS_REQUEST_GRANTED);
    }

    public void releaseAudioFocus(AudioManager.OnAudioFocusChangeListener focusChangeListener,int steamType,int audioFocusGain){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            mAudioManager.abandonAudioFocusRequest(new AudioFocusRequest.Builder(audioFocusGain)
                    .setAudioAttributes(
                            new AudioAttributes.Builder()
                                    .setLegacyStreamType(steamType)
                                    .build())
                    .setOnAudioFocusChangeListener(changeListener)
                    .build()
            );
        }
        else {
            mAudioManager.abandonAudioFocus(changeListener);
        }
    }

}
