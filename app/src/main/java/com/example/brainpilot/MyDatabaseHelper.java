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

class
MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "brainpilot.db";
    private static final int DATABASE_VERSION = 1;

    //Event database
    private static final String TABLE_NAME_EVENT = "event";
    private static final String EVENT_ID = "id";
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

    // Settings database
    private static final String TABLE_NAME_SETTING = "setting";
    private static final String SETTING_ID = "id";
    private static final String COLUMN_SETTING_FIRSTNAME ="firstName";
    private static final String COLUMN_SETTING_LASTNAME ="lastName";
    private static final String COLUMN_SETTING_LEARNINGMETHOD = "learningMethod";
    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String queryEvent = createQueryEvent();
        String queryKanban = createQueryKanban();
        String querySetting = createQuerySetting();
        db.execSQL(queryEvent);
        db.execSQL(queryKanban);
        db.execSQL(querySetting);
    }

    @NonNull
    private String createQueryEvent() {
        String query = "CREATE TABLE " + TABLE_NAME_EVENT +
                " (" + EVENT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
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

    private String createQuerySetting(){
        String query = " CREATE TABLE " + TABLE_NAME_SETTING +
                "(" +SETTING_ID + "INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COLUMN_SETTING_FIRSTNAME + " TEXT, " +
                COLUMN_SETTING_LASTNAME + " TEXT , " +
                COLUMN_SETTING_LEARNINGMETHOD + " TEXT, ";
        return query;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_EVENT);
        onCreate(db);
    }

    void addEvent(int day, int month, int year, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

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

    void addDataSetting (String firstName, String lastName, String learningMethod){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_SETTING_FIRSTNAME, firstName);
        cv.put(COLUMN_SETTING_LASTNAME, lastName);
        cv.put(COLUMN_SETTING_LEARNINGMETHOD, learningMethod);
        long result = db.insert(TABLE_NAME_SETTING, null, cv);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else {
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


    void updateData( String row_id, String name, String state, String creationDate, String deadline){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KANBAN_NAME, name);
        cv.put(COLUMN_KANBAN_STATE, state);
        cv.put(COLUMN_KANBAN_CREATION, creationDate);
        cv.put(COLUMN_KANBAN_DEADLINE, deadline);

        long result = db.update(TABLE_NAME_KANBAN, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    void updateData( String row_id, String firstName, String lastName, String learningMethod){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_KANBAN_NAME, firstName);
        cv.put(COLUMN_KANBAN_STATE, lastName);
        cv.put(COLUMN_KANBAN_CREATION, learningMethod);


        long result = db.update(TABLE_NAME_SETTING, cv, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_EVENT, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully Deleted.",Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOneRowKanban(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_KANBAN, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully Deleted.",Toast.LENGTH_SHORT).show();
        }
    }
    void deleteOneRowSetting(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME_SETTING, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME_EVENT);
    }

    void deleteAllDataKanban(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" DELETE FROM " + TABLE_NAME_KANBAN);
    }
    void deleteAllDataSetting(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(" DELETE FROM " + TABLE_NAME_SETTING);
    }

}
