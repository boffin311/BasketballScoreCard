package com.example.ankit.basketballscorecard.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.example.ankit.basketballscorecard.Adapter.adapter.MainBdapter;
import com.example.ankit.basketballscorecard.R;

import com.example.ankit.basketballscorecard.database.ChngTheme.ThemeList;
import com.example.ankit.basketballscorecard.database.teamA.MyDataBaseHelper;
import com.example.ankit.basketballscorecard.database.teamA.TeamATable;

import com.example.ankit.basketballscorecard.database.teamB.TeamBList;


import java.util.ArrayList;
import java.util.Random;

public class  FragmentTwo extends Fragment {
    ArrayList<TeamBList> arrayList;
    RecyclerView rvTeamA;
    ArrayList<ThemeList>themeArray;
    FloatingActionButton fabOne;
    EditText etAdd;
    LinearLayout mainlinear;
    SQLiteDatabase db;
     MainBdapter mainBdapter;
      CoordinatorLayout coordinatorLayout;
    public static final String TAG="HIM";
    public static final String ARG_SECTION_NUMBER="section_number";

    public FragmentTwo() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyDataBaseHelper myDataBaseHelper=new MyDataBaseHelper(getContext());
        db = myDataBaseHelper.getWritableDatabase();
        myDataBaseHelper.onCreate(db);

    }

    public static FragmentTwo newFragment(int sectionNumber) {
        FragmentTwo fragmenttwo=new FragmentTwo();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragmenttwo.setArguments(args);
        return fragmenttwo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
      View view2=inflater.inflate(R.layout.fragment_main,container,false);
      themeArray=TeamATable.readAllTheme(db);
        coordinatorLayout=view2.findViewById(R.id.constraintLayout);
        if (themeArray.size()!=0)
      {if (themeArray.get(0).getCode()==1)coordinatorLayout.setBackgroundColor(Color.BLACK);}
        rvTeamA=view2.findViewById(R.id.rvTeamA);
        mainlinear=view2.findViewById(R.id.mainLinear);
        fabOne=view2.findViewById(R.id.fabOne);
        fabOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(getContext(), AddPlayerB.class);
//                startActivity(intent);
              LayoutInflater li=getLayoutInflater();
              View view=li.inflate(R.layout.list_of_players_added,null);
              etAdd=view.findViewById(R.id.etAdd);
                AlertDialog alertDialog=new AlertDialog.Builder(getContext())
                        .setTitle("Add Player")
                        .setView(view)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TeamBList teamBList=new TeamBList();
                                if (etAdd.getText().toString().length()!=0){teamBList.setName(etAdd.getText().toString());
                              teamBList.setFoulB(0);
                              teamBList.setSumB(0);
                                Random random=new Random();
                                int playerId=random.nextInt(6);
                                teamBList.setPlayer(playerId);
                                TeamATable.insertPlayerB(db,teamBList);}
                                else
                                    Toast.makeText(getContext(), "Name of Player Required", Toast.LENGTH_SHORT).show();
                                ArrayList<TeamBList> arrayList1=TeamATable.readAllB(db);
                                MainBdapter mainBdapter1=new MainBdapter(arrayList1,db,getContext(),coordinatorLayout,rvTeamA);
                               if (arrayList1.size()!=0)mainlinear.setVisibility(View.INVISIBLE);
                               rvTeamA.setLayoutManager(new LinearLayoutManager(getContext()));
                                rvTeamA.setAdapter(mainBdapter1);
                                if (arrayList1.size()- arrayList.size()>=1)
                                    Toast.makeText(getContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getContext(), "Could'nt Add the Player", Toast.LENGTH_SHORT).show();

                                Log.d(TAG, "onClick: "+arrayList1.size());
                            }
                        })
                        .setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });
      return view2;
    }

    @Override
    public void onResume() {
        super.onResume();
        MyDataBaseHelper myDataBaseHelper=new MyDataBaseHelper(getContext());
Fragment fragmentTwo=getFragmentManager().findFragmentByTag(getTag());
        if (fragmentTwo!=null) {
            db = myDataBaseHelper.getWritableDatabase();
            myDataBaseHelper.onCreate(db);
            themeArray = TeamATable.readAllTheme(db);
            arrayList = TeamATable.readAllB(db);
            Log.d(TAG, "onResume: ");
            mainBdapter = new MainBdapter(arrayList, db, getContext(), coordinatorLayout, rvTeamA);
            if (arrayList.size() != 0) mainlinear.setVisibility(View.INVISIBLE);
            else {
                mainlinear.setVisibility(View.VISIBLE);
                if (themeArray.size() != 0) {
                    if (themeArray.get(0).getCode() == 1)
                    { coordinatorLayout.setBackgroundColor(Color.BLACK);
                        Log.d(TAG, "onResume: ");}
                    else coordinatorLayout.setBackgroundColor(Color.WHITE);
                }
            }
            rvTeamA.setLayoutManager(new LinearLayoutManager(getContext()));
            rvTeamA.setAdapter(mainBdapter);
        }
    }
}
