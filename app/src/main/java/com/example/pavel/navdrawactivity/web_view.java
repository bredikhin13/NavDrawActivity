package com.example.pavel.navdrawactivity;

import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.webkit.WebView;

public class web_view extends AppCompatActivity /*implements OnTouchListener*/ {

    WebView webView;
    String foldername = "/testfolder/myapp";
    String filename = "img.jpg";
    DrawActivity drawActivity;
    private boolean isPotentialLongPress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_web_view);
        //webView = (WebView) findViewById(R.id.webView);
        webView = new WebView(this);
        //webView.setOnTouchListener(this);
        setContentView(webView);
        kek();
    }

//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        float x = event.getX();
//        float y = event.getY();
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            super.onTouchEvent(event);
//            //Log.d("aaa",x+"/"+y);;
//        }
//        return true;
//    }

        @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        handleLongPress(event);
        return super.dispatchTouchEvent(event);
    }

    private void handleLongPress(MotionEvent event) {
        final MotionEvent e = event;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            new Thread(new Runnable() {
                public void run() {
                    Looper.prepare();
                    if (isLongPressDetected()) {
                        float x = e.getX();
                        float y = e.getY();
                        Log.d("aaa",x+"/"+y);
                    }
                }
            }).start();
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (event.getHistorySize() < 1)
                return;

            float diffX = event.getX()
                    - event.getHistoricalX(event.getHistorySize() - 1);
            float diffY = event.getY()
                    - event.getHistoricalY(event.getHistorySize() - 1);

            if (Math.abs(diffX) > 0.5f || Math.abs(diffY) > 0.5f) {
                isPotentialLongPress = false;
            }
        } else {
            isPotentialLongPress = false;
        }
    }


    public boolean isLongPressDetected() {
        isPotentialLongPress = true;
        try {
            for (int i = 0; i < (50); i++) {
                Thread.sleep(10);
                if (!isPotentialLongPress) {
                    return false;
                }
            }
            return true;
        } catch (InterruptedException e) {
            return false;
        } finally {
            isPotentialLongPress = false;
        }
    }



    private void kek() {
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.setScrollbarFadingEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        String path = "file://" + Environment.getExternalStorageDirectory().getPath() + foldername + "/" + filename;
        webView.loadUrl(path);
    }
}
