package com.example.ankit.basketballscorecard.database.teamA;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    public MyDataBaseHelper(Context context) {
        super(context, "TeamA.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TeamATable.CMD_CREATE_TEAM_A);
        db.execSQL(TeamATable.CMD_CREATE_TEAM_B);
        db.execSQL(TeamATable.CMD_CREATE_THEME);
        db.execSQL(TeamATable.CMD_CREATE_COMMON);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
