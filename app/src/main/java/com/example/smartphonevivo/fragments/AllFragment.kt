package com.example.smartphonevivo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartphonevivo.Item
import com.example.smartphonevivo.MainViewModel
import com.example.smartphonevivo.adapters.ItemsAdapter
import com.example.smartphonevivo.databinding.FragmentAllBinding


class AllFragment : Fragment() {
    private lateinit var binding: FragmentAllBinding
    private val model:MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initItemsListOne()
        //получаем отпаршеный список, когда прогружается вьюшка
        model.liveDataList.observe(viewLifecycleOwner){
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
        fun newInstance() = AllFragment()
    }
}