package com.example.myfitnessapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myfitnessapp.Database.AppDatabase;
import com.example.myfitnessapp.MainActivity;
import com.example.myfitnessapp.Model.ModelReminder;
import com.example.myfitnessapp.R;

public class ShowReminder extends AppCompatActivity implements View.OnClickListener {


    Button btnStop;
    TextView title;
    MediaPlayer mp ;
    boolean isDelted=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reminder);
        if(getSupportActionBar()!=null)
        {

            getSupportActionBar().setTitle("Reminder");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        init();
    }

    private void init()
    {
        btnStop=findViewById(R.id.btnStop);
        title=findViewById(R.id.txtTitle);
        title.setText(getIntent().getExtras().getString("Title"));

        btnStop.setOnClickListener(this);
        mp = MediaPlayer.create(this,R.raw.alarm);
        mp.start();

    }

    @Override
    public void onBackPressed() {
        if(mp!=null && mp.isPlaying())
            mp.stop();

        if(!isDelted)
        {
            deletefromdatabase();
            return;
        }
        ActivityCompat.finishAffinity(this);
        startActivity(new Intent(this, MainActivity.class));
        super.onBackPressed();
    }

    private void deletefromdatabase() {


        if(isDelted)
            onBackPressed();
        int uid = getIntent().getExtras().getInt("UID");
        final ModelReminder obj=new ModelReminder();
        obj.setUid(uid);
        final AppDatabase DB = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "history").build();
        new Thread()
        {
            @Override
            public void run() {
                DB.reminderDao().delete(obj);
                isDelted=true;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
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

    @Override
    public void onClick(View v)
    {

        if(v==btnStop)
        {
            if(mp!=null && mp.isPlaying())
                mp.stop();
            onBackPressed();
        }

    }
}
