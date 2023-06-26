package com.master.androidessentials.adapters

import android.view.LayoutInflater
import com.master.androidessentials.R
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

/*created by Gaurav Singh 21-06-20223*/
class GenericAdapter<T, VB : ViewBinding>(
    private var data: List<T>,
    private val bindingInflater: (LayoutInflater) -> VB,
    private val onBind: (VB, T) -> Unit,
    private val areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
    private val areContentsTheSame: (oldItem: T, newItem: T) -> Boolean
) :
    RecyclerView.Adapter<GenericAdapter<T, VB>.ViewHolder>() {
    private var itemClickListener: ((T) -> Unit)? = null

    fun setItemClickListener(listener: (T) -> Unit) {
        itemClickListener = listener
    }

    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    itemClickListener?.invoke(data[position])
                }
            }
        }

        fun bind(item: T) {
            onBind.invoke(binding, item)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = bindingInflater.invoke(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }
    fun updateList1(newList: List<T>) {
        val diffResult = DiffUtil.calculateDiff(MyDiffUtil(data, newList, areItemsTheSame, areContentsTheSame))
        data = newList
        diffResult.dispatchUpdatesTo(this)
    }

}