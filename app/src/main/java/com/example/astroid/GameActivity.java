package com.example.astroid;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    DrawSurfaceView ds;
    Thread thread;
    FrameLayout frame;
    TextView timerview;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        frame = findViewById(R.id.frame);
        ds = new DrawSurfaceView(this);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.badaddrill);
        mediaPlayer.start();
        frame.addView(ds);
        thread = new Thread(ds);
        thread.start();
        timerview=findViewById(R.id.timerview);
        CountUpTimer timer= new CountUpTimer(300000) {
        @Override
        public void onTick(int second) {
            timerview.setText(String.valueOf(second));
        }
    };
    timer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
        mediaPlayer.release();

    }


    public void pauseResume(View view)
    {
        ds.isRunning=false;
        mediaPlayer.pause();
        //mediaPlayer.release();
    }

    public void playResume(View view)
    {
       ds.isRunning=true;
        mediaPlayer.start();
    }
    public void move(View view)
    {
      if (view==findViewById(R.id.btnD))
      {
          ds.moveD();
      }
        if (view==findViewById(R.id.btnU))
        {
            ds.moveU();
        }
        if (view==findViewById(R.id.btnR))
        {
            ds.moveR();
        } if (view==findViewById(R.id.btnL))
    {
        ds.moveL();
    }
    }
    public  abstract class CountUpTimer extends CountDownTimer
    {
        private static final long INTERVAL_MS=1000;
        private final long duration;

        protected CountUpTimer(long durationMs){
            super(durationMs,INTERVAL_MS);
            this.duration=durationMs;
        }

        public abstract void onTick(int second);
        @Override
        public void onTick(long msUntilFinished){
            int second =(int)((duration-msUntilFinished)/1000);
            onTick(second);
        }
        @Override
        public void  onFinish(){
            onTick(duration/1000);
        }
    }

}