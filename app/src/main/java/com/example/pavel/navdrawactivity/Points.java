package com.example.pavel.navdrawactivity;

/**
 * Created by Pavel on 12.06.2016.
 */
public class Points {
    private float X;
    private float Y;
    private String name;

    public Points(float x, float y){
        this.X = x;
        this.Y = y;
        this.name = "axaxa";
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
