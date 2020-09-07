package com.hyunkwak.watacook.auxiliaryClasses;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class MyParcelable implements Parcelable {

    public static final Creator<MyParcelable> CREATOR = new Creator<MyParcelable>() {
        @Override
        public MyParcelable createFromParcel(Parcel in) {
            return new MyParcelable(in);
        }

        @Override
        public MyParcelable[] newArray(int size) {
            return new MyParcelable[size];
        }
    };
    private ArrayList<Recipe> arrList = new ArrayList<Recipe>();
    private int myInt = 0;
    private String str = null;

    public MyParcelable() {
        arrList = new ArrayList<Recipe>();
    }

    public MyParcelable(Parcel in) {
        myInt = in.readInt();
        str = in.readString();
        in.readTypedList(arrList, Recipe.CREATOR);
    }

    public ArrayList<Recipe> getArrList() {
        return arrList;
    }

    public void setArrList(ArrayList<Recipe> arrList) {
        this.arrList = arrList;
    }

    public int getMyInt() {
        return myInt;
    }

    public void setMyInt(int myInt) {
        this.myInt = myInt;
    }

    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(myInt);
        parcel.writeString(str);
        parcel.writeTypedList(arrList);
    }
}
