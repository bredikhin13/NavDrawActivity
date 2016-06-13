package com.example.pavel.navdrawactivity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;

public class DrawingActivity extends AppCompatActivity implements OnTouchListener {

    DrawActivity drawActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawActivity = new DrawActivity(this);
        setContentView(drawActivity);
        drawActivity.setOnTouchListener(this);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            drawActivity.drawCircle(x, y);
        }
        return true;

    }


}
