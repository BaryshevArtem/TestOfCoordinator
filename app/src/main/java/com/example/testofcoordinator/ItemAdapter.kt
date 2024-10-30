package com.example.testofcoordinator

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ItemAdapter : ListAdapter<String, ItemAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.item_text)
        private val arrowIcon: ImageView = itemView.findViewById(R.id.arrow_button)

        fun bind(item: String) {
            textView.text = item
            itemView.setOnClickListener {
                val scale = if (itemView.scaleX == 1f) 0.8f else 1f
                ObjectAnimator.ofPropertyValuesHolder(
                    itemView,
                    android.animation.PropertyValuesHolder.ofFloat("scaleX", scale),
                    android.animation.PropertyValuesHolder.ofFloat("scaleY", scale)
                ).setDuration(300).start()
            }
            arrowIcon.setOnClickListener {
                val rotation = if (arrowIcon.rotation == 0f) 180f else 0f
                ObjectAnimator.ofFloat(arrowIcon, "rotation", rotation)
                    .setDuration(300)
                    .start()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }
}
