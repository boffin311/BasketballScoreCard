package com.example.ankit.basketballscorecard.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toolbar;

import com.example.ankit.basketballscorecard.Adapter.adapter.CommonAdapter;
import com.example.ankit.basketballscorecard.R;
import com.example.ankit.basketballscorecard.database.ChngTheme.ThemeList;
import com.example.ankit.basketballscorecard.database.common.CommonList;


import com.example.ankit.basketballscorecard.database.teamA.MyDataBaseHelper;
import com.example.ankit.basketballscorecard.database.teamA.TeamAList;
import com.example.ankit.basketballscorecard.database.teamA.TeamATable;

import com.example.ankit.basketballscorecard.database.teamB.TeamBList;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
public class FragmentThree extends Fragment {

public static final  int StartingTime=480000;
long remaining_time=StartingTime;
CountDownTimer countDownTimer;
FrameLayout FlContainer;
RecyclerView rvCommon;
TextView tvLive;
ArrayList<TeamAList> arrayList;
ArrayList<TeamBList> arrayListB;
ArrayList<CommonList> commonLists,commomarrayList;
TextView tvReset,tvTeamA,tvTeamB,tvVS;
    SQLiteDatabase db1;
    MaterialProgressBar mpbar;
    FloatingActionButton btnStart;
    View view;
    TextView tvTimer,tvTotalTeamA,tvTotalTeamB;
    Chronometer chronometer;
LinearLayout totalLinear;
LinearLayout linear_two,linear_three;
CardView card_one;
    boolean isRunning=false;
    boolean startingTimeIsSet=true;





    private static final String ARG_SECTION_NUMBER = "section_number";
public static final String TAG="NEXT";
    public FragmentThree() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyDataBaseHelper myDataBaseHelper=new MyDataBaseHelper(getContext());
        db1=myDataBaseHelper.getWritableDatabase();


    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FragmentThree newInstance(int sectionNumber) {
        FragmentThree fragment = new FragmentThree();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_three,container,false);
        chronometer=view.findViewById(R.id.chronometer);
        linear_two=view.findViewById(R.id.Linear_two);
        linear_three=view.findViewById(R.id.Linear_three);
        card_one=view.findViewById(R.id.card_one);
        btnStart=view.findViewById(R.id.btnStart);
        tvReset=view.findViewById(R.id.tvReset);
        totalLinear=view.findViewById(R.id.fullLinear);

        tvLive=view.findViewById(R.id.tvLive);
        tvTeamA=view.findViewById(R.id.tvTeamA);
        tvTeamB=view.findViewById(R.id.tvTeamB);
       TeamNameUpdate();
        tvVS=view.findViewById(R.id.tvVS);
        rvCommon=view.findViewById(R.id.rvCommon);
        FlContainer=view.findViewById(R.id.FlContainer);
        tvTotalTeamA=view.findViewById(R.id.tvTotalTeamA);
        tvTotalTeamB=view.findViewById(R.id.tvTotalTeamB);
        tvTimer=view.findViewById(R.id.tvTimer);
        mpbar=view.findViewById(R.id.progressbar);
mpbar.setMax(480000);
        btnStart.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {

           if (isRunning==false)
               start();
           else if (isRunning==true)
               pause();
       }
   });

   tvReset.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
          reset();
       }
   });

//ThemeUpdate();


   return view;
    }
public void start(){
        countDownTimer=new CountDownTimer(remaining_time,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remaining_time=millisUntilFinished;
                updateTime();
            }

            @Override
            public void onFinish() {
                btnStart.setImageResource(R.drawable.ic_play_arrow_white_24dp);
                isRunning=true;
                mpbar.setProgress(0);
            }
        }.start();
        isRunning=true;
    btnStart.setImageResource(R.drawable.ic_pause);
    tvReset.setVisibility(View.INVISIBLE);

}

public void pause(){
        countDownTimer.cancel();
        isRunning=false;
    btnStart.setImageResource(R.drawable.ic_play_arrow_white_24dp);
    tvReset.setVisibility(View.VISIBLE);
    }

public void reset(){
        remaining_time=480000;
        updateTime();
        tvReset.setVisibility(View.INVISIBLE);
        btnStart.setImageResource(R.drawable.ic_play_arrow_white_24dp);}

