package com.example.myfitnessapp.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitnessapp.Activities.ExerciseDetail;
import com.example.myfitnessapp.Constants;
import com.example.myfitnessapp.Model.ModelExercise;
import com.example.myfitnessapp.R;

import java.util.ArrayList;


public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private ArrayList<ModelExercise> mData;
    private LayoutInflater mInflater;
    private  Context mContext;


    public ExerciseAdapter(Context context, ArrayList<ModelExercise> data) {
        this.mInflater = LayoutInflater.from(context);
        mContext=context;
        this.mData = data;
    }


    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_exercise, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        final ModelExercise obj = mData.get(position);
        holder.txtPose.setText("Pose  : "+obj.getPose());
        holder.txtTitle.setText(obj.getTitle());
        holder.txtNumber.setText(String.valueOf("Exercise "+(++position)));



        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent n=new Intent(mContext, ExerciseDetail.class);

                n.putExtra(Constants.TITLE,obj.getTitle());
                n.putExtra(Constants.DESC,obj.getDesc());
                n.putExtra(Constants.IMAGE,obj.getImage());
                n.putExtra(Constants.POSE,obj.getPose());

                mContext.startActivity(n);
            }
        });


    }



    @Override
    public int getItemCount() {
        return mData.size();
    }






    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtPose;
        TextView txtTitle;
        TextView txtNumber;
        LinearLayout parent;

        ViewHolder(View itemView) {
            super(itemView);
            txtPose=itemView.findViewById(R.id.txtPose);
            txtTitle = itemView.findViewById(R.id.txtExercise);
            txtNumber = itemView.findViewById(R.id.txtExerciseNum);
            parent=itemView.findViewById(R.id.parent);
        }

    }

}