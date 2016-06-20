package com.example.pavel.navdrawactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


import java.util.ArrayList;
import java.util.List;

public class DrawingActivity extends AppCompatActivity implements OnTouchListener {

    DrawActivity drawActivity;
    private WifiManager manager;
    private List<ScanResult> scanResultList;
    public ArrayList<Points> pointList;
    private Points p = null;
    public static int time = 1000;



    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (manager.getScanResults() != null) {
                scanResultList = manager.getScanResults();
            }
        }
    };
    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_ENABLING:
                   // text.setText("Wi-Fi state enabling");
                    break;
                case WifiManager.WIFI_STATE_ENABLED:
                    //text.setText("Wi-Fi state enabled");
                    startMonitoringRssi();
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    //text.setText("Wi-Fi state disabling");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                   // text.setText("Wi-Fi state disabled");
                    break;
                case WifiManager.WIFI_STATE_UNKNOWN:
                    //text.setText("Wi-Fi state unknown");
                    break;
            }
        }
    };

    public void startMonitoringRssi() {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    manager.startScan();
                    handler.sendMessage(handler.obtainMessage());
                }
            }
        });
        t.start();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawActivity = new DrawActivity(this);
        setContentView(drawActivity);
        drawActivity.setOnTouchListener(this);
        this.registerReceiver(this.receiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
        manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        manager.setWifiEnabled(true);
    }

    public void openActivity(View v) {
        Intent intent = new Intent(DrawingActivity.this, CreatePoint.class);
        intent.putExtra("point",p);
        startActivityForResult(intent, 0);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            p.setPointName(data.getStringExtra("name"));
            p.SetCount();
            DrawActivity.getPoints().add(p);
            drawActivity.drawCircle(p.getX(), p.getY());
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            p = new Points("name",manager.getScanResults(),(int)x,(int)y);
            openActivity(v);
        }
        return true;

    }


}
