package com.example.smartphonevivo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


//класс для работы с инфой, которая будет постоянно обновляться с получением данных с сервака
class MainViewModel:ViewModel() {
    val liveDataCurrent = MutableLiveData<Item>() //обновлене одного элемента
    val liveDataList = MutableLiveData<List<Item>>() //обновление списка элементов
}