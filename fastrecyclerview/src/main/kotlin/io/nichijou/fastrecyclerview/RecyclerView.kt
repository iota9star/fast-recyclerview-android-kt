package io.nichijou.fastrecyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

fun <T> RecyclerView.addNew(newData: MutableList<T>, reset: Boolean = false) {
    (this.adapter as? RecyclerAdapter<T>?)?.add(newData, reset)
        ?: throw IllegalStateException("you need use RecyclerView.with() init RecyclerView")
}

fun <T> RecyclerView.clearList() {
    (this.adapter as? RecyclerAdapter<T>?)?.clear()
        ?: throw IllegalStateException("you need use RecyclerView.with() init RecyclerView")
}

fun <T> RecyclerView.submitList(newData: MutableList<T>) {
    (this.adapter as? RecyclerAdapter<T>?)?.submitList(newData)
        ?: throw IllegalStateException("you need use RecyclerView.with() init RecyclerView")
}

fun <T> RecyclerView.with(@LayoutRes layoutRes: Int,
                          data: MutableList<T> = ArrayList(),
                          viewTypeMatching: (T) -> Boolean = { true },
                          diff: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
                              override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
                                  return oldItem == newItem
                              }

                              @SuppressLint("DiffUtilEquals")
                              override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
                                  return oldItem == newItem
                              }
                          },
                          bind: View.(T) -> Unit): RecyclerAdapter<T> = RecyclerAdapter(data, this, diff).with(layoutRes, viewTypeMatching, bind)


class RecyclerAdapter<T>(private val data: MutableList<T>, private var recycler: RecyclerView, diff: DiffUtil.ItemCallback<T>) : ListAdapter<T, ViewHolder<T>>(diff) {

    private inner class ItemView(@LayoutRes val layoutRes: Int, val matching: (T) -> Boolean, val bind: View.(T) -> Unit)

    private var items = ArrayList<ItemView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(items.first { it.layoutRes == viewType }.layoutRes, parent, false), viewType)
    }

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bindView(data[position], items.first { it.layoutRes == holder.viewType }.bind)
    }

    override fun getItemCount() = data.size

    override fun getItemViewType(position: Int) = items.first { it.matching(data[position]) }.layoutRes


    fun with(@LayoutRes layout: Int, viewTypeMatching: (T) -> Boolean, bind: View.(T) -> Unit): RecyclerAdapter<T> {
        items.add(ItemView(layout, viewTypeMatching, bind))
        recycler.adapter = this
        return this
    }

    fun add(newData: MutableList<T>, reset: Boolean = false) {
        val size = if (reset) {
            clear()
            0
        } else {
            data.size
        }
        data.addAll(newData)
        notifyItemRangeInserted(size, newData.size)
    }

    fun clear() {
        data.clear()
        notifyItemRangeRemoved(0, data.size)
    }
}

class ViewHolder<T>(itemView: View, val viewType: Int) : RecyclerView.ViewHolder(itemView) {
    fun bindView(entity: T, bind: View.(T) -> Unit) {
        itemView.apply {
            bind(entity)
        }
    }
}
