package com.example.smartphonevivo.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.smartphonevivo.domain.models.Channel


//класс для работы с инфой, которая будет постоянно обновляться с получением данных с сервака
class MainViewModel:ViewModel() {
    //val liveDataCurrent = MutableLiveData<Item>() //обновлене одного элемента
    val liveDataList = MutableLiveData<List<Channel>>() //обновление списка элементов
    val favData = MutableLiveData<List<Channel>>()
}   