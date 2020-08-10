package com.binar.sqliteapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context):
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            private val DATABASE_VERSION = 1
            private val DATABASE_NAME = "InventoryDatabase"
            private val TABLE_INVENTORY = "InventoryTable"
            private val COLUMN_ID = "id"
            private val COLUMN_NAME = "name"
            private val COLUMN_TOTAL = "total"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        // membuat table dengan fields
        val queryCreateTable =
            "CREATE TABLE $TABLE_INVENTORY ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_NAME TEXT, $COLUMN_TOTAL INTEGER)"
        db?.execSQL(queryCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val queryDropTable = "DROP TABLE IF EXISTS $TABLE_INVENTORY"
        db?.execSQL(queryDropTable)
        onCreate(db)
    }

    fun insertInventory(inventory: Inventory): Long? {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_NAME, inventory.name)
        contentValues.put(COLUMN_TOTAL, inventory.total)

        // mengembalikan jumlah baris yang berhasil disimpan / diinput
        val numberRecordSaved = db?.insert(TABLE_INVENTORY, null, contentValues)
        db?.close()
        return numberRecordSaved
    }

    fun readInventory(): ArrayList<Inventory> {
        val listInventory = ArrayList<Inventory>()
        val selectAllStudentQuery = "SELECT * FROM $TABLE_INVENTORY"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery(selectAllStudentQuery, null)
        } catch (e: SQLException) {
            db.execSQL(selectAllStudentQuery)
            return ArrayList()
        }

        // buat menampung sementara
        var id: Int
        var name: String
        var total: Int

        if (cursor.moveToFirst()) { // setidaknya ada datanya. kalo ndk ada pasti "first" nya ndk ada
            do {
                id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                total = cursor.getInt(cursor.getColumnIndex(COLUMN_TOTAL))
                val inventory = Inventory(id, name, total)
                listInventory.add(inventory)
            } while (cursor.moveToNext())
        }

        return listInventory
    }

    fun updateInventory(st: Inventory): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID,  st.id)
        contentValues.put(COLUMN_NAME, st.name)
        contentValues.put(COLUMN_TOTAL, st.total)

        // update baris
        val success = db.update(TABLE_INVENTORY, contentValues, "id= ${st.id}", null)
        // argumen kedua adalah string yang berisi null-column-hack

        db.close()
        return success
    }

    fun deleteInventory(st: Inventory): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_ID, st.id)
        val success = db.delete(TABLE_INVENTORY, "id= ${st.id}", null)
        db.close()
        return success
    }
}