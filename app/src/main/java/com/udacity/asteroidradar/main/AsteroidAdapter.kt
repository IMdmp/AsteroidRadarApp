package com.udacity.asteroidradar.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ListMenuItemView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.database.AsteroidEntity
import com.udacity.asteroidradar.databinding.ItemAsteroidBinding

class AsteroidAdapter(private val asteroidItemListener: AsteroidItemListener) :
    ListAdapter<AsteroidEntity, AsteroidAdapter.ViewHolder>(AsteroidDiffCallback()) {


    class ViewHolder private constructor(val binding: ItemAsteroidBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AsteroidEntity,asteroidItemListener: AsteroidItemListener) {
            binding.asteroid = item
            binding.clickListener = asteroidItemListener
            binding.executePendingBindings()

        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemAsteroidBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,asteroidItemListener)
    }
}

class AsteroidDiffCallback : DiffUtil.ItemCallback<AsteroidEntity>() {
    override fun areItemsTheSame(oldItem: AsteroidEntity, newItem: AsteroidEntity): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: AsteroidEntity, newItem: AsteroidEntity): Boolean {
        return oldItem == newItem
    }

}

class AsteroidItemListener(val clickListener: (id:Long)->Unit){
    fun onClick(asteroidEntity: AsteroidEntity) = clickListener(asteroidEntity.id)
}