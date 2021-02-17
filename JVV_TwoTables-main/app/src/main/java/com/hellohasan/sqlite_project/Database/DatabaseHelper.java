package com.hellohasan.sqlite_project.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hellohasan.sqlite_project.Util.Config;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper databaseHelper;

    // All Static variables
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = Config.DATABASE_NAME;

    // Constructor
    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static DatabaseHelper getInstance(Context context) {
        if(databaseHelper==null){
            synchronized (DatabaseHelper.class) {
                if(databaseHelper==null)
                    databaseHelper = new DatabaseHelper(context);
            }
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // Create tables SQL execution
        String CREATE_PATIENT_TABLE = "CREATE TABLE " + Config.TABLE_PATIENT + "("
                + Config.COLUMN_PATIENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Config.COLUMN_PATIENT_NAME + " TEXT NOT NULL, "
                + Config.COLUMN_PATIENT_REGISTRATION + " INTEGER NOT NULL UNIQUE, "
                + Config.COLUMN_PATIENT_DISEASE + " TEXT, " //nullable
                + Config.COLUMN_PATIENT_VILLAGE + " TEXT, "
                + Config.COLUMN_PATIENT_CONTACT +" INTEGER, "
                + Config.COLUMN_PATIENT_TABLETS+ " TEXT, "
                + Config.COLUMN_PATIENT_GENDER+ " TEXT "   + ")";
        //nullable



        db.execSQL(CREATE_PATIENT_TABLE);


        Logger.d("DB created!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Config.TABLE_PATIENT);


        // Create tables again
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);

        //enable foreign key constraints like ON UPDATE CASCADE, ON DELETE CASCADE
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

}
