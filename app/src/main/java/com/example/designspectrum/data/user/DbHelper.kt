package com.example.designspectrum.data.user

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "app", factory, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, email TEXT, phoneNumber TEXT, country TEXT, gender TEXT)"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    fun addUser(user: User) {
        val values = ContentValues().apply {
            put("name", user.name)
            put("email", user.email)
            put("phoneNumber", user.phoneNumber)
            put("country", user.country)
            put("gender", user.gender)
        }

        writableDatabase.use { db ->
            db.insert("users", null, values)
        }
    }

    fun updateUser(user: User) {
        val values = ContentValues().apply {
            put("name", user.name)
            put("email", user.email)
            put("phoneNumber", user.phoneNumber)
            put("country", user.country)
            put("gender", user.gender)
        }

        val selection = "email = ?"
        val selectionArgs = arrayOf(user.email)

        writableDatabase.use { db ->
            db.update("users", values, selection, selectionArgs)
        }
    }


    @SuppressLint("Range")
    fun getUserByEmail(email: String): User? {
        val columns = arrayOf("name", "email", "phoneNumber", "country", "gender")
        val selection = "email = ?"
        val selectionArgs = arrayOf(email)

        readableDatabase.use { db ->
            val cursor = db.query("users", columns, selection, selectionArgs, null, null, null)

            var user: User? = null

            if (cursor.moveToFirst()) {
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val userEmail = cursor.getString(cursor.getColumnIndex("email"))
                val phoneNumber = cursor.getString(cursor.getColumnIndex("phoneNumber"))
                val country = cursor.getString(cursor.getColumnIndex("country"))
                val gender = cursor.getString(cursor.getColumnIndex("gender"))

                user = User(name, userEmail, phoneNumber, country, gender)
            }

            cursor.close()
            return user
        }
    }

    fun getCount(): Int {
        readableDatabase.use { db ->
            val result = db.rawQuery("SELECT COUNT(*) FROM users", null)
            var count = 0

            if (result.moveToFirst()) {
                count = result.getInt(0)
            }

            result.close()
            return count
        }
    }
}
