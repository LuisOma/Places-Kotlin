package com.example.minutestest.ui.main.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.minutestest.R
import com.example.minutestest.databinding.FragmentDetailBinding
import com.example.minutestest.ui.main.MainViewModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class DetailFragment: Fragment() {

    private var mainViewModel: MainViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)

        mainViewModel =
            activity?.let { ViewModelProvider(it).get(MainViewModel::class.java) }

        binding.viewModel = mainViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        val image = "https://maps.googleapis.com/maps/api/place/photo" +
                "?maxwidth=400" +
                "&photo_reference=${mainViewModel?.detailLocation?.value?.photos?.get(0)?.photo_reference}" +
                "&key=AIzaSyBQxrRs9gALWm8krbuH4dMd0l9LIvNBWcQ"

        Picasso.get().load(image)
            .transform(CropCircleTransformation())
            .into(binding.profilePicture)

        binding.textAddress.text = mainViewModel?.detailLocation?.value?.vicinity

        binding.totalRating.text = "Total de calificaciones: ${mainViewModel?.detailLocation?.value?.user_ratings_total}"
        mainViewModel?.detailLocation?.value?.rating?.toFloat()?.let {
            binding.ratingBar.rating = it
        }
        binding.tvName.text = mainViewModel?.detailLocation?.value?.name

        return binding.root
    }

}