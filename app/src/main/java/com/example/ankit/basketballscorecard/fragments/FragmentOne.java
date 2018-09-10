package com.example.ankit.basketballscorecard.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.ankit.basketballscorecard.Adapter.adapter.MainAdapter;

import com.example.ankit.basketballscorecard.R;

import com.example.ankit.basketballscorecard.database.ChngTheme.ThemeList;
import com.example.ankit.basketballscorecard.database.teamA.MyDataBaseHelper;
import com.example.ankit.basketballscorecard.database.teamA.TeamAList;
import com.example.ankit.basketballscorecard.database.teamA.TeamATable;

import java.util.ArrayList;
import java.util.Random;

public class FragmentOne extends Fragment {
ArrayList<TeamAList> teamALists;
  ArrayList<ThemeList>themeArray;
   RecyclerView rvTeamA;
    SQLiteDatabase db;
CoordinatorLayout coordinatorLayout;
   FloatingActionButton fabOne;
   LinearLayout mainLinear;
   EditText etAdd;
    MainAdapter mainAdapter;

    View view1;
   public static final String TAG="Check";
    private static final String ARG_SECTION_NUMBER = "section_number";

    public FragmentOne () {
    }


    @Override
    public void setArguments(@Nullable Bundle args) {
        super.setArguments(args);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyDataBaseHelper myDataBaseHelper=new MyDataBaseHelper(getContext());

        db=myDataBaseHelper.getWritableDatabase();
        myDataBaseHelper.onCreate(db);




    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FragmentOne newInstance(int sectionNumber) {
        FragmentOne fragment = new FragmentOne();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        view1 = inflater.inflate(R.layout.fragment_main, container, false);
       themeArray=TeamATable.readAllTheme(db);
        rvTeamA=view1.findViewById(R.id.rvTeamA);
        coordinatorLayout=view1.findViewById(R.id.constraintLayout);
        if (themeArray.size()!=0)
        {if (themeArray.get(0).getCode()==1)coordinatorLayout.setBackgroundColor(Color.BLACK);}
        mainLinear=view1.findViewById(R.id.mainLinear);
        fabOne=view1.findViewById(R.id.fabOne);
        fabOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 LayoutInflater li=getLayoutInflater();
                 View view=li.inflate(R.layout.list_of_players_added,null);
                 etAdd=view.findViewById(R.id.etAdd);
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                    AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setView(view)
                            .setTitle("Add Player")
                            .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        TeamAList teamAList=new TeamAList();
                                        if (etAdd.getText().toString().length()!=0)
                                        {  teamAList.setName(etAdd.getText().toString());

                                        teamAList.setSum(0);
                                        teamAList.setFoul(0);
                                        teamAList.setId(0);
                                            Random random=new Random();
                                           int playerId= random.nextInt(6);
                                            Log.d(TAG, "onClick: "+playerId);
                                            teamAList.setPlayer(playerId);
                                        TeamATable.insertPlayer(db,teamAList);}
                                        else
                                            Toast.makeText(getContext(), "Name of Player Required", Toast.LENGTH_SHORT).show();
                                        ArrayList<TeamAList>teamALists1=TeamATable.readAll(db);
                                        MainAdapter mainAdapter1=new MainAdapter(db,teamALists1,getContext(),coordinatorLayout,rvTeamA);
                                       if (teamALists1.size()!=0)mainLinear.setVisibility(View.INVISIBLE);
                                            rvTeamA.setLayoutManager(new LinearLayoutManager(getContext()));
                                            rvTeamA.setAdapter(mainAdapter1);
                                        if (teamALists1.size()- teamALists.size()>=1)
                                            Toast.makeText(getContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                                       else
                                            Toast.makeText(getContext(), "Could'nt Add the Player", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                            )
                            .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }
            }
        });
        return view1;
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onResume() {
        super.onResume();


        themeArray=TeamATable.readAllTheme(db);
        teamALists=TeamATable.readAll(db);
        mainAdapter = new MainAdapter(db,teamALists,getContext(),coordinatorLayout,rvTeamA);
        if(teamALists.size()!=0)mainLinear.setVisibility(View.INVISIBLE);
        else
        {  mainLinear.setVisibility(View.VISIBLE);
       if (themeArray.size()!=0){if(themeArray.get(0).getCode()==1) coordinatorLayout.setBackgroundColor(Color.BLACK);
                                 else coordinatorLayout.setBackgroundColor(Color.WHITE);
       }
        }
        rvTeamA.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTeamA.setAdapter(mainAdapter);

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

    public void DeleteIt(SQLiteDatabase db, SQLiteDatabase db1, ArrayList<TeamAList> arrayList, Context context){

//       LayoutInflater li=getLayoutInflater();
//        view1 = li.inflate(R.layout.fragment_main, null);

//        rvTeamA=view1.findViewById(R.id.rvTeamA);

        teamALists=TeamATable.readAll(db);
       // mainAdapter = new MainAdapter(db,teamALists,getContext(),coordinatorLayout);
        rvTeamA.setLayoutManager(new LinearLayoutManager(getContext()));
        rvTeamA.setAdapter(mainAdapter);
    }


}
