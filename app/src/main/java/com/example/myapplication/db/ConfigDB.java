package com.example.myapplication.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.model.Notes;

import java.util.ArrayList;


public class ConfigDB extends SQLiteOpenHelper {
    public ConfigDB(Context context) {
        super(context, NotesDBConstants.DB_NAME, null, NotesDBConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " +
                NotesDBConstants.NOTES_TABLE + "(" +
                NotesDBConstants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NotesDBConstants.NAME + " TEXT, " +
                NotesDBConstants.QUANTITY + " INTEGER, " +
                NotesDBConstants.STATUS + " TEXT" + ")";

        String sql2 = "CREATE TABLE " +
                NotesDBConstants.CATEGORY_TABLE + "(" +
                NotesDBConstants.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + "NotesId INTEGER," +
                NotesDBConstants.NAME + " TEXT, " + "FOREIGN KEY(NotesId) REFERENCES " +
                NotesDBConstants.CATEGORY_TABLE + "("+ NotesDBConstants.KEY_ID +")" + ")";

        db.execSQL(sql);
        db.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NotesDBConstants.NOTES_TABLE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NotesDBConstants.CATEGORY_TABLE);
        onCreate(sqLiteDatabase);
    }

    public void addNote(String name, String qty, String status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NotesDBConstants.NAME, name);
        values.put(NotesDBConstants.QUANTITY, Integer.parseInt(qty));
        values.put(NotesDBConstants.STATUS, status);
        db.insert(NotesDBConstants.NOTES_TABLE, null, values);
        db.close();
    }

    public Cursor getNote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ NotesDBConstants.NOTES_TABLE +" where id=" + id, null );
        return res;
    }

    public boolean updateNote (Integer id, Notes note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NotesDBConstants.NAME, note.getName());
        contentValues.put(NotesDBConstants.QUANTITY, note.getQuantity());
        contentValues.put(NotesDBConstants.STATUS, note.getStatus());
        db.update(NotesDBConstants.NOTES_TABLE, contentValues, "id = ? ", new String[] { Integer.toString(id) });
        return true;
    }

    public Integer deleteNote (Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(NotesDBConstants.NOTES_TABLE,
                "id = ? ",
                new String[] { Integer.toString(id) });
    }

    public ArrayList<Notes> getAllNotes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + NotesDBConstants.NOTES_TABLE +
                " ORDER BY "+ NotesDBConstants.KEY_ID +" DESC", null);
        ArrayList<Notes> notesArrayList = new ArrayList<>();

        if (c.moveToFirst()) {
            do {
                notesArrayList.add(new Notes(c.getInt(c.getColumnIndexOrThrow(NotesDBConstants.KEY_ID)),
                        c.getString(c.getColumnIndexOrThrow(NotesDBConstants.NAME)),
                        c.getInt(c.getColumnIndexOrThrow(NotesDBConstants.QUANTITY)),
                        c.getString(c.getColumnIndexOrThrow(NotesDBConstants.STATUS))));
            } while (c.moveToNext());
        }

        c.close();
        db.close();
        return notesArrayList;
    }
}
