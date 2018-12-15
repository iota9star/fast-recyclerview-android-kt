@file:JvmName("FastRecyclerViewWrapper")

package io.nichijou.fastrecyclerview

import android.annotation.SuppressLint
import android.view.View
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

@JvmOverloads
fun <T> RecyclerView.addItem(elements: MutableList<T>, reset: Boolean = false) {
    val fastAdapter = this.adapter as? FastAdapter<T>?
        ?: throw IllegalStateException("FastAdapter not attach.")
    fastAdapter.addItem(elements, reset)
}

fun <T> RecyclerView.addItem(element: T) {
    val fastAdapter = this.adapter as? FastAdapter<T>?
        ?: throw IllegalStateException("FastAdapter not attach.")
    fastAdapter.addItem(element)
}

fun <T> RecyclerView.addItem(element: T, pos: Int) {
    val fastAdapter = this.adapter as? FastAdapter<T>?
        ?: throw IllegalStateException("FastAdapter not attach.")
    fastAdapter.addItem(element, pos)
}

fun <T> RecyclerView.clearList() {
    val fastAdapter = this.adapter as? FastAdapter<T>?
        ?: throw IllegalStateException("FastAdapter not attach.")
    fastAdapter.clearList()
}

fun <T> RecyclerView.submitList(elements: MutableList<T>) {
    val fastAdapter = this.adapter as? FastAdapter<T>?
        ?: throw IllegalStateException("FastAdapter not attach.")
    fastAdapter.submitList(elements)
}

fun RecyclerView.removeItemAt(index: Int) {
    val fastAdapter = this.adapter as? FastAdapter<*>?
        ?: throw IllegalStateException("FastAdapter not attach.")
    fastAdapter.removeItemAt(index)
}

fun <T> RecyclerView.removeItemAt(element: T) {
    val fastAdapter = this.adapter as? FastAdapter<T>?
        ?: throw IllegalStateException("FastAdapter not attach.")
    fastAdapter.removeItem(element)
}

fun <T> RecyclerView.getList(): MutableList<T> {
    val fastAdapter = this.adapter as? FastAdapter<T>?
        ?: throw IllegalStateException("FastAdapter not attach.")
    return fastAdapter.getList()
}

@JvmOverloads
fun <T> RecyclerView.withLayout(
    @LayoutRes layoutResId: Int,
    elements: MutableList<T> = ArrayList(),
    viewTypeMatching: (T) -> Boolean = { true },
    diffCallback: DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }
    },
    bind: View.(T) -> Unit
): FastAdapter<T> {
    return FastAdapter(elements, this, diffCallback).withLayout(layoutResId, viewTypeMatching, bind)
}

