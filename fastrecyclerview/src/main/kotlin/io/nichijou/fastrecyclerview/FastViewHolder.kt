package io.nichijou.fastrecyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FastViewHolder<T>(itemView: View, val viewType: Int) : RecyclerView.ViewHolder(itemView) {
  fun bindView(entity: T, bind: View.(T) -> Unit) {
    itemView.apply {
      bind(entity)
    }
  }
}
