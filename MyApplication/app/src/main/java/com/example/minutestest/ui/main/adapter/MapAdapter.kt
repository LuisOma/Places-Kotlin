package com.example.minutestest.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.minutestest.R
import com.example.minutestest.domain.model.Result
import com.squareup.picasso.Picasso


class MapAdapter(var users: Array<Result>? = null) :
    RecyclerView.Adapter<MapAdapter.MyViewHolder>() {
    interface AdapterClickListener{
        fun onItemSelected(user: Result?)
    }

    var listener: AdapterClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyViewHolder(layoutInflater.inflate(R.layout.item_user, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = users?.get(position)
        holder.run {
            this.name.text = item?.vicinity
            this.username.text = item?.name
            item?.rating?.let {
                this.rating.rating = it.toFloat()
            }
            Picasso.get().load(item?.icon)
                .into(this.thumb)

            this.itemView.setOnClickListener {
                listener?.onItemSelected(item)
            }
        }
    }

    override fun getItemCount() = users?.size?:0

    fun updateInfo(users: Array<Result>?){
        this.users = users
        notifyDataSetChanged()
    }

    fun setClickListener(listener: AdapterClickListener?){
        this.listener = listener
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val username = itemView.findViewById(R.id.userName) as TextView
        val name= itemView.findViewById(R.id.name) as TextView
        val rating = itemView.findViewById(R.id.rating) as RatingBar
        val thumb = itemView.findViewById(R.id.thumbnail) as ImageView

    }
}

