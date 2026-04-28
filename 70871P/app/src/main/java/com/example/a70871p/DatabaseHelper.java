package com.example.a70871p;

import android.database.Cursor;
import java.util.ArrayList;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.Cursor;
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "lost_found.db";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "items";

    public static final String COL_ID = "id";
    public static final String COL_TYPE = "type";
    public static final String COL_NAME = "name";
    public static final String COL_PHONE = "phone";
    public static final String COL_DESCRIPTION = "description";
    public static final String COL_LOCATION = "location";
    public static final String COL_CATEGORY = "category";
    public static final String COL_IMAGE = "image";
    public static final String COL_DATE_TIME = "date_time";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_TYPE + " TEXT, " +
                COL_NAME + " TEXT, " +
                COL_PHONE + " TEXT, " +
                COL_DESCRIPTION + " TEXT, " +
                COL_LOCATION + " TEXT, " +
                COL_CATEGORY + " TEXT, " +
                COL_IMAGE + " TEXT, " +
                COL_DATE_TIME + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertItem(String type, String name, String phone, String description,
                              String location, String category, String image, String dateTime) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_TYPE, type);
        values.put(COL_NAME, name);
        values.put(COL_PHONE, phone);
        values.put(COL_DESCRIPTION, description);
        values.put(COL_LOCATION, location);
        values.put(COL_CATEGORY, category);
        values.put(COL_IMAGE, image);
        values.put(COL_DATE_TIME, dateTime);

        long result = db.insert(TABLE_NAME, null, values);

        return result != -1;
    }

    public ArrayList<Item> getAllItems() {

        ArrayList<Item> itemList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                Item item = new Item(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8)
                );

                itemList.add(item);

            } while (cursor.moveToNext());
        }

        cursor.close();
        return itemList;
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_ID + "=?", new String[]{String.valueOf(id)});
    }

    public Item getItemById(int id) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE id=?",
                new String[]{String.valueOf(id)});

        Item item = null;

        if (cursor.moveToFirst()) {
            item = new Item(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getString(8)
            );
        }

        cursor.close();
        return item;
    }

}
