package com.example.smartphonevivo.adapters
import android.graphics.drawable.Drawable
import android.util.Log
import com.squareup.picasso.Picasso

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.smartphonevivo.Item
import com.example.smartphonevivo.MainViewModel
import com.example.smartphonevivo.R
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.net.URI

//класс, который подставляет в поля дизайна item_in_list значения из элементов списка Item из ItemsActivity
class ItemsAdapter(private var items:List<Item>): RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    //view - это наш дизайн, например, item_in_list.xml
    class MyViewHolder(view:View): RecyclerView.ViewHolder(view){

        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
        val broadcast: TextView = view.findViewById(R.id.item_list_desc)

        val fav : ImageButton = view.findViewById(R.id.item_list_favourites)

    }
    // обрабока дизайна
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        //указываем с каким дизайном работаем
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_in_list, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    //private var f = false
    companion object fafv {
        var asd = ArrayList<Item>()
    }

    //обращаемся к полям дизайна и устанавливаем значения
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //position автоматически увеличивается на 1, когда считывается новый элемент

        holder.title.text = items[position].nameTV
        holder.broadcast.text = items[position].nameTV
        Picasso.get().load(items[position].imageURL).into(holder.image)
        holder.fav.setOnClickListener {
            if (items[position].fav) {
                holder.fav.setImageResource(R.drawable.star_off)
                items[position].fav = false
                //f = false
                val g = items[position]
                asd.remove(g)


                Log.d("12", "$asd")
            }
            else {
                holder.fav.setImageResource(R.drawable.star_on)
                items[position].fav = true
                val g = items[position]

                asd.add(g)
                Log.d("12", "$asd")
                //f = true
            }

        }
        if (items[position].fav) {
            holder.fav.setImageResource(R.drawable.star_on)

        }
        else {
            holder.fav.setImageResource(R.drawable.star_off)
        }



       /*holder.btnToProduct.setOnClickListener { //передача данных на другую активити
            val intent = Intent(context, ProductActivity::class.java)

            intent.putExtra("itemImageId", imageId)
            intent.putExtra("itemTitle", items[position].title)
            intent.putExtra("itemText", items[position].text)

            context.startActivity(intent)
        } */
    }

}