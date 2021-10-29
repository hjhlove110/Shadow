package com.dresses.library.utils;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

/**
 * @author HUAN
 * @date
 */
public class PlayVoiceManager {
    private SoundPool soundPool;
    private boolean loop = false;

    private MediaPlayer mediaPlayer;
    private float musicVolume = 1f;
    private float charVolume = 1f;

    private PlayVoiceManager() {
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundPool.setOnLoadCompleteListener((soundPool, sampleId, status) -> {
            soundPool.play(sampleId, charVolume, charVolume, 1, loop ? -1 : 0, 1f);//播放
            soundPool.setVolume(sampleId, charVolume, charVolume);
        });

        if (null == mediaPlayer) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setOnPreparedListener(mp -> {
                mp.setVolume(musicVolume, musicVolume);
                mp.start();
            });
        }
    }

    private static PlayVoiceManager instance;

    public static PlayVoiceManager getInstance() {
        if (instance == null) {
            instance = new PlayVoiceManager();

        }
        return instance;
    }

    public void setMusicVolume(float volume) {
        this.musicVolume = volume;
        mediaPlayer.setVolume(volume, volume);
    }

    public void setCharVolume(float charVolume) {
        this.charVolume = charVolume;
    }

    public void playBackgroundMusic(String voicePath, boolean isLoop) {
        try {
            mediaPlayer.reset();
            mediaPlayer.setDataSource(voicePath);
            mediaPlayer.setLooping(isLoop);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            mediaPlayer.start();
        }
    }
    public MediaPlayer playVoice(int resId, boolean isLoop,Context context){
        try {
            AssetFileDescriptor afd = context.getResources().openRawResourceFd(resId);
            if (afd == null) return mediaPlayer;
            mediaPlayer.reset();
            final AudioAttributes aa = new AudioAttributes.Builder().build();
//            mediaPlayer.setAudioAttributes(aa);
            mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
            mediaPlayer.setLooping(isLoop);
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            mediaPlayer.start();
            return mediaPlayer;
        }
        return mediaPlayer;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void stop() {
        try {
            mediaPlayer.pause();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
