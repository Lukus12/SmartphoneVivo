package com.example.smartphonevivo.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.smartphonevivo.Item
import com.example.smartphonevivo.MainActivity
import com.example.smartphonevivo.MainViewModel
import com.example.smartphonevivo.R
import com.example.smartphonevivo.adapters.ItemsAdapter
import com.example.smartphonevivo.adapters.VpAdapter
import com.example.smartphonevivo.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.MutableStateFlow
import org.json.JSONObject


class MainFragment : Fragment() {

    private val fList = listOf(
        AllFragment.newInstance(),
        FavouritesFragment.newInstance()
    )
    private val tList = listOf(
        "Все",
        "Избранные"
    )

    private lateinit var binding: FragmentMainBinding
    private val model: MainViewModel by activityViewModels()





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    //функция инициализации
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        sendGetRequest()

    }
    //переключение вкладок
    private fun init()= with(binding){
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp){
            tab, pos -> tab.text = tList[pos]
        }.attach()
    }




    private fun sendGetRequest(){
        val url = "https://limeapi.online/api/playlist"

        val queue = Volley.newRequestQueue(context)

        val stringRequest = object : StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                parseDate(response)
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





    private fun parseDate(result: String):List<Item>{
        val mainObject = JSONObject(result).getJSONArray("channels")
        val list = ArrayList<Item>()



        for (i in 0 until mainObject.length()) {
            val channelObject = mainObject.getJSONObject(i)
            val item = Item(
                i,
                channelObject.getString("name_ru"),
                channelObject.getString("image"),
                channelObject.getString("url")
            )

            list.add(item)
        }


        Log.d("MyLog","Request:$mainObject")

        model.liveDataList.value = list
        searchCh(list)
        return list
    }


    private fun searchCh(list: ArrayList<Item>) {
        val arr = ArrayList<Item>()
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(qwe: String?): Boolean {
                arr.clear()
                for (item in list) {
                    if (item.nameTV.toLowerCase().contains(qwe.toString().toLowerCase())) {
                        arr.add(item)
                        //Toast.makeText(activity, "$arr", Toast.LENGTH_SHORT).show()
                    }
                }
                model.liveDataList.value = arr
                //Toast.makeText(activity, "$arr", Toast.LENGTH_SHORT).show()
                return false
            }

            override fun onQueryTextChange(zxc: String?): Boolean {
               arr.clear()
                for (item in list) {
                    if (item.nameTV.toLowerCase().contains(zxc.toString().toLowerCase())) {
                        arr.add(item)
                    }
                    //Toast.makeText(activity, "Поиск по содержанию", Toast.LENGTH_SHORT).show()

                }
                model.liveDataList.value = arr
                //Toast.makeText(activity, "$arr", Toast.LENGTH_SHORT).show()
                return false
            }

        })
    }




    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}