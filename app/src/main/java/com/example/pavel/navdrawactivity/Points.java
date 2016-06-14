package com.example.pavel.navdrawactivity;

import android.net.wifi.ScanResult;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Pavel on 12.06.2016.
 */
public class Points implements Parcelable {
    public List<ScanResult> getScanResults() {
        return scanResults;
    }

    public void setScanResults(List<ScanResult> scanResults) {
        this.scanResults = scanResults;
    }

    private List<ScanResult> scanResults;

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    private String pointName;
    private int count;
    private int x;
    private int y;

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(pointName);
        parcel.writeList(scanResults);
        parcel.writeInt(x);
        parcel.writeInt(y);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    private Points(Parcel parcel) {
        pointName = parcel.readString();
        scanResults = parcel.readArrayList(ClassLoader.getSystemClassLoader());
        x = parcel.readInt();
        y = parcel.readInt();
    }

    public Points(String s, List<ScanResult> list, int x, int y) {
        this.pointName = s;
        this.x = x;
        this.y = y;
        scanResults = list;
        Collections.sort(list, new Comparator<ScanResult>() {
            @Override
            public int compare(ScanResult scanResult, ScanResult t1) {
                if (scanResult.level >= t1.level) return -1;
                else return 1;
            }
        });

        this.count = list.size();
    }

    public Points(String s, int x, int y) {
        this.pointName = s;
        this.x = x;
        this.y = y;
        scanResults = new ArrayList<>();
    }


    public void SetCount(){
        count = scanResults.size();
    }

    private boolean compareLevel(int pointLevel, int scanLevel) {
        return (scanLevel > pointLevel - 10 && scanLevel < pointLevel + 10);
    }

    private void addnewhotspot(List<ScanResult> list) {
        for (ScanResult s : list) {
            boolean flag = true;
            for (ScanResult l : scanResults) {
                if (s.BSSID.equals(l.BSSID)) flag = false;
            }
            if (flag) {
                scanResults.add(s);
                flag = true;
            }
        }
    }

    @Override
    public String toString() {
        return pointName;
    }

    public boolean Compare(List<ScanResult> list) {
        int res = 0;
        for (ScanResult s : list) {
            for (ScanResult l : scanResults) {
                if (l.BSSID.equals(s.BSSID) && compareLevel(l.level, s.level)) {
                    res++;
                    break;
                }
            }
        }
        if (res >= count)
            return true;
        else
            return false;
    }


    public static final Parcelable.Creator<Points> CREATOR = new Parcelable.Creator<Points>() {
        public Points createFromParcel(Parcel in) {
            return new Points(in);
        }

        public Points[] newArray(int size) {
            return new Points[size];
        }
    };
}
