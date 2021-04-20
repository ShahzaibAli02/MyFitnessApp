package com.example.myfitnessapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.myfitnessapp.Adapter.ExerciseAdapter;
import com.example.myfitnessapp.Model.ModelExercise;
import com.example.myfitnessapp.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Exercises extends AppCompatActivity {


    private ShimmerFrameLayout mShimmer;
    RecyclerView recyclerView;

    ArrayList<ModelExercise> exerciseArrayList=new ArrayList<>();
    boolean isActivityRunning=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises);
        if (getSupportActionBar() != null){
            {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle("Exercises");
            }}

        init();
    }

    private void init()
    {
        mShimmer=findViewById(R.id.shimmereffect);
        mShimmer.startShimmerAnimation();
        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadData();
    }

    private void loadData()
    {


        FirebaseDatabase.getInstance().getReference("Exercises").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {


                if(!isActivityRunning)
                    return;


                mShimmer.stopShimmerAnimation();
                mShimmer.setVisibility(View.GONE);
                exerciseArrayList.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    exerciseArrayList.add(snapshot.getValue(ModelExercise.class));
                }

                if(exerciseArrayList.size()<1)
                {
                    Toast.makeText(Exercises.this,"No Data Found",Toast.LENGTH_LONG).show();
                }
                else
                {

                    recyclerView.setAdapter(new ExerciseAdapter(Exercises.this,exerciseArrayList));

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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
