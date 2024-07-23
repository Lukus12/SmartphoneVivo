package com.example.smartphonevivo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartphonevivo.databinding.FragmentAllBinding
import com.example.smartphonevivo.databinding.FragmentMainBinding
import org.w3c.dom.Text


//класс для работы с инфой, которая будет постоянно обновляться с получением данных с сервака
class MainViewModel:ViewModel() {
    //val liveDataCurrent = MutableLiveData<Item>() //обновлене одного элемента
    val liveDataList = MutableLiveData<List<Item>>() //обновление списка элементов

    val favData = MutableLiveData<List<Item>>()
    //val searchCh = MutableLiveData<List<Item>>()

}