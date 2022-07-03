package com.example.astroid;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by asafamir on 19/05/2017.
 */

public class DrawSurfaceView extends SurfaceView implements Runnable {
    int interval=50;//try to change it
    float dx=10;
    float dy=10;
    Context context;
    SurfaceHolder holder;
    boolean threadRunning = true;
    boolean isRunning = true;
    Bitmap bitmap;
    MediaPlayer  mediaPlayer;
    float x =100;
    float y =100;

    public DrawSurfaceView(Context context)
    {
        super(context);
        this.context = context;
        holder = getHolder();
        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.zhoar2);
    }

    @Override
    public void run() {
        while (threadRunning)
        {
            if(isRunning)
            {
               if(!holder.getSurface().isValid())
                   continue;

                Canvas c = null;
                try
                {
                    c = this.getHolder().lockCanvas();//what with line meaning?
                    synchronized (this.getHolder()){
                        c.drawRGB(100,100,255);//Try pushing this line into a remark. what happened? you can change the color.
                        c.drawBitmap(bitmap,x,y,null);
                        automaticMove();
                    }
                    Thread.sleep(interval);
                }
                catch (Exception e)
                {

                }
                /*what is finally?
                */
                finally {
                    if(c!=null)
                    {
                        this.getHolder().unlockCanvasAndPost(c);//what this line meaning?
                    }
                }
            }
        }
    }

    /*
    how it works?
     */
    public void automaticMove()
    {
        x = x + dx;
        y = y + dy;
        if(x < 0 || x > this.getWidth()-300){
            dx = -dx;
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.oofsound);
            mediaPlayer.start();

        }
        if(y < 0 || y > this.getHeight()-300){
            mediaPlayer = MediaPlayer.create(context.getApplicationContext(), R.raw.oofsound);
        mediaPlayer.start();
        dy = -dy;
    }


    }
    public void moveD()
    {
        dy=0;
       dy=dy+10;
    }
    public void moveU()
    {
        dy=0;
       dy=dy-10;
    }
    public void moveR()
    {
        dx=0;
       dx=dx+10;
        Matrix matrix = new Matrix();
        matrix.preScale(-1,1);
        bitmap= Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    public void moveL()
    {
        dx=0;
       dx=dx-10;
        Matrix matrix = new Matrix();
        matrix.preScale(-1,1);
        bitmap= Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }
    @Override
    public boolean onTouchEvent (MotionEvent event)
    {

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            //if (event.getX() == x && event.getY() == y){
            x = event.getX();
            y = event.getY();
            invalidate();
            //}
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            // if (event.getX() == x && event.getY() == y){
            x = event.getX();
            y = event.getY();
            invalidate();
            // }
        }
        //}
        return true;
    }
}