public void updateTime(){
        int minutes=(int)(remaining_time/1000)/60;
        int second=(int)(remaining_time/1000)%60;
       int xyz=(int)(StartingTime-remaining_time);
    Log.d(TAG, "updateTime: "+remaining_time);
        mpbar.setProgress(xyz);
    if((remaining_time>9000)&&(remaining_time<10000)) {

    }
        String timelestFormated=String.format(Locale.getDefault(),"%02d:%02d",minutes,second);
          tvTimer.setText(timelestFormated);
    }

    @Override
    public void onResume() {
        super.onResume();

        commonLists= TeamATable.readAllCommon(db1);

        Collections.reverse(commonLists);
        CommonAdapter commonAdapter=new CommonAdapter(commonLists);
   if(commonLists.size()!=0)
   { rvCommon.setVisibility(View.VISIBLE);
   tvLive.setVisibility(View.INVISIBLE);
   }
   rvCommon.setLayoutManager(new LinearLayoutManager(getContext()));
   rvCommon.setAdapter(commonAdapter);
   commonAdapter.notifyDataSetChanged();
    SumUpdate();

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }


    public void SumUpdate(){
        int Sum=0,SumB=0;
       arrayList= TeamATable.readAll(db1);
       arrayListB= TeamATable.readAllB(db1);
       for(int i=0;i<arrayList.size();++i) {
           Sum+= arrayList.get(i).getSum();
           }
           for (int j=0;j<arrayListB.size();++j)
               SumB+=arrayListB.get(j).getSumB();
        tvTotalTeamA.setText(String.valueOf(Sum));
       tvTotalTeamB.setText(String.valueOf(SumB));
      
    }
//    public  void ThemeUpdate()
//    {ArrayList<ThemeList> arrayList=TeamATable.readAllTheme(db1);
//   if (arrayList.size()!=0) {
//       if (arrayList.get(0).getCode() == 1) {
//           totalLinear.setBackgroundColor(Color.BLACK);
//           tvTotalTeamA.setTextColor(Color.BLACK);
//           tvTotalTeamB.setTextColor(Color.BLACK);
//           tvReset.setTextColor(Color.BLACK);
//           tvAddNew.setTextColor(Color.BLACK);
//           tvTeamB.setTextColor(Color.BLACK);
//           tvTeamA.setTextColor(Color.BLACK);
//           tvVS.setTextColor(Color.BLACK);
//           rvCommon.setBackgroundColor(Color.BLACK);
//           tvTimer.setTextColor(Color.BLACK);
//           tvLive.setTextColor(Color.BLACK);
//           linear_two.setBackgroundColor(Color.WHITE);
//           linear_three.setBackgroundColor(Color.WHITE);
//           card_one.setCardBackgroundColor(Color.WHITE);
//           tvLive.setBackgroundColor(Color.WHITE);
//       } else {
//           totalLinear.setBackgroundColor(Color.WHITE);
//           tvTotalTeamA.setTextColor(Color.WHITE);
//           tvTotalTeamB.setTextColor(Color.WHITE);
//           tvReset.setTextColor(Color.WHITE);
//           tvAddNew.setTextColor(Color.WHITE);
//           tvTeamB.setTextColor(Color.WHITE);
//           tvTeamA.setTextColor(Color.WHITE);
//           tvVS.setTextColor(Color.WHITE);
//           rvCommon.setBackgroundColor(Color.WHITE);
//           tvTimer.setTextColor(Color.WHITE);
//           tvLive.setTextColor(Color.WHITE);
//           linear_two.setBackgroundColor(Color.BLACK);
//           linear_three.setBackgroundColor(Color.BLACK);
//           card_one.setCardBackgroundColor(Color.BLACK);
//           tvLive.setBackgroundColor(Color.BLACK);
//       }
// }
 //   }
    public  void TeamNameUpdate()
    {
        FragmentThree fragmentThree= (FragmentThree) getFragmentManager().findFragmentByTag(getTag());
      if (fragmentThree!=null)
      { TabLayout tabLayout=getActivity().findViewById(R.id.tabs);
        tvTeamA.setText(tabLayout.getTabAt(1).getText().toString());
        tvTeamB.setText(tabLayout.getTabAt(2).getText().toString());}
    }

}
