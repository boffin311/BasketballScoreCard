package com.example.ankit.basketballscorecard.Adapter.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ankit.basketballscorecard.R;
import com.example.ankit.basketballscorecard.database.common.CommonList;
import com.example.ankit.basketballscorecard.database.teamA.TeamAList;
import com.example.ankit.basketballscorecard.database.teamA.TeamATable;
import com.example.ankit.basketballscorecard.database.teamB.TeamBList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class ParticularAdapter extends RecyclerView.Adapter<ParticularAdapter.NyHolder>{
   SQLiteDatabase db;
   RecyclerView rvparticular,rvTeamA;
   Context context;
   int code,pos;
   ArrayList<TeamAList> teamAarray;
   ArrayList<TeamBList> teamBArray;
   CoordinatorLayout coordinatorLayout;

    private static final String TAG = "CDK";
    public ParticularAdapter(ArrayList<CommonList> arrayList, SQLiteDatabase db,RecyclerView rvparticular,Context context,RecyclerView rvTeamA,int code,CoordinatorLayout coordinatorLayout,int pos) {
        this.arrayList = arrayList;
        this.db=db;
        this.rvparticular=rvparticular;
        this.context=context;
        this.rvTeamA=rvTeamA;
        this.code=code;
        this.pos=pos;
        this.coordinatorLayout=coordinatorLayout;
    }

    ArrayList<CommonList> arrayList;
    @NonNull
    @Override
    public NyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=li.inflate(R.layout.list_of_particular_player,parent,false);
        return new NyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NyHolder holder, final int position) {
holder.tvPoints.setText(String.valueOf(arrayList.get(position).getScore()));
int hrs=arrayList.get(position).getHours();
int min=arrayList.get(position).getMin();
if (hrs>12)
{   hrs=hrs-12;
    holder.tvShowTime.setText(String.format(Locale.getDefault(),"%02d:%02d",hrs,min));
}
else
{
    holder.tvShowTime.setText(String.format(Locale.getDefault(),"%02d:%02d",hrs,min));
}
        Log.d(TAG, "onBindViewHolder: "+hrs);
holder.imageDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Log.d(TAG, "onClick: "+arrayList.get(position).getPAR_ID());
        int deletingValue=arrayList.get(position).getScore();
     db.delete(TeamATable.CommonTableName,TeamATable.ID+"="+arrayList.get(position).getPAR_ID(),null);
       arrayList=TeamATable.readParticular(db,arrayList.get(position).getName());
        Collections.reverse(arrayList);
        ParticularAdapter particularAdapter=new ParticularAdapter(arrayList,db,rvparticular,context,rvTeamA,code,coordinatorLayout,pos);
        rvparticular.setLayoutManager(new LinearLayoutManager(context));
        rvparticular.setAdapter(particularAdapter);
        if (code==1)
        {  teamAarray=TeamATable.readAll(db);
        TeamAList teamAList=new TeamAList();
        teamAList.setSum(teamAarray.get(pos).getSum()-deletingValue);
            TeamATable.UpdateScore(db,teamAList,pos);
            teamAarray=TeamATable.readAll(db);
        MainAdapter mainAdapter=new MainAdapter(db,teamAarray,context,coordinatorLayout,rvTeamA);
        rvTeamA.setLayoutManager(new LinearLayoutManager(context));
        rvTeamA.setAdapter(mainAdapter);
        }
        else
        {teamBArray=TeamATable.readAllB(db);
            TeamBList teamBList=new TeamBList();
            teamBList.setSumB(teamBArray.get(pos).getSumB()-deletingValue);
            TeamATable.UpdateScoreB(db,teamBList,pos);
            teamBArray=TeamATable.readAllB(db);
            MainBdapter mainAdapter=new MainBdapter(teamBArray,db,context,coordinatorLayout,rvTeamA);
            rvTeamA.setLayoutManager(new LinearLayoutManager(context));
            rvTeamA.setAdapter(mainAdapter);
        }
    }
});
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class NyHolder extends RecyclerView.ViewHolder{
      TextView tvPoints;
      TextView tvShowTime;
      ImageButton imageDelete;
        public NyHolder(View itemView) {
            super(itemView);
            imageDelete=itemView.findViewById(R.id.imagedelete);
            tvPoints=itemView.findViewById(R.id.tvPoints);
            tvShowTime=itemView.findViewById(R.id.tvShowTime);
        }
    }
}
