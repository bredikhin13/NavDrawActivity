package com.example.pavel.navdrawactivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class CreatePoint extends AppCompatActivity {
    private Points points;
    private TextView textView;
    private EditText editText;
    private WifiManager manager;
    private List<ScanResult> scanResultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        points = intent.getParcelableExtra("point");
        setContentView(R.layout.activity_create_point);
        textView = (TextView) findViewById(R.id.textViewPoints);
        editText = (EditText) findViewById(R.id.editName);
        this.registerReceiver(this.receiver, new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION));
        manager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        manager.setWifiEnabled(true);
        points.setScanResults(manager.getScanResults());
        textView.setMovementMethod(new ScrollingMovementMethod());
        print();
    }
    private void print(){
        points.setScanResults(manager.getScanResults());
        textView.setText("");
        textView.append("x="+points.getX()+"\n");
        textView.append("y="+points.getY()+"\n");
        textView.append("Count routers="+points.getScanResults().size()+"\n");
        for (ScanResult result:points.getScanResults()){
            if(result.level>=-80) {
                textView.append("SSID="+result.SSID+"\n\tMac="+result.BSSID+"\n\tlevel="+result.level+"\n");
            }

        }
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (manager.getScanResults() != null) {
                scanResultList = manager.getScanResults();
                print();
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

    public void onOkClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("name",editText.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }

    public void onCancelClick(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }
}
