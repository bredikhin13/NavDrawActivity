package com.example.pavel.navdrawactivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {


    private EditText editText;
    private EditText editText2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(String.valueOf(Points.range));
        editText2 = (EditText) findViewById(R.id.editText2);
        editText2.setText(String.valueOf(DrawingActivity.time));
    }

    public void onSaveClick(View view) {
        Points.range = Integer.valueOf(editText.getText().toString());
        DrawingActivity.time = Integer.valueOf(editText2.getText().toString());
        finish();
    }
}
