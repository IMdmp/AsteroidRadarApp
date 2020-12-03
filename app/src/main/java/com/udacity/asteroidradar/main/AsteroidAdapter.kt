package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ListMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidAdapter: RecyclerView.Adapter<AsteroidAdapter.ViewHolder>() {



    class ViewHolder private constructor(val binding:ItemAsteroidBinding):RecyclerView.ViewHolder(binding.root) {
        val name = binding.tvName
        val date = binding.tvDate
        val dangerStatus = binding.ivDangerStatus

        fun bind(item:AsteroidEntity){
            binding.asteroid = item
            binding.executePendingBindings()

        }
        companion object{
            fun from(parent: ViewGroup):ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =ItemAsteroidBinding.inflate(layoutInflater,parent,false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }


}