package com.lekz112.test.service.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import javax.inject.Inject;

/**
 * Created by aleksey.kurnosenko on 18.04.2017.
 */

public class ReservationsSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String NAME = "reservations.db";

    @Inject
    public ReservationsSQLiteOpenHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(RestaurantTableModel.CREATE_TABLE);
        db.execSQL(CustomerModel.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO: implement if needed
        throw new UnsupportedOperationException("Not implemented");
    }
}
