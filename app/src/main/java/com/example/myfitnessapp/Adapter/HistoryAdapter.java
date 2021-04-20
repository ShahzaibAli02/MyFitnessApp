package com.example.myfitnessapp.Adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.myfitnessapp.Database.AppDatabase;
import com.example.myfitnessapp.Model.ModelHistory;
import com.example.myfitnessapp.Model.ModelTips;
import com.example.myfitnessapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<ModelHistory> mData;
    private LayoutInflater mInflater;
    private  Context mContext;


    public HistoryAdapter(Context context, List<ModelHistory> data) {
        this.mInflater = LayoutInflater.from(context);
        mContext=context;
        this.mData = data;
    }


    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position)
    {

        final ModelHistory obj = mData.get(position);
        holder.txtTitle.setText(obj.getTitle());
        holder.txtPose.setText("Pose  : "+obj.getPose());
        holder.txtTime.setText("TIME TO COMPLETE : "+obj.getTimeTaken());
        holder.txtDate.setText("DATE : "+obj.getDatecompleted());


        holder.parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AskForDelet(obj,position);
                return false;
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Picasso.get().load(obj.getImage()).placeholder(mContext.getDrawable(R.drawable.img_placeholder)).into(holder.imgthumbnail);
        }
        else
            Picasso.get().load(obj.getImage()).into(holder.imgthumbnail);

    }

    private void AskForDelet(final ModelHistory obj, final int position)
    {


        AlertDialog.Builder dialog=new AlertDialog.Builder(mContext);
        dialog.setMessage("Are You Sure To Delete ?");
        dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               final AppDatabase DB = Room.databaseBuilder(mContext, AppDatabase.class, "history").build();


               new Thread()
               {
                   @Override
                   public void run()
                   {
                       DB.historyDao().delete(obj);

                       ((Activity) mContext).runOnUiThread(new Runnable() {
                           @Override
                           public void run() {
                               mData.remove(position);
                               notifyDataSetChanged();
                               Toast.makeText(mContext,"Deleted",Toast.LENGTH_LONG).show();
                           }
                       });

                   }
               }.start();
            }
        });

        dialog.setNegativeButton("No",null);

        dialog.show();


    }


    @Override
    public int getItemCount() {
        return mData.size();
    }






    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txtPose;
        TextView txtTime;
        TextView txtDate;
        TextView txtTitle;
        ImageView imgthumbnail;
        LinearLayout parent;

        ViewHolder(View itemView) {
            super(itemView);
            txtPose=itemView.findViewById(R.id.txtPose);
            txtTime=itemView.findViewById(R.id.txtTimer);
            txtDate=itemView.findViewById(R.id.txtDate);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            imgthumbnail = itemView.findViewById(R.id.imgthumbnail);
            parent=itemView.findViewById(R.id.parent);
        }

    }

}