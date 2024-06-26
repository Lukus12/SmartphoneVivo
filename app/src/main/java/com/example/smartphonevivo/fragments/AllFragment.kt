package com.example.smartphonevivo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smartphonevivo.Item
import com.example.smartphonevivo.adapters.ItemsAdapter
import com.example.smartphonevivo.databinding.FragmentAllBinding


class AllFragment : Fragment() {
    private lateinit var binding: FragmentAllBinding
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
        initItemsList()
    }

    private fun initItemsList() = with(binding){
        val items = listOf(
            Item(1,"Телекомпания НТВ","https://assets-iptv2022.cdnvideo.ru/static/channel/10100/logo_256_1655385292.png","https://mhd.iptv2022.com/p/NX9MVkKd-mdD_RxsMRB1vg,1719176402/streaming/ntvnn/324/1/index.m3u8"),
            Item(2,"Звезда","https://assets-iptv2022.cdnvideo.ru/static/channel/72/logo_256_1655448761.png","https://mhd.iptv2022.com/p/_vuojahmIux8WA1RT4Y2AA,1719176402/streaming/zvezda/324/1/index.m3u8")
        )
        itemsList.layoutManager = LinearLayoutManager(activity) // тут можно изменить то, как отображается наш список верт/гор
        itemsList.adapter = ItemsAdapter(items)

    }
    companion object {
        @JvmStatic
        fun newInstance() = AllFragment()
    }
}