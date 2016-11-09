package com.udahoron.arnob.calculator.calculation.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ${farhanarnob} on ${06-Oct-16}.
 */

public class database extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "history.bd";
    public database(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
