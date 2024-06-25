package com.example.smartphonevivo

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject

class ItemsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_items)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btGetResp:Button = findViewById(R.id.btGetResponse)
        btGetResp.setOnClickListener {
            sendGetRequest()
        }

        //создаём наш список (в виде массива с элементами типа Item)
        val itemsList:RecyclerView = findViewById(R.id.itemsList)
        val items = arrayListOf<Item>()

        //туть будем парсить



        items.add(Item(1,"Телекомпания НТВ","https://assets-iptv2022.cdnvideo.ru/static/channel/10100/logo_256_1655385292.png","https://mhd.iptv2022.com/p/NX9MVkKd-mdD_RxsMRB1vg,1719176402/streaming/ntvnn/324/1/index.m3u8"))
        items.add(Item(2,"Звезда","https://assets-iptv2022.cdnvideo.ru/static/channel/72/logo_256_1655448761.png","https://mhd.iptv2022.com/p/_vuojahmIux8WA1RT4Y2AA,1719176402/streaming/zvezda/324/1/index.m3u8"))


        itemsList.layoutManager = LinearLayoutManager(this) // указываем в каком формате будут располагаться элементы дизайна
        itemsList.adapter = ItemsAdapter(items, this) // подставляем в адаптер свой собственный
    }
    private fun sendGetRequest() {

        val url = "https://limeapi.online/api/playlist"

        val queue = Volley.newRequestQueue(this)

        val stringRequest = object : StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                val obj = JSONObject(response)
                //val temp = obj.getJSONArray("channels")
                //val temp2 = temp.getJSONObject(1)
                Log.d("MyLog","Response: $obj")
            },
            Response.ErrorListener { error ->
                Log.d("MyLog","Error: ${error.message}")
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["X-Key"] = "fh3487klskhjk2fh782kjhsdi72knjwfk7i2efdjbm"
                return headers
            }
        }

        queue.add(stringRequest)
    }
}

