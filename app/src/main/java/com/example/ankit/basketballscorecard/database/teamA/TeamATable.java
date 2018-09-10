package com.example.ankit.basketballscorecard.database.teamA;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.ankit.basketballscorecard.Constants;
import com.example.ankit.basketballscorecard.database.ChngTheme.ThemeList;
import com.example.ankit.basketballscorecard.database.common.CommonList;
import com.example.ankit.basketballscorecard.database.teamB.TeamBList;


import java.util.ArrayList;

public class TeamATable implements Constants{
   public static final String TABLE_NAME="TeamA";
   public static final String TABLE_B_NAME="TeamB";
    public static final String CommonTableName="Common";
    public static String ID="ID";
    public interface ColumnCommon
    {
        String Name="Name ";
        String score="score ";
        String HOURS="hours";
        String MIN="min";

    }
   int Sum=0;
    public final static String Foul="Fouls ";
    public static final String Player="Player ";
    public static final String ThemeTableName="Theme";

    public static final String[] All_Column={ID,Column.Name,Column.Sum,Foul,Player};
    public static final String[] AllTheme={ ThemeColumn.Theme};
    public static final String[] All={ColumnCommon.Name, ColumnCommon.score,ID,ColumnCommon.HOURS,ColumnCommon.MIN};
    public interface Column{ String Name="Name ";String Sum="Sum ";}
    public interface ThemeColumn { String Theme="Theme";}
    public static final String CMD_CREATE_TEAM_A=
            Create+TABLE_NAME+LBR+
                    ID +INT+
                    COMMA+
                    Column.Name+TEXT+
                    COMMA+
                    Column.Sum+INT+
                    COMMA+
                   Foul+INT+
                    COMMA+
                    Player+INT+
                    RBR+SEMI;
    public static final String CMD_CREATE_TEAM_B=
            Create+TABLE_B_NAME+LBR+
                    ID +INT+
                    COMMA+
                    TeamATable.Column.Name+TEXT+
                    COMMA+
                    TeamATable.Column.Sum+INT+
                    COMMA+
                    TeamATable.Foul+INT+
                    COMMA+Player+INT+
                    RBR+SEMI;

    public static final String CMD_CREATE_COMMON=
            Create+CommonTableName+LBR+
                    ColumnCommon.Name+TEXT+
                    COMMA+
                   ColumnCommon.score+INT+
                    COMMA+
                    ID+INT+" primary key"+" AutoIncrement"+
                    COMMA+
                    ColumnCommon.HOURS+INT+COMMA
                    +ColumnCommon.MIN+INT+
                    RBR+SEMI;

    public static final String CMD_CREATE_THEME= Create+ThemeTableName+LBR+ ThemeColumn.Theme+INT+ RBR+SEMI;
    public static final String insert="insert into "+ThemeTableName+" values(2);";
     public static void insertPlayer(SQLiteDatabase db,TeamAList team){
         ContentValues row=new ContentValues();
         row.put(Column.Name,team.getName());
         row.put(Column.Sum,team.getSum());
         row.put(Foul,team.getFoul());
         row.put(ID,team.getId());
         row.put(Player,team.getPlayer());
         db.insert(TABLE_NAME,null,row);
     }
    public static void insertPlayerB(SQLiteDatabase db,TeamBList team){
        ContentValues row=new ContentValues();
        row.put(TeamATable.Column.Name,team.getName());
        row.put(Column.Sum,team.getSumB());
        row.put(Foul,team.getFoulB());
        row.put(ID,team.getIdB());
        row.put(Player,team.getPlayer());
        db.insert(TABLE_B_NAME,null,row);
    }
    public static void insertCommomPlayer(SQLiteDatabase db, CommonList team){
        ContentValues row=new ContentValues();
        row.put(ColumnCommon.Name,team.getName());
        row.put(ColumnCommon.score,team.getScore());
        row.put(ColumnCommon.HOURS,team.getHours());
        row.put(ColumnCommon.MIN,team.getMin());
        db.insert(CommonTableName,null,row);
    }
     public static ArrayList<TeamAList> readAll(SQLiteDatabase db)
     {
         ArrayList<TeamAList> arrayList=new ArrayList<>();
         Cursor c=db.query(TABLE_NAME,All_Column,null,null,
                            null,null,null);
         while(c.moveToNext())
         {
            TeamAList teamAList=new TeamAList();
            teamAList.setId(c.getInt(0));
            teamAList.setName(c.getString(1));
             teamAList.setSum(c.getInt(2));
             teamAList.setFoul(c.getInt(3));
             teamAList.setPlayer(c.getInt(4));
            arrayList.add(teamAList);
            }
         return arrayList;
     }
    public static ArrayList<TeamBList> readAllB(SQLiteDatabase db)
    {
        ArrayList<TeamBList> arrayList=new ArrayList<>();
        Cursor c=db.query(TABLE_B_NAME,All_Column,null,null,
                null,null,null);
        while(c.moveToNext())
        {
            TeamBList teamBList=new TeamBList();
            teamBList.setIdB(c.getInt(0));
            teamBList.setName(c.getString(1));
            teamBList.setSumB(c.getInt(2));
            teamBList.setFoulB(c.getInt(3));
            teamBList.setPlayer(c.getInt(4));
            arrayList.add(teamBList);
        }
        return arrayList;
    }

