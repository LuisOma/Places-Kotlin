package com.example.minutestest.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minutestest.R
import com.example.minutestest.databinding.FragmentListBinding
import com.example.minutestest.domain.model.Result
import com.example.minutestest.ui.main.MainViewModel
import com.example.minutestest.ui.main.adapter.MapAdapter

class ListFragment : Fragment(), MapAdapter.AdapterClickListener
{

    private var mainViewModel: MainViewModel? = null
    lateinit var adapter: MapAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding: FragmentListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_list, container, false)

        mainViewModel =
            activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }

        binding.viewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner
        mainViewModel?.detailLocation?.value = null
        mainViewModel?.selectedLocation?.value=null

        initRecycler(binding)

        mainViewModel?.currentLocation?.observe(viewLifecycleOwner, Observer {
            getInfo()
        })

        mainViewModel?.nearLocations?.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                binding.loadingLot.visibility = View.GONE
                adapter.updateInfo(it.toTypedArray())
            }else{
                binding.loadingLot.visibility = View.VISIBLE
            }
        })

        mainViewModel?.detailLocation?.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                mainViewModel?.navController?.navigate(R.id.action_listFragment_to_profileFragment)
            }
        })

        return binding.root
    }

    private fun initRecycler(binding: FragmentListBinding) {
        binding.usersRecycler.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = MapAdapter()
       adapter.setClickListener(this)
        binding.usersRecycler.adapter = adapter

    }

    private fun getInfo() {
        mainViewModel?.getUsers()
    }

    override fun onItemSelected(user: Result?) {
        mainViewModel?.selectedLocation?.value= user
    }

}