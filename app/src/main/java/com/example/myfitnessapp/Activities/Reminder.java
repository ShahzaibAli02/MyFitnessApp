package com.example.myfitnessapp.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myfitnessapp.Adapter.HistoryAdapter;
import com.example.myfitnessapp.Adapter.ReminderAdapter;
import com.example.myfitnessapp.Database.AppDatabase;
import com.example.myfitnessapp.Model.ModelExercise;
import com.example.myfitnessapp.Model.ModelHistory;
import com.example.myfitnessapp.Model.ModelReminder;
import com.example.myfitnessapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.List;

public class Reminder  extends AppCompatActivity implements View.OnClickListener {


    private ShimmerFrameLayout mShimmer;
    RecyclerView recyclerView;
    AppDatabase DB;
    List<ModelReminder> exerciseArrayList=new ArrayList<>();
    boolean isActivityRunning=true;

    Button btnAddNewReminder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
        if (getSupportActionBar() != null){
            {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle("Reminder");
            }}

        init();
    }

    private void init()
    {
        mShimmer=findViewById(R.id.shimmereffect);
        mShimmer.startShimmerAnimation();
        recyclerView=findViewById(R.id.recyclerView);
        btnAddNewReminder=findViewById(R.id.btnAddNewReminder);
        DB = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "history").build();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        btnAddNewReminder.setOnClickListener(this);
        loadData();
    }

    private void loadData()
    {


        new Thread()
        {
            @Override
            public void run() {
                final List<ModelReminder> all = DB.reminderDao().getAll();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run()
                    {
                        mShimmer.stopShimmerAnimation();
                        mShimmer.setVisibility(View.GONE);
                        exerciseArrayList.clear();
                        exerciseArrayList=all;
                        if(exerciseArrayList.size()<1)
                        {
                            Toast.makeText(Reminder.this,"No Data Found",Toast.LENGTH_LONG).show();
                        }
                        else
                        {

                            recyclerView.setAdapter(new ReminderAdapter(Reminder.this,exerciseArrayList));

                        }

                    }
                });
            }
        }.start();


    }


    @Override
    public void onBackPressed() {
        finish();

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        isActivityRunning=false;
        super.onDestroy();
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

        if(v==btnAddNewReminder)
        {
            finish();
            startActivity(new Intent(Reminder.this,AddReminder.class));
        }

    }
}

