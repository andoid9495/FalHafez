package com.hafez;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Database extends SQLiteOpenHelper {

    public static String   DB_PATH;
    public static String   DB_NAME = "data.db";
    private SQLiteDatabase myDataBase;
    private final Context  myContext;



    public Database(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;
        DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
    }



    public void createDataBase() throws IOException {

        copyDataBase();
        Log.d("isexist", "" + checkDataBase());
    }



    private boolean checkDataBase() {

        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.OPEN_READONLY);

        }
        catch (SQLiteException e) {}

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;
    }



    private void copyDataBase() throws IOException {
        File dir = new File(DB_PATH);
        if ( !dir.exists()) {
            dir.mkdirs();
        }
        File database = new File(dir, DB_NAME);
        if ( !database.exists()) {

            InputStream myInput = myContext.getAssets().open(DB_NAME);
            String outFileName = DB_PATH + DB_NAME;
            OutputStream myOutput = new FileOutputStream(outFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();
        }
    }



    public void openDataBase() throws SQLException {

        // Open the database
        String myPath = DB_PATH + DB_NAME;
        // SQLiteDatabase.NO_LOCALIZED_COLLATORS
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READWRITE);

    }



    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }



    @Override
    public void onCreate(SQLiteDatabase arg0) {
        // TODO Auto-generated method stub

    }



    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

}
