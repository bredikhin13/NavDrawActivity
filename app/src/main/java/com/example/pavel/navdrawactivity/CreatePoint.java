package com.example.pavel.navdrawactivity;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class CreatePoint extends AppCompatActivity {
    Points points;
    TextView textView;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        points = intent.getParcelableExtra("point");
        setContentView(R.layout.activity_create_point);
        textView = (TextView) findViewById(R.id.textViewPoints);
        editText = (EditText) findViewById(R.id.editName);
        print();
    }
    private void print(){
        textView.setText("");
        textView.append("x="+points.getX()+"\n");
        textView.append("x="+points.getX()+"\n");
        for (ScanResult result:points.getScanResults()){
            textView.append("SSID="+result.SSID+" Mac="+result.BSSID+" level="+result.level+"\n");
        }
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
