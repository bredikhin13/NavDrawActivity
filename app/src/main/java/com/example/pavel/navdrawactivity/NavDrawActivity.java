package com.example.pavel.navdrawactivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pavel on 14.06.2016.
 */
public class NavDrawActivity extends View {
    public NavDrawActivity(Context context){
        super(context);
        bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + foldername + "/" + filename);
    }
    private Bitmap bitmap;
    private Paint mCirclePaint = new Paint();
    private String foldername = "/testfolder/myapp";
    private String filename = "img.jpg";
    private int x;
    private int y;
    private boolean isPointExist = false;
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(bitmap,null,canvas.getClipBounds(),null);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStyle(Paint.Style.FILL);
        if(isPointExist) {
            canvas.drawCircle(x, y, 25, mCirclePaint);
        }
    }
    public void drawCircle(int x, int y){
        this.x = x;
        this.y = y;
        this.isPointExist = true;
        this.invalidate();
    }

    public void clearMap(){
        this.isPointExist = false;
        invalidate();
    }
}
