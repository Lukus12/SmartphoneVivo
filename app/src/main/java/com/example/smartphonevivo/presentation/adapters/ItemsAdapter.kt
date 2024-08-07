package com.example.smartphonevivo.presentation.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartphonevivo.R
import com.example.smartphonevivo.domain.models.Channel
import com.example.smartphonevivo.presentation.activities.PlayerActivity
import com.squareup.picasso.Picasso


//класс, который подставляет в поля дизайна item_in_list значения из элементов списка Item из ItemsActivity
class ItemsAdapter(private val context: Context, private var items:List<Channel>): RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    //view - это наш дизайн, например, item_in_list.xml
    class MyViewHolder(view:View): RecyclerView.ViewHolder(view){

        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
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

    companion object fafv {
        var asd = ArrayList<Channel>()
    }

    //обращаемся к полям дизайна и устанавливаем значения
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //position автоматически увеличивается на 1, когда считывается новый элемент
        holder.title.text = items[position].nameTV
        Picasso.get().load(items[position].imageURL).into(holder.image)

        holder.fav.setOnClickListener {
            if (items[position].fav) {
                holder.fav.setImageResource(R.drawable.star_off)
                items[position].fav = false
                //f = false
                val g = items[position]
                asd.remove(g)


                //Log.d("12", "$asd")
            }
            else {
                holder.fav.setImageResource(R.drawable.star_on)
                items[position].fav = true
                val g = items[position]

                asd.add(g)
                //Log.d("12", "$asd")
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
        holder.itemView.setOnClickListener {
            val intent = Intent(context, PlayerActivity::class.java)
            intent.putExtra("URL", items[position].url)
            intent.putExtra("NAME_TV", items[position].nameTV)
            intent.putExtra("IMAGE_URL", items[position].imageURL)
            context.startActivity(intent)
        }
    }
}