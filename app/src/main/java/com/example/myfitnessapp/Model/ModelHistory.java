package com.example.myfitnessapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
public class ModelHistory
{


    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "title")
    private  String title;

    @ColumnInfo(name = "pose")
    private  String pose;

    @ColumnInfo(name = "image")
    private  String image;

    @ColumnInfo(name = "desc")
    private  String desc;

    @ColumnInfo(name = "timeTaken")
    private  String timeTaken;








    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPose() {
        return pose;
    }

    public void setPose(String pose) {
        this.pose = pose;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(String timeTaken) {
        this.timeTaken = timeTaken;
    }

    public String getDatecompleted() {
        return datecompleted;
    }

    public void setDatecompleted(String datecompleted) {
        this.datecompleted = datecompleted;
    }

    @ColumnInfo(name = "datecompleted")
    private  String datecompleted;



}
