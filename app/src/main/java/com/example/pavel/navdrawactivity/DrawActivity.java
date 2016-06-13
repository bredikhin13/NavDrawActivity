package com.example.pavel.navdrawactivity;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;

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
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
        canvas.drawPaint(mPaint);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.YELLOW);
        mCirclePaint.setStyle(Paint.Style.FILL);
        for(Points p:points){
            canvas.drawCircle(p.getX(),p.getY(),10,mCirclePaint);
        }
    }
    public void drawCircle(float x, float y){
        points.add(new Points((int)x,(int)y));
        this.invalidate();
    }

    public static List<Points> getPoints() {
        return points;
    }
}
