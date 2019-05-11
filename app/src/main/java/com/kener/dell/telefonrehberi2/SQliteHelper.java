package com.kener.dell.telefonrehberi2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

import java.io.File;

public class SQliteHelper extends SQLiteOpenHelper {

    private  static  final String database_NAME="RehberDB.db";//buraya db uzantısı eklemediğin için veraitbanı oldugunu anLAMIYOR program. tamam?
    private  static  final int database_VERSION=1;
    public SQliteHelper(Context c){
        super(c,database_NAME,null,database_VERSION);// tamam gonca abla oldu anladım çok sağol :)
        // rica ederim :D

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

     db.execSQL("CREATE TABLE rehber(id INTEGER PRIMARY KEY AUTOINCREMENT,ad TEXT,soyad TEXT,number TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS rehber" );
        onCreate(db);

    }
}
