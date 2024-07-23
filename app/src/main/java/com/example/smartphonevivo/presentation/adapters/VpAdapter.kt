package com.example.smartphonevivo.presentation.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

//в FragmentStateAdapter передаём фрагмент активити
class VpAdapter(fa:FragmentActivity, private val list: List<Fragment>): FragmentStateAdapter(fa) {
    //возращаем количество фрагментов для переключения (все, избранное)
    override fun getItemCount(): Int {
        return list.size
    }
    //возращаем нужный нам фрагмент для отображения
    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}