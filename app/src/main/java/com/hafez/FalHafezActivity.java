package com.hafez;

import java.io.IOException;
import java.util.Random;
import android.app.Activity;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class FalHafezActivity extends Activity {

    TextView       txt1;
    TextView       txt2;
    Database       myDbHelper;
    SQLiteDatabase db;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main);

        txt1 = (TextView) findViewById(R.id.textView1);
        txt2 = (TextView) findViewById(R.id.textView2);
        Database();

    }



    public void onRandomClick(View v) {
        Random rand = new Random();

        int max = 400;
        int min = 1;
        int randomNum = rand.nextInt((max - min) + 1) + min;

        db = myDbHelper.getWritableDatabase();
        Cursor c = db.rawQuery("select * from ashar where ID = " + randomNum, null);
        if (c.moveToNext()) {
            txt1.setText(c.getString(c.getColumnIndex("sher")));
            txt2.setText("تفسیر: " + c.getString(c.getColumnIndex("mani")));
        }
        db.close();
        c.close();

    }



    private void Database() throws Error {
        myDbHelper = new Database(FalHafezActivity.this);
        try {

            myDbHelper.createDataBase();

        }
        catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();
        }
        catch (SQLException sqle) {
            throw sqle;

        }
    }



    @Override
    protected void onDestroy() {

        myDbHelper.close();
        super.onDestroy();
    }
}