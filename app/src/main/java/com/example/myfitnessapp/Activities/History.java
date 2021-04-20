package com.example.myfitnessapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myfitnessapp.Adapter.HistoryAdapter;
import com.example.myfitnessapp.Adapter.TipsAdapter;
import com.example.myfitnessapp.Database.AppDatabase;
import com.example.myfitnessapp.Model.ModelHistory;
import com.example.myfitnessapp.Model.ModelTips;
import com.example.myfitnessapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class History  extends AppCompatActivity {


    private ShimmerFrameLayout mShimmer;
    RecyclerView recyclerView;
    AppDatabase DB;
    List<ModelHistory> exerciseArrayList=new ArrayList<>();
    boolean isActivityRunning=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        if (getSupportActionBar() != null){
            {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle("History");
            }}

        init();
    }

    private void init()
    {
        mShimmer=findViewById(R.id.shimmereffect);
        mShimmer.startShimmerAnimation();
        recyclerView=findViewById(R.id.recyclerView);
        DB = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "history").build();
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadData();
    }

    private void loadData()
    {


        new Thread()
        {
            @Override
            public void run() {
                final List<ModelHistory> all = DB.historyDao().getAll();
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
                            Toast.makeText(History.this,"No Data Found",Toast.LENGTH_LONG).show();
                        }
                        else
                        {

                            recyclerView.setAdapter(new HistoryAdapter(History.this,exerciseArrayList));

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
}

