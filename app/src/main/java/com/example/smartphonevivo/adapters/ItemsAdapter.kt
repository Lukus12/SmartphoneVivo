package com.example.smartphonevivo.adapters
import com.squareup.picasso.Picasso

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.smartphonevivo.Item
import com.example.smartphonevivo.R

//класс, который подставляет в поля дизайна item_in_list значения из элементов списка Item из ItemsActivity
class ItemsAdapter(private var items:List<Item>): RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {

    //view - это наш дизайн, например, item_in_list.xml
    class MyViewHolder(view:View): RecyclerView.ViewHolder(view){

        val image: ImageView = view.findViewById(R.id.item_list_image)
        val title: TextView = view.findViewById(R.id.item_list_title)
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

    //обращаемся к полям дизайна и устанавливаем значения
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //position автоматически увеличивается на 1, когда считывается новый элемент
        holder.title.text = items[position].nameTV
        Picasso.get().load(items[position].imageURL).into(holder.image)



       /*holder.btnToProduct.setOnClickListener { //передача данных на другую активити
            val intent = Intent(context, ProductActivity::class.java)

            intent.putExtra("itemImageId", imageId)
            intent.putExtra("itemTitle", items[position].title)
            intent.putExtra("itemText", items[position].text)

            context.startActivity(intent)
        } */
    }
}