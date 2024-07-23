package com.example.smartphonevivo.presentation

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.smartphonevivo.domain.models.User

class DbHelper(val context: Context, val factory:SQLiteDatabase.CursorFactory? ) :
    SQLiteOpenHelper(context,"dbApp", factory, 1){
    override fun onCreate(db: SQLiteDatabase?) {
        val query = "CREATE TABLE users (id INT PRIMARY KEY, login TEXT, email TEXT, pass TEXT)"
        db!!.execSQL(query) //выполнение sql запроса
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS users")
        onCreate(db)
    }

    //регистрируем нового пользователя в нашей базе данных
    fun addUser(user: User){
        val values = ContentValues()
        values.put("login", user.login) // подставляем данные в sql запрос
        values.put("email", user.email)
        values.put("pass", user.pass)

        val db = this.writableDatabase //обращение к нашей бд, в которую можно что-то записать
        db.insert("users", null, values)
        db.close()
    }

    fun getUser(login:String, pass:String): Boolean {
        val db = this.readableDatabase //обращение к нашей бд, в которую можно что-то прочитать
        //получаем запись из бд
        val result = db.rawQuery("SELECT * FROM users WHERE login = '$login' AND pass = '$pass'", null)
        return result.moveToFirst() //до первой записи в бд
    }

}