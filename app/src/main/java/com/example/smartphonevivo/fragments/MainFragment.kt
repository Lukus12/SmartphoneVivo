package com.example.smartphonevivo.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.smartphonevivo.Item
import com.example.smartphonevivo.MainViewModel
import com.example.smartphonevivo.adapters.VpAdapter
import com.example.smartphonevivo.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
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
        val adapter = VpAdapter(activity as FragmentActivity, fList)
        vp.adapter = adapter
        TabLayoutMediator(tabLayout, vp){
            tab, pos -> tab.text = tList[pos]
        }.attach()
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


    private fun parseData(result: String):List<Item>{
        val mainObject = JSONObject(result).getJSONArray("channels")
        val list = ArrayList<Item>()
        for (i in 0 until mainObject.length()) {
            val channelObject = mainObject.getJSONObject(i)
            val item = Item(
                i,
                channelObject.getString("name_ru"),
                channelObject.getString("image"),
                channelObject.getString("url"),
            )
            list.add(item)
        }
        Log.d("MyLog","Request:$mainObject")
        model.liveDataList.value = list
        return list
    }
    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }
}