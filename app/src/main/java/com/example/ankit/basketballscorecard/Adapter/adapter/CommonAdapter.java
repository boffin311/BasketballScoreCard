package com.example.ankit.basketballscorecard.Adapter.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ankit.basketballscorecard.R;
import com.example.ankit.basketballscorecard.database.common.CommonList;

import java.util.ArrayList;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.MyHolder> {
   ArrayList<CommonList> commonListArrayList;
    private static final String TAG = "CDL";
    public CommonAdapter(ArrayList<CommonList> commonListArrayList) {
        this.commonListArrayList = commonListArrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
     LayoutInflater li= (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
     View view=li.inflate(R.layout.total_score,parent,false);
     return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: "+commonListArrayList.get(position).getPAR_ID());
        holder.tvline.setText(commonListArrayList.get(position).getName());
        holder.tvPointer.setText(String.valueOf(commonListArrayList.get(position).getScore()));
        }

    @Override
    public int getItemCount() {
        return commonListArrayList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
TextView tvline,tvPointer;
        public MyHolder(View itemView) {
            super(itemView);
            tvline=itemView.findViewById(R.id.tvline);
            tvPointer=itemView.findViewById(R.id.tvPointer);
        }
    }
}
