package com.example.huydq17.alertsimple;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;

import java.io.IOException;

/**
 * Created by HuyDQ17 on 10/3/2015.
 */
public class AlertMusic extends Service {

    private MediaPlayer mediaPlayer;

    public AlertMusic() {
         mediaPlayer = new MediaPlayer();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
     //   mediaPlayer.setDataSource("// Source");
        try {
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }
}
