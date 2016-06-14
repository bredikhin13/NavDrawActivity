package com.example.pavel.navdrawactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 09.06.2016.
 */
public class DrawActivity extends View {
    public DrawActivity(Context context){
        super(context);
    }
    private static List<Points> points = new ArrayList<>();
    private Paint mPaint = new Paint();
    private Paint mCirclePaint = new Paint();
    String foldername = "/testfolder/myapp";
    String filename = "img.jpg";
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //File file = new File(Environment.getExternalStorageDirectory().getPath() + foldername + "/" + filename);
        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + foldername + "/" + filename);
        mPaint.setStyle(Paint.Style.FILL);
        //mPaint.setColor(Color.GREEN);
        //canvas.drawPaint(mPaint);

        canvas.drawBitmap(bitmap,null,canvas.getClipBounds(),null);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.BLUE);
        mCirclePaint.setStyle(Paint.Style.FILL);
        for(Points p:points){
            canvas.drawCircle(p.getX(),p.getY(),10,mCirclePaint);
        }
    }
    public void drawCircle(float x, float y){
        //points.add(new Points((int)x,(int)y));
        this.invalidate();
    }

    public static List<Points> getPoints() {
        return points;
    }
}
