package com.example.smartphonevivo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.placeHolder)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val userLoginBd: EditText = findViewById(R.id.userLogin_auth)
        val userPassBd: EditText = findViewById(R.id.userPass_auth)
        val buttonAuth: Button = findViewById(R.id.buttonAuth)

        val transToReg: TextView = findViewById(R.id.TransToReg)

        buttonAuth.setOnClickListener {
            val login = userLoginBd.text.toString().trim()
            val pass = userPassBd.text.toString().trim()

            if(login == "" || pass == ""){
                Toast.makeText(this, "Не все поля заполнены", Toast.LENGTH_SHORT).show()
            }
            else{
                val db = DbHelper(this, null)
                val authUser = db.getUser(login, pass)

                if(authUser){
                    Toast.makeText(this, "Пользователь $login авторизован", Toast.LENGTH_SHORT).show()
                    userLoginBd.text.clear()
                    userPassBd.text.clear()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this, "Неверно указаны логин или пароль", Toast.LENGTH_SHORT).show()
                }
            }

        }

        transToReg.setOnClickListener {
            //переход на другую страницу
            val intent = Intent(this, RegActivity::class.java)
            startActivity(intent)
        }
    }
}