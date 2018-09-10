package com.example.ankit.basketballscorecard.Adapter.adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ankit.basketballscorecard.R;
import com.example.ankit.basketballscorecard.database.ChngTheme.ThemeList;
import com.example.ankit.basketballscorecard.database.common.CommonList;

import com.example.ankit.basketballscorecard.database.teamA.TeamAList;
import com.example.ankit.basketballscorecard.database.teamA.TeamATable;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyHolder> {
public static final String TAG="READ";
SQLiteDatabase db;
    Context context;

    RecyclerView rvParticular,rvTeamA;
ArrayList<TeamAList> arrayList,arrayList1,arrayList2,arrayList3;
    ArrayList<TeamAList> arrayteamALists;
    ArrayList<CommonList> commonLists;
     TeamAList teamAList;

CoordinatorLayout coordinatorLayout;
    public MainAdapter(SQLiteDatabase db, ArrayList<TeamAList> arrayteamALists,Context context,CoordinatorLayout coordinatorLayout,RecyclerView rvTeamA) {
        this.db = db;
        this.rvTeamA=rvTeamA;
        this.arrayteamALists = arrayteamALists;
        this.coordinatorLayout=coordinatorLayout;
        this.context=context;

    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li= (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=li.inflate(R.layout.list_of_players,parent,false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyHolder holder, final int position) {
        ThemeColour(holder);
        teamAList = arrayteamALists.get(position);
        if (teamAList.getId()==0)
       {
           ContentValues contentValues=new ContentValues();
           contentValues.put(TeamATable.ID,position+1);
           Log.d(TAG, "onBindViewHolder: "+arrayteamALists.get(position).getId());
           Log.d(TAG, "onBindViewHolder:              "+TeamATable.ID);
           db.update(TeamATable.TABLE_NAME,contentValues,TeamATable.ID+"=0",null);
       }
        arrayList=TeamATable.readAll(db);
        if (arrayList.size()!=0)
        playerImage(arrayList.get(position).getPlayer(),holder);
      //  int id=arrayList.get(position).getId();
        Log.d(TAG, "onBindViewHolder: "+position);
        holder.tvName.setText(teamAList.getName());
        holder.tvSum.setText(String.valueOf(teamAList.getSum()));
    //    holder.tvId.setText(String.valueOf(id));
       holder.tvFoul.setText(String.valueOf(teamAList.getFoul()));
       holder.imageOne.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               TeamAList teamAList1=new TeamAList();
               teamAList1.setSum(1+Integer.valueOf(holder.tvSum.getText().toString()));
               TeamATable.UpdateScore(db,teamAList1,position);
               arrayList=TeamATable.readAll(db);
               holder.tvSum.setText(String.valueOf(arrayList.get(position).getSum()));


               CommonList commonList=new CommonList();
               commonList.setScore(1);
               commonList.setName(holder.tvName.getText().toString());
               commonList.setHours(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
               commonList.setMin(Calendar.getInstance().get(Calendar.MINUTE));
               TeamATable.insertCommomPlayer(db,commonList);


           }
       });
       holder.imageTwo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               TeamAList teamAList1=new TeamAList();
               teamAList1.setSum(2+Integer.valueOf(holder.tvSum.getText().toString()));
               TeamATable.UpdateScore(db,teamAList1,position);
               arrayList=TeamATable.readAll(db);
               holder.tvSum.setText(String.valueOf(arrayList.get(position).getSum()));

               CommonList commonList1=new CommonList();
               commonList1.setScore(2);
               commonList1.setName(holder.tvName.getText().toString());
               commonList1.setHours(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
               commonList1.setMin(Calendar.getInstance().get(Calendar.MINUTE));
               TeamATable.insertCommomPlayer(db,commonList1);

           }
       });
       holder.imageThree.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               TeamAList teamAList1=new TeamAList();
               teamAList1.setSum(3+Integer.valueOf(holder.tvSum.getText().toString()));
               TeamATable.UpdateScore(db,teamAList1,position);
               arrayList=TeamATable.readAll(db);
               holder.tvSum.setText(String.valueOf(arrayList.get(position).getSum()));


               CommonList commonList2=new CommonList();
               commonList2.setScore(3);
               commonList2.setName(holder.tvName.getText().toString());
               commonList2.setHours(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
               commonList2.setMin(Calendar.getInstance().get(Calendar.MINUTE));
               TeamATable.insertCommomPlayer(db,commonList2);
           }
       });
       holder.imageFoul.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               TeamAList teamAList2=new TeamAList();
               teamAList2.setFoul(1+Integer.valueOf(holder.tvFoul.getText().toString()));
               TeamATable.UpdateFoul(db,teamAList2,position);
               arrayList=TeamATable.readAll(db);
               holder.tvFoul.setText(String.valueOf(arrayList.get(position).getFoul()));
                          }
       });

       holder.tvName.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
             commonLists=TeamATable.readParticular(db,holder.tvName.getText().toString());
             Collections.reverse(commonLists);
               LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

               View rv=inflater.inflate(R.layout.particuler_player,null);
               rvParticular=rv.findViewById(R.id.rvParticular);
             ParticularAdapter particularAdapter=new ParticularAdapter(commonLists,db,rvParticular,context,rvTeamA,1,coordinatorLayout,position);
             rvParticular.setLayoutManager(new LinearLayoutManager(context));
               rvParticular.setAdapter(particularAdapter);

               if (Build.VERSION.SDK_INT>Build.VERSION_CODES.LOLLIPOP){
               AlertDialog alertBox=new AlertDialog.Builder(context)
                       .setView(rv)
                       .setPositiveButton("OK", new
                               DialogInterface.OnClickListener() {
                                   @Override
                                   public void onClick(DialogInterface dialog, int which) {

                                   }
                               })
                       .setTitle(holder.tvName.getText().toString())
                       .show();
           }}
       });

       holder.imageView.setOnClickListener(new View.OnClickListener() {

     @Override
           public void onClick(View v) {
         LayoutInflater li= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         arrayList =TeamATable.readAll(db);
         int player=arrayList.get(position).getPlayer();


         final View pic = li.inflate(R.layout.image_viewer, null);


         ImageView animatedImage=(ImageView)pic.findViewById(R.id.animatedImage);
         TextView textView=(TextView)pic.findViewById(R.id.tvPlayerName);
         textView.setText(arrayList.get(position).getName());
         if (player==1)animatedImage.setImageResource(R.drawable.image_one);
         else if (player==2) animatedImage.setImageResource(R.drawable.image_two);
         else if (player==3) animatedImage.setImageResource(R.drawable.image_three);
         else if (player==4) animatedImage.setImageResource(R.drawable.image_four);
         else if (player==5) animatedImage.setImageResource(R.drawable.image_five);
         else     animatedImage.setImageResource(R.drawable.image_six);
         playerImage(player,holder);
               final AlertDialog alert=new AlertDialog.Builder(context)
                       .setView(pic)
                       .show();
               alert.setCancelable(true);
               alert.setOnCancelListener(new DialogInterface.OnCancelListener() {
                   @Override
                   public void onCancel(DialogInterface dialog) {
                       alert.hide();
                       ((ViewGroup)pic.getParent()).removeView(pic);
                   }
               });
           }
       });
       }

    @Override
    public int getItemCount() {
        return arrayteamALists.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
TextView tvName,tvId;
TextView tvSum,tvFoul;
LinearLayout card;
CircleImageView imageView;
ImageButton imageOne,imageTwo,imageThree,imageFoul;

        public MyHolder(View itemView) {
            super(itemView);
            rvParticular=itemView.findViewById(R.id.rvParticular);
            imageOne=itemView.findViewById(R.id.imageOne);
            imageFoul=itemView.findViewById(R.id.imageFoul);
            imageTwo=itemView.findViewById(R.id.imageTwo);
           imageView=itemView.findViewById(R.id.imagePlayer);
            card=itemView.findViewById(R.id.card);
            imageThree=itemView.findViewById(R.id.imageThree);
            tvName=itemView.findViewById(R.id.tvName);
            tvSum=itemView.findViewById(R.id.tvSum);
            tvFoul=itemView.findViewById(R.id.tvFoul);

        }
    }
    public void ThemeColour(MyHolder getHolder)
    {
    ArrayList<ThemeList> themeArray=TeamATable.readAllTheme(db);


            if (themeArray.size()!=0)
            { if (themeArray.get(0).getCode()==1) {
                coordinatorLayout.setBackgroundColor(Color.BLACK);
                getHolder.card.setBackgroundColor(Color.BLACK);
                getHolder.tvName.setTextColor(Color.WHITE);
                getHolder.tvFoul.setTextColor(Color.WHITE);
                getHolder.tvSum.setTextColor(Color.WHITE);
                getHolder.imageOne.setBackgroundResource(R.drawable.ic_1);
                getHolder.imageTwo.setBackgroundResource(R.drawable.ic_2);
                getHolder.imageThree.setBackgroundResource(R.drawable.ic_3);
                getHolder.imageFoul.setBackgroundResource(R.drawable.ic_do_not_disturb1);
            }
            else {
                coordinatorLayout.setBackgroundColor(Color.WHITE);
                getHolder.card.setBackgroundColor(Color.WHITE);
                getHolder.tvName.setTextColor(Color.BLACK);
                getHolder.tvFoul.setTextColor(Color.rgb(145, 139, 139));
                getHolder.tvSum.setTextColor(Color.rgb(145, 139, 139));
                getHolder.imageOne.setBackgroundResource(R.drawable.ic_looks_one);
                getHolder.imageTwo.setBackgroundResource(R.drawable.ic_looks_two);
                getHolder.imageThree.setBackgroundResource(R.drawable.ic_looks_3);
                getHolder.imageFoul.setBackgroundResource(R.drawable.ic_do_not_disturb);
            }
            }

    }
    public void playerImage(int playerId,MyHolder holder)
    {    if (playerId==1)holder.imageView.setImageResource(R.drawable.image_one);
        else if (playerId==2) holder.imageView.setImageResource(R.drawable.image_two);
        else if (playerId==3) holder.imageView.setImageResource(R.drawable.image_three);
       else if (playerId==4) holder.imageView.setImageResource(R.drawable.image_four);
        else if (playerId==5) holder.imageView.setImageResource(R.drawable.image_five);
       else     holder.imageView.setImageResource(R.drawable.image_six);

    }

}