    public static ArrayList<CommonList> readAllCommon(SQLiteDatabase db)
    {
        ArrayList<CommonList> arrayList=new ArrayList<>();
        Cursor c=db.query(CommonTableName,All,null,null,
                null,null,null);
        while(c.moveToNext())
        {
            CommonList commonList=new CommonList();
            commonList.setScore(c.getInt(1));
            commonList.setName(c.getString(0));
            commonList.setPAR_ID(c.getInt(2));
            arrayList.add(commonList);
        }
        return arrayList;
    }
    public static ArrayList<CommonList> readParticular(SQLiteDatabase db,String name)
    {
        ArrayList<CommonList> arrayList=new ArrayList<>();
        Cursor c=db.query(CommonTableName,All, ColumnCommon.Name+"="+"'"+name+"'",null,
                null,null,null);
        while(c.moveToNext())
        {
            CommonList commonList=new CommonList();
            commonList.setScore(c.getInt(1));
            commonList.setName(c.getString(0));
            commonList.setPAR_ID(c.getInt(2));
            commonList.setHours(c.getInt(3));
            commonList.setMin(c.getInt(4));
            arrayList.add(commonList);
        }
        return arrayList;
    }
    public static ArrayList<ThemeList> readAllTheme(SQLiteDatabase db)
    {
        ArrayList<ThemeList> arrayList=new ArrayList<>();
        Cursor c=db.query(ThemeTableName,AllTheme,null,null,
                null,null,null);
        while(c.moveToNext())
        {
            ThemeList themeList=new ThemeList();
            themeList.setCode(c.getInt(0));
            arrayList.add(themeList);
        }
        return arrayList;
    }
     public static void DeleteTable(SQLiteDatabase db,int position)
     {
         db.delete(TABLE_NAME,ID+"="+(position+1),null);
     }
     public  static void UpdateScore(SQLiteDatabase db,TeamAList teamA,int position){
      ContentValues values=new ContentValues();
      values.put(Column.Sum,teamA.getSum());
      db.update(TABLE_NAME,values,ID+"="+(position+1),null);

     }
    public  static void UpdateFoul(SQLiteDatabase db,TeamAList teamA,int position){
        ContentValues values=new ContentValues();
        values.put(Foul,teamA.getFoul());
        Log.d("TAG", "UpdateFoul: "+teamA.getFoul());
        db.update(TABLE_NAME,values,ID+"="+(position+1),null);

    }
    public  static void UpdateScoreB(SQLiteDatabase db,TeamBList teamB,int position){
        ContentValues values=new ContentValues();
        values.put(Column.Sum,teamB.getSumB());
        db.update(TABLE_B_NAME,values,ID+"="+(position+1),null);

    }
    public  static void UpdateFoulB(SQLiteDatabase db,TeamBList teamB,int position){
        ContentValues values=new ContentValues();
        values.put(Foul,teamB.getFoulB());
        Log.d("TAG", "UpdateFoul: "+teamB.getFoulB());
        db.update(TABLE_B_NAME,values,ID+"="+(position+1),null);

    }






     public void sumUpdate(SQLiteDatabase db1)
    {
        Cursor sumCursor= db1.query(TABLE_NAME,All_Column,null,null,
                                     null,null,null);
        while(sumCursor.moveToNext())
        {
            TeamAList teamAList=new TeamAList();
            teamAList.setSum(sumCursor.getInt(2));
        }
    }


}
