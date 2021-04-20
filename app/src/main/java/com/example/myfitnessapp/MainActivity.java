package com.example.myfitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myfitnessapp.Activities.Exercises;
import com.example.myfitnessapp.Activities.History;
import com.example.myfitnessapp.Activities.Reminder;
import com.example.myfitnessapp.Activities.Tips;
import com.facebook.shimmer.ShimmerFrameLayout;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void OnclickListener(View view)
    {



        if(view.getId()==R.id.CardExercise)
        {
            startActivity(new Intent(this, Exercises.class));
        }
        if(view.getId()==R.id.CardTips)
        {
            startActivity(new Intent(this, Tips.class));
        }
        if(view.getId()==R.id.CardHistory)
        {
            startActivity(new Intent(this, History.class));
        }

        if(view.getId()==R.id.CardREMINDER)
        {
            startActivity(new Intent(this, Reminder.class));
        }


    }
}
