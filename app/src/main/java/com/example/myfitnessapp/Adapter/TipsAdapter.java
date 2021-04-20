package com.example.myfitnessapp.Adapter;


import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfitnessapp.Model.ModelExercise;
import com.example.myfitnessapp.Model.ModelTips;
import com.example.myfitnessapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class TipsAdapter extends RecyclerView.Adapter<TipsAdapter.ViewHolder> {

    private ArrayList<ModelTips> mData;
    private LayoutInflater mInflater;
    private  Context mContext;


    public TipsAdapter(Context context, ArrayList<ModelTips> data) {
        this.mInflater = LayoutInflater.from(context);
        mContext=context;
        this.mData = data;
    }


    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_tips, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {

        final ModelTips obj = mData.get(position);
        holder.txtTitle.setText(obj.getTitle());
        holder.txtdesc.setText(obj.getDesc());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Picasso.get().load(obj.getImage()).placeholder(mContext.getDrawable(R.drawable.img_placeholder)).into(holder.imgthumbnail);
        }
        else
            Picasso.get().load(obj.getImage()).into(holder.imgthumbnail);

    }



    @Override
    public int getItemCount() {
        return mData.size();
    }






    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView txtdesc;
        TextView txtTitle;
        ImageView imgthumbnail;

        ViewHolder(View itemView) {
            super(itemView);
            txtdesc=itemView.findViewById(R.id.txtdesc);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            imgthumbnail = itemView.findViewById(R.id.imgthumbnail);
        }

    }

}