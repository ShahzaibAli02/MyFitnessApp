package com.example.myfitnessapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ModelReminder
{

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    private  String title;

    @ColumnInfo(name = "date")
    private  String date;




    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRequestcode() {
        return requestcode;
    }

    public void setRequestcode(int requestcode) {
        this.requestcode = requestcode;
    }

    @ColumnInfo(name = "requestcode")
    private  int requestcode;







}
