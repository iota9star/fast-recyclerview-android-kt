package io.nichijou.fastrecyclerview

import android.view.View
import androidx.annotation.LayoutRes

internal class ItemViewEntry<T>(
  @LayoutRes val layoutResId: Int,
  val viewTypeMatching: (T) -> Boolean,
  val bind: View.(T) -> Unit
)
