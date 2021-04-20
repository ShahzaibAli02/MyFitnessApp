package com.example.myfitnessapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.myfitnessapp.Database.AppDatabase;
import com.example.myfitnessapp.Model.ModelReminder;
import com.example.myfitnessapp.R;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class AddReminder extends AppCompatActivity implements View.OnClickListener {



    Button save;
    EditText title;
    int hour,minute=-1;
    int day,month,year;
    LinearLayout layoutDate,layoutTime;

    boolean isDateSelected=false;
    boolean isTimeSelected=false;
    TextView txtDate,txtTime;
    final Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        init();
    }

    private void init()
    {


        save=findViewById(R.id.btnSaveReminder);
        title=findViewById(R.id.edittextTitle);
        txtDate=findViewById(R.id.txtDate);
        txtTime=findViewById(R.id.txtTime);
        layoutDate=findViewById(R.id.layoutDate);
        layoutTime=findViewById(R.id.layoutTime);

        layoutTime.setOnClickListener(this);
        layoutDate.setOnClickListener(this);
        save.setOnClickListener(this);

        if(getSupportActionBar()!=null)
        {

            getSupportActionBar().setTitle("Add Reminder");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public  void showDatePicker()
    {


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker view, int YEAR, int MONTH, int DAY) {
                year=YEAR;
                month=MONTH;
                day=DAY;
                txtDate.setText(String.format("%d/%d/%d", day, month + 1, year));
                isDateSelected=true;
            }

        };
        new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    public  void showTimePicker() {

        final Calendar myCalendar = Calendar.getInstance();
        final TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minuteOfHour) {
                hour = hourOfDay;
                minute = minuteOfHour;
                txtTime.setText(hour+" : "+minute+" : 00" );
                isTimeSelected = true;
            }
        };


        new TimePickerDialog(this, (TimePickerDialog.OnTimeSetListener) time, myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE), false).show();


    }

    @Override
    public void onClick(View v)
    {

        if(v==save)
        {


            title.setError(null);

            if(!TextUtils.isEmpty(title.getText()))
            {
                if(!isDateSelected || !isTimeSelected)
                {
                    Toast.makeText(this,"Select  Date AndTime",Toast.LENGTH_LONG).show();
                }
                else
                {
                    setAlarm(hour,minute);
                }

            }
            else
            {
                title.setError("Required Field");
                title.requestFocus();
            }
        }


        if(v==layoutDate)
        {
            showDatePicker();
        }
        if(v==layoutTime)
        {
            showTimePicker();
        }


    }

    public  void setAlarm(int hourofday,int minute)
    {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Date dat = new Date();
        Calendar timeToCall = Calendar.getInstance();
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(dat);
        timeToCall.setTime(dat);
        timeToCall.set(Calendar.HOUR_OF_DAY,hourofday);
        timeToCall.set(Calendar.MINUTE,minute);
        timeToCall.set(Calendar.DAY_OF_MONTH,day);
        timeToCall.set(Calendar.YEAR,year);
        timeToCall.set(Calendar.MONTH,month);
        timeToCall.set(Calendar.SECOND,0);
        if(timeToCall.before(currentTime))
        {
           Toast.makeText(AddReminder.this,"Selected Time And Date Is In Past",Toast.LENGTH_LONG).show();
           return;
        }
        int RequestCode=new Random().nextInt(100);
        Intent myIntent = new Intent(this, ShowReminder.class);
        myIntent.putExtra("Title",title.getText().toString());
        myIntent.putExtra("UID",RequestCode);
        PendingIntent alarmPendingIntent = PendingIntent.getActivity(this, RequestCode,myIntent,0 );
        manager.set(AlarmManager.RTC_WAKEUP,timeToCall.getTimeInMillis(), alarmPendingIntent);


        SaveInDatabae(RequestCode);
    }

    private void SaveInDatabae(int requestCode)
    {


       final AppDatabase DB = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "history").build();
        final ModelReminder obj=new ModelReminder();
        obj.setDate("Date  : " +txtDate.getText().toString() +" \n TIME   :   "+txtTime.getText().toString());

        obj.setRequestcode(requestCode);
        obj.setTitle(title.getText().toString());
        obj.setUid(requestCode);
       new Thread()
       {
           @Override
           public void run() {
               DB.reminderDao().insertAll(obj);
               runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(AddReminder.this,"Reminder Saved",Toast.LENGTH_LONG).show();
                   }
               });
           }
       }.start();


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
