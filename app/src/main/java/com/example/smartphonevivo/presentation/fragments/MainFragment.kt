package com.example.smartphonevivo.presentation.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.smartphonevivo.domain.models.Channel
import com.example.smartphonevivo.presentation.MainViewModel
import com.example.smartphonevivo.presentation.adapters.ItemsAdapter
import com.example.smartphonevivo.presentation.adapters.VpAdapter
import com.example.smartphonevivo.databinding.FragmentMainBinding
import com.example.smartphonevivo.fragments.FavouritesFragment
import com.google.android.material.tabs.TabLayoutMediator
import org.json.JSONObject
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException


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


        search.setIconifiedByDefault(false)

        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp){
                tab, pos -> tab.text = tList[pos]
        }.attach()


        vp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
// Здесь можно добавить код обновления фрагмента при его переключении
                val fragment = fList[position]
                if (fragment is AllFragment) {
                    Log.d("MyLog", "all")
                    model.favData.value = ItemsAdapter.fafv.asd
                    model.liveDataList.value = ll
                    /*if (arr.isEmpty() and arrf.isEmpty()) {
                        model.favData.value = ItemsAdapter.fafv.asd
                        model.liveDataList.value = ll
                    }
                    else {
                        model.favData.value = arrf
                        model.liveDataList.value = arr
                    }*/



                } else if (fragment is FavouritesFragment) {
                    Log.d("MyLog", "fav")
                    if (arr.isEmpty() and arrf.isEmpty()) {
                        model.favData.value = ItemsAdapter.fafv.asd
                        model.liveDataList.value = ll
                    }
                    else {
                        model.favData.value = arrf
                        model.liveDataList.value = arr
                    }
                }

            }
        })

    }

    /*
     Вызов suspend-функции приостанавливает выполнение функции и позволяет потоку
     выполнять другие действия. Через некоторое время приостановленная функция
     может быть возобновлена в том же или другом потоке.
    * */
    private suspend fun makeRequest(url: String, client: OkHttpClient): Response {
        val request = Request.Builder()
            .url(url)
            .header("X-Key", "fh3487klskhjk2fh782kjhsdi72knjwfk7i2efdjbm")
            .build()

        return withContext(Dispatchers.IO) {
            client.newCall(request).await() //"ожидаем" ответ на запрос, используя сопрограммы
        }
    }

    private fun sendGetRequest() {
        val url = "https://limeapi.online/api/playlist"
        val client = OkHttpClient()

        lifecycleScope.launch {
            try {
                val response = makeRequest(url, client)

                if (response.isSuccessful) { // если ответ получен
                    val responseBody = response.body?.string()
                    responseBody?.let { parseData(it) }
                } else {
                    // Обработка ошибки
                    Log.d("MyLog", "Error: ${response.message}")
                }
            } catch (e: Exception) {
                // Обработка исключений
                Log.e("MyLog", "Exception: ${e.message}")
            }
        }
    }

    //создаем новую сопрограмму, которая поддерживает отмену и приостановку
    private suspend fun Call.await(): Response = suspendCancellableCoroutine { cont ->
        enqueue(object : Callback {
            /* onResponse вызывается, когда запрос выполнен успешно,
              а onFailure используется для обработки ошибок в случае неудачного
              выполнения запроса. */
            override fun onResponse(call: Call, response: Response) {
                cont.resume(response) //возобновляем выполнение сопрограммы с полученным объектом response
            }

            override fun onFailure(call: Call, e: IOException) {
                cont.resumeWithException(e)
            }
        })

        cont.invokeOnCancellation {//обработка отмены сопрограммы
            cancel()
        }
    }

    private fun parseData(result: String):List<Channel>{
        val mainObject = JSONObject(result).getJSONArray("channels")
        val list = ArrayList<Channel>()

        for (i in 0 until mainObject.length()) {
            val channelObject = mainObject.getJSONObject(i)
            val item = Channel(
                i,
                channelObject.getString("name_ru"),
                channelObject.getString("image"),
                channelObject.getString("url"),
                fav = false
            )
            list.add(item)
        }



        Log.d("MyLog","Request:$mainObject")

        model.liveDataList.value = list

        searchCh(list)
        ll = list
        return list
    }



    private fun searchCh(list: ArrayList<Channel>) {
        /*val arr = ArrayList<Item>()
        val arrf= ArrayList<Item>()*/

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(qwe: String?): Boolean {
                /* arr.clear()
                 for (item in list) {
                     if (item.nameTV.toLowerCase().contains(qwe.toString().toLowerCase())) {
                         arr.add(item)
                         //Toast.makeText(activity, "$arr", Toast.LENGTH_SHORT).show()
                     }
                 }
                 model.liveDataList.value = arr
                 model.favData.value = arr
                 //Toast.makeText(activity, "$arr", Toast.LENGTH_SHORT).show()*/
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
                Log.d("live", "${model.liveDataList.value}")
                arrf.clear()
                for (item in list) {
                    if (item.fav == true) {
                        if (item.nameTV.toLowerCase().contains(zxc.toString().toLowerCase())) {
                            arrf.add(item)
                        }
                    }
                }
                if (zxc == "") {
                    model.favData.value = ItemsAdapter.fafv.asd
                    model.liveDataList.value = list
                }
                model.favData.value = arrf
                //Toast.makeText(activity, "$arr", Toast.LENGTH_SHORT).show()
                return false
            }

        })

    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
        var ll = ArrayList<Channel>()
        val arr = ArrayList<Channel>()
        val arrf= ArrayList<Channel>()
    }
}