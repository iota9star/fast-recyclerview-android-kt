package io.nichijou.fastrecyclerview

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class FastAdapter<T> @JvmOverloads constructor(
  private val elements: MutableList<T> = ArrayList(),
  private var recyclerView: RecyclerView,
  diffCallback: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
      return oldItem == newItem
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
      return oldItem == newItem
    }
  }
) : ListAdapter<T, FastViewHolder<T>>(diffCallback) {

  private val itemViews = ArrayList<ItemViewEntry<T>>()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FastViewHolder<T> {
    return FastViewHolder(LayoutInflater.from(parent.context).inflate(itemViews.first { it.layoutResId == viewType }.layoutResId, parent, false), viewType)
  }

  override fun onBindViewHolder(holder: FastViewHolder<T>, position: Int) {
    holder.bindView(elements[position], itemViews.first { it.layoutResId == holder.viewType }.bind)
  }

  override fun getItemCount() = elements.size

  override fun getItemViewType(position: Int) = itemViews.first { it.viewTypeMatching(elements[position]) }.layoutResId

  @JvmOverloads
  fun withLayout(
    @LayoutRes layoutResId: Int,
    viewTypeMatching: (T) -> Boolean = { true },
    bind: View.(T) -> Unit
  ): FastAdapter<T> {
    itemViews.add(ItemViewEntry(layoutResId, viewTypeMatching, bind))
    recyclerView.adapter = this
    return this
  }

  @JvmOverloads
  fun addItem(elements: MutableList<T>, reset: Boolean = false) {
    val size = if (reset) {
      this.elements.clear()
      0
    } else {
      this.elements.size
    }
    this.elements.addAll(elements)
    notifyItemRangeInserted(size, elements.size)
  }

  @JvmOverloads
  fun addItem(element: T, pos: Int = this.elements.size) {
    this.elements.add(pos, element)
    notifyItemInserted(pos)
  }

  fun removeItemAt(pos: Int) {
    this.elements.removeAt(pos)
    notifyItemRemoved(pos)
  }

  fun removeItem(element: T) {
    val pos = this.elements.indexOf(element)
    removeItemAt(pos)
  }

  fun getList() = this.elements

  fun clearList() {
    elements.clear()
    notifyItemRangeRemoved(0, elements.size)
  }
}
