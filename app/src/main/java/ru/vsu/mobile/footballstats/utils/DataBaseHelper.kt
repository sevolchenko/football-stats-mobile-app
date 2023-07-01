package ru.vsu.mobile.footballstats.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBaseHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("
                + LEAGUE_ID + " INTEGER PRIMARY KEY) ")
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addFav(leagueId: Int) {
        val values = ContentValues()
        values.put(LEAGUE_ID, leagueId)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun deleteFav(leagueId: Int) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$LEAGUE_ID=?", arrayOf(leagueId.toString()))
        db.close()
    }

    fun getFavById(leagueId: Int): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $LEAGUE_ID FROM $TABLE_NAME WHERE $LEAGUE_ID = $leagueId", null)
        val res = cursor.count > 0
        cursor.close()
        return res
    }

    fun getFavs(): List<Int> {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $LEAGUE_ID FROM $TABLE_NAME", null)
        val res = mutableListOf<Int>()
        if (cursor.count > 0) {
            cursor.moveToFirst()
            res.add(cursor.getInt(0))
            while (cursor.moveToNext()) {
                res.add(cursor.getInt(0))
            }
        }
        cursor.close()
        return res
    }

    fun getName(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null)
    }

    companion object{
        private const val DATABASE_NAME = "FAV_DB"
        private const val DATABASE_VERSION = 1
        const val TABLE_NAME = "favorites"
        const val LEAGUE_ID = "league_id"
    }
}