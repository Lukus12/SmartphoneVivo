package com.example.smartphonevivo.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartphonevivo.Item
import com.example.smartphonevivo.MainViewModel
import com.example.smartphonevivo.R
import com.example.smartphonevivo.adapters.ItemsAdapter
import com.example.smartphonevivo.adapters.ItemsAdapter.fafv.asd
import com.example.smartphonevivo.databinding.FragmentAllBinding
import com.example.smartphonevivo.databinding.FragmentFavouritesBinding

class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private val model: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }


   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemsListOne()
        for (item in ItemsAdapter.fafv.asd) {
            if (item.fav) {
                continue
            }
            else {
                ItemsAdapter.fafv.asd.remove(item)
            }
        }
        model.favData.value = ItemsAdapter.fafv.asd
        //Log.d("fav", "12345")

        //получаем отпаршеный список, когда прогружается вьюшка
        model.favData.observe(viewLifecycleOwner){
            initItemsListTwo(it)
        }

    }

    private fun initItemsListOne() = with(binding){
        itemsList.layoutManager = LinearLayoutManager(activity) // тут можно изменить то, как отображается наш список верт/гор
    }


    private fun initItemsListTwo(items:List<Item>) = with(binding){

        itemsList.adapter = ItemsAdapter(items)
    }


    companion object {
        @JvmStatic
        fun newInstance() = FavouritesFragment()
    }
}

