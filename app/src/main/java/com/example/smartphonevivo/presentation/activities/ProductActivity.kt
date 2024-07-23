package com.example.smartphonevivo.presentation.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.smartphonevivo.R


/*Этот класс не нужен для проекта, но может послужить примером того
  как получить данные с одной активи в другой*/


class ProductActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //setContentView(R.layout.activity_product)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.placeHolder)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

       /* val image: ImageView = findViewById(R.id.item_list_image_product)
        val title:TextView = findViewById(R.id.item_list_title_inside)
        val text:TextView = findViewById(R.id.item_list_text_inside)*/

       /* val imageId = intent.getIntExtra("itemImageId", -1)
        title.text = intent.getStringExtra("itemTitle")
        text.text = intent.getStringExtra("itemText")


        image.setImageResource(imageId)*/
    }
}