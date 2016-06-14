package com.example.pavel.navdrawactivity;

import android.content.Intent;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Xml;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_manage) {
            Intent intent = new Intent(MainActivity.this, DrawingActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_web){
            Intent intent = new Intent(MainActivity.this, web_view.class);
            startActivity(intent);
        } else if (id == R.id.nav_navigate){
            Intent intent = new Intent(MainActivity.this, NavActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View view) {
        XmlSerializer serializer = Xml.newSerializer();
        String foldername = "/testfolder/myapp";
        String filename = "text.xml";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getPath() + foldername + "/" + filename;
            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
                FileOutputStream stream = new FileOutputStream(file);
                OutputStreamWriter writer = new OutputStreamWriter(stream);
                serializer.setOutput(writer);
                serializer.startDocument("UTF-8", true);
                serializer.startTag("", "data");
                for (Points points : DrawActivity.getPoints()) {
                    serializer.startTag("", "point");
                    serializer.attribute("", "name", String.valueOf(points.getPointName()));
                    serializer.attribute("", "x", String.valueOf(points.getX()));
                    serializer.attribute("", "y", String.valueOf(points.getY()));
                    for(ScanResult result:points.getScanResults()){
                        serializer.startTag("","router");
                        serializer.attribute("","ssid",result.SSID);
                        serializer.attribute("","bssid",result.BSSID);
                        serializer.attribute("","level",String.valueOf(result.level));
                        serializer.endTag("","router");
                    }
                    serializer.endTag("", "point");
                }
                serializer.endTag("", "data");
                serializer.endDocument();
                //writer.write("content");
                writer.close();
                stream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void onReadClick(View view) {
        DrawActivity.getPoints().clear();
        String foldername = "/testfolder/myapp";
        String filename = "text.xml";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getPath() + foldername + "/" + filename;
            File file = new File(path);
            Points p = null;
            try {
                FileInputStream stream = new FileInputStream(file);
                //BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
                XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
                xmlPullParserFactory.setNamespaceAware(true);
                XmlPullParser parser = xmlPullParserFactory.newPullParser();
                parser.setInput(stream, "UTF-8");
                while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                    switch (parser.getEventType()) {
                        case XmlPullParser.START_DOCUMENT:
                            break;
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("point")) {
                                p = new Points(parser.getAttributeValue("","name"),Integer.valueOf(parser.getAttributeValue("", "x")), Integer.valueOf(parser.getAttributeValue("", "y")));
                            } else if(parser.getName().equals("router")){
                                ScanResult scanResult = ScanResult.class.newInstance();
                                scanResult.SSID =parser.getAttributeValue("","ssid");
                                scanResult.BSSID =parser.getAttributeValue("","bssid");
                                scanResult.level =Integer.valueOf(parser.getAttributeValue("","level"));
                                if(p!=null) {
                                    p.getScanResults().add(scanResult);
                                }
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("point")) {
                                DrawActivity.getPoints().add(p);
                            }
                            break;
                        case XmlPullParser.TEXT:
                            break;
                    }
                    parser.next();
                }
                //String string = "";
                //string = reader.readLine();
                //reader.close();
                //TextView textView = (TextView) findViewById(R.id.textView2);
                //textView.append(" " + string);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
