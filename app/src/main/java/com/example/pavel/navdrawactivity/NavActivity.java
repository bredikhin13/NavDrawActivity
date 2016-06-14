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

import java.util.ArrayList;
import java.util.List;

public class NavActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.registerReceiver(this.receiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
        manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        drawActivity = new NavDrawActivity(this);
        setContentView(drawActivity);

    }

    private NavDrawActivity drawActivity;
    private WifiManager manager;
    private List<ScanResult> scanResultList;


    public void searchPoint(List<ScanResult> list) {
        boolean flag = false;
        for (Points p : DrawActivity.getPoints()) {
            if(p.Compare(list)){
                    drawActivity.drawCircle(p.getX(),p.getY());
                    flag = true;
                break;
            }
        }
        if(!flag){
            drawActivity.clearMap();
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (manager.getScanResults() != null) {
                scanResultList = manager.getScanResults();
                searchPoint(scanResultList);
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
                        Thread.sleep(1000);
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






}
