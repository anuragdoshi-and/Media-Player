package com.example.mediaplayer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class play extends AppCompatActivity {
    MediaPlayer player;
    SeekBar seekBar;
    Runnable runnable;
    Handler handler;
    TextView textView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        // Intent

        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        assert bundle != null;
        ArrayList allSongs = (ArrayList) bundle.getParcelableArrayList("sNames");
        int position = bundle.getInt("pos",0);

        //end Intent

        //get song
         Uri u = Uri.parse(allSongs.get(position).toString());


         // start song
        /*
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
        );
            mediaPlayer.setDataSource(getApplicationContext(), u);
            e.printStackTrace();
            mediaPlayer.prepare();
        mediaPlayer.start();


        

        player = MediaPlayer.create(getApplicationContext(), u);
        player.start();

        /*

        seekBar.setMax(player.getDuration());
        playCycle();

         */



        //

        handler = new Handler();

        seekBar = (SeekBar) findViewById(R.id.s1);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                player.seekTo(progress);
                seekBar.setProgress(progress);


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    public void playCycle() {
        seekBar.setProgress(player.getCurrentPosition());
        runnable = new Runnable() {
            @Override
            public void run() {
                playCycle();
            }
        };
        handler.postDelayed(runnable, 1500);
    }

/*
    public void play(View v) {
        if (player == null) {
            player = MediaPlayer.create(
            double duration = player.getDuration();
            duration = (duration / 1000) / 60;
            textView.setText("" + duration + "mins");
            //   player.setOnCompletionListner(new On)
        }
        player.start();
        seekBar.setMax(player.getDuration());
        playCycle();
    }
*/


    public void pause(View v) {
        if (player != null) {
            player.pause();
        }

    }

    public void stop(View v) {
        stopPlayer();

    }

    private void stopPlayer() {
        if (player != null) {
            player.release();
            player = null;
            Toast.makeText(this, "Media Released", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        stopPlayer();
    }
}