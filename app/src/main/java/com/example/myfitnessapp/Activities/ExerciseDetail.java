package com.example.myfitnessapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfitnessapp.Constants;
import com.example.myfitnessapp.Database.AppDatabase;
import com.example.myfitnessapp.Model.ModelHistory;
import com.example.myfitnessapp.R;
import com.squareup.picasso.Picasso;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class ExerciseDetail extends AppCompatActivity implements View.OnClickListener {



    ImageView music;
    MediaPlayer mp ;
    boolean isPlaying=false;
    ImageView imgthumbnail,imgcomplete;
    String Title;
    String PoseName;
    String Desc;
    String ImageLink;
    TextView txtDetail,txtTimer;
    Handler timerHandler = new Handler();
    Runnable timerRunnable;
    long time=0;
    AppDatabase DB;
    Boolean isActivityRunning=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_detail);
        mp = MediaPlayer.create(this,R.raw.music);
        getExtras() ;
        init();
        startTimer();
        checkIfAlreadyCompleted();
    }

    private void checkIfAlreadyCompleted()
    {


    new Thread()
    {
        @Override
        public void run() {
            ModelHistory byTitle = DB.historyDao().findByTitle(Title);

            if(byTitle!=null && isActivityRunning)
            {
                imgcomplete.setVisibility(View.GONE);
            }
        }
    }.start();



    }

    private void startTimer()
    {


        time=0;
        timerRunnable = new Runnable()
        {
            @Override
            public void run()
            {
                time+=1000;
                txtTimer.setText("Timer  : "+FormateTime(time));
                timerHandler.postDelayed(this, 1000);
            }
        };
        timerRunnable.run();

    }

    private void getExtras()
    {

        Bundle extras = getIntent().getExtras();
        Title=extras.getString(Constants.TITLE);
        Desc=extras.getString(Constants.DESC);
        PoseName=extras.getString(Constants.POSE);
        ImageLink=extras.getString(Constants.IMAGE);




    }
    private String FormateTime(long time)
    {
        @SuppressLint("DefaultLocale")
        String format = String.format("%02d:%02d:%02d",
                TimeUnit.MILLISECONDS.toHours(time),
                TimeUnit.MILLISECONDS.toMinutes(time) -
                        TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time)), // The change is in this line
                TimeUnit.MILLISECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time)));

        return  format;
    }

    private void init()
    {


        DB = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "history").build();
        music=findViewById(R.id.music);
        txtTimer=findViewById(R.id.txtTimer);
        txtDetail=findViewById(R.id.txtDetail);
        imgthumbnail=findViewById(R.id.imgthumbnail);
        imgcomplete=findViewById(R.id.imgcomplete);

        if(getSupportActionBar()!=null)
        {

            getSupportActionBar().setTitle(Title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        txtDetail.setText(Desc);
        music.setOnClickListener(this);
        imgcomplete.setOnClickListener(this);
        Picasso.get().load(ImageLink).placeholder(getResources().getDrawable(R.drawable.img_placeholder)).into(imgthumbnail);

        music.callOnClick();
    }
    public  String getDate()
    {


        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        return df.format(Calendar.getInstance().getTime());
    }

    @Override
    public void onClick(View v)
    {

        if(v==music)
        {

            if(isPlaying)
            {
                if(mp!=null && mp.isPlaying())
                    mp.pause();

                isPlaying=false;

                music.setBackground(getResources().getDrawable(R.drawable.ic_music_play));

            }
            else
            {
                isPlaying=true;
                mp.start();
                music.setBackground(getResources().getDrawable(R.drawable.ic_music_pause));
            }

        }



        if(v==imgcomplete)
        {



            String timeTaken = FormateTime(time);
            final ModelHistory obj=new ModelHistory();
            obj.setTitle(Title);
            obj.setPose(PoseName);
            obj.setImage(ImageLink);
            obj.setTimeTaken(timeTaken);
            obj.setDesc(Desc);
            obj.setDatecompleted(getDate());
            final ProgressDialog pb=new ProgressDialog(this);
            pb.setMessage("Saving....");
            pb.setCancelable(false);
            pb.show();
            new Thread()
            {

                @Override
                public void run()
                {

                    DB.historyDao().insertAll(obj);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run()
                        {
                            pb.dismiss();
                            imgcomplete.setVisibility(View.GONE);
                            Toast.makeText(ExerciseDetail.this, "Added To History", Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }.start();

        }

    }

    @Override
    public void onBackPressed() {
        finish();
        if(mp!=null && mp.isPlaying())
            mp.stop();

        timerHandler.removeCallbacks(timerRunnable);
        super.onBackPressed();
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
    protected void onDestroy() {
        isActivityRunning=false;
        super.onDestroy();
    }
}
