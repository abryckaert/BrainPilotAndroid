package com.example.brainpilot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "brainpilot.db";
    private static final int DATABASE_VERSION = 1;

    //Event database
    private static final String TABLE_NAME_EVENT = "event";
    private static final String EVENT_ID = "id";
    private static final String COLUMN_MIN_EVENT = "min";
    private static final String COLUMN_HOUR_EVENT = "hour";

    private static final String COLUMN_DAY_EVENT = "day";
    private static final String COLUMN_MONTH_EVENT = "month";
    private static final String COLUMN_YEAR_EVENT = "year";
    private static final String COLUMN_NAME_EVENT = "name";

    //Kanban database
    private static final String TABLE_NAME_KANBAN = "kanban";
    private static final String KANBAN_ID = "id";
    private static final String COLUMN_KANBAN_NAME = "name";
    private static final String COLUMN_KANBAN_STATE = "state";
    private static final String COLUMN_KANBAN_CREATION = "creationDate";
    private static final String COLUMN_KANBAN_DEADLINE = "deadLine";


    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryEvent = createQueryEvent();
        String queryKanban = createQueryKanban();
        db.execSQL(queryEvent);
        db.execSQL(queryKanban);
    }

    @NonNull
    private String createQueryEvent() {
        String query = "CREATE TABLE " + TABLE_NAME_EVENT +
                " (" + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MIN_EVENT + " TEXT, " +
                COLUMN_HOUR_EVENT + " TEXT, " +
                COLUMN_DAY_EVENT + " INTEGER, " +
                COLUMN_YEAR_EVENT + " INTEGER," +
                COLUMN_MONTH_EVENT + " INTEGER," +
                COLUMN_NAME_EVENT + " TEXT);";
        return query;
    }

    private String createQueryKanban() {
        String query = "CREATE TABLE " + TABLE_NAME_KANBAN +
                " (" + KANBAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_KANBAN_CREATION + " TEXT, " +
                COLUMN_KANBAN_DEADLINE + " TEXT," +
                COLUMN_KANBAN_STATE + " INTEGER," +
                COLUMN_KANBAN_NAME + " TEXT);";
        return query;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EVENT);
        onCreate(db);
    }

    void addEvent(int day, int month, int year,String hour, String min, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_MIN_EVENT, min);
        cv.put(COLUMN_HOUR_EVENT, hour);
        cv.put(COLUMN_DAY_EVENT, day);
        cv.put(COLUMN_MONTH_EVENT, month);
        cv.put(COLUMN_YEAR_EVENT, year);
        cv.put(COLUMN_NAME_EVENT, name);
        long result = db.insert(TABLE_NAME_EVENT,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void addTaskKanban(String creationDate, String  deadLine, int state, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_KANBAN_CREATION, creationDate);
        cv.put(COLUMN_KANBAN_DEADLINE, deadLine);
        cv.put(COLUMN_KANBAN_STATE, state);
        cv.put(COLUMN_KANBAN_NAME, name);
        long result = db.insert(TABLE_NAME_KANBAN,null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Added Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME_EVENT;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    ArrayList<String> executeQuery(String query){
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<String> queryResult = new ArrayList<String>();
        Cursor cursor = db.rawQuery(query,null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    queryResult.add(cursor.getString(0));
                } while (cursor.moveToNext());
            }

        /*
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            for (int i = 0; i < cursor.getCount() -1; i++){
               queryResult.set(i, cursor.toString());
               cursor.moveToNext();
            }
        }
         */
        return (queryResult);
    }

    int countResult(String query){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    void updateData(String row_id, int day, int year, int name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DAY_EVENT, day);
        cv.put(COLUMN_YEAR_EVENT, year);
        cv.put(COLUMN_NAME_EVENT, name);

        long result = db.update(TABLE_NAME_EVENT, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_EVENT, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_EVENT);
    }

}
