package io.nichijou.fastrecyclerview.simple

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.nichijou.fastrecyclerview.addItem
import io.nichijou.fastrecyclerview.removeItemAt
import io.nichijou.fastrecyclerview.withLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_recyclerview.*
import kotlinx.android.synthetic.main.item_type_one.view.*
import kotlinx.android.synthetic.main.item_type_three.view.*
import kotlinx.android.synthetic.main.item_type_two.view.*
import java.util.*

class MainActivity : AppCompatActivity() {

  private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
    when (item.itemId) {
      R.id.nav_single -> {
        view_pager.currentItem = 0
        return@OnNavigationItemSelectedListener true
      }
      R.id.nav_multiple -> {
        view_pager.currentItem = 1
        return@OnNavigationItemSelectedListener true
      }
    }
    false
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    view_pager.adapter = PagerAdapter(supportFragmentManager, arrayOf(SingleFragment(), MultipleFragment()))
    view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
      override fun onPageScrollStateChanged(state: Int) {
      }

      override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
      }

      override fun onPageSelected(position: Int) {
        navigation.selectedItemId = if (position == 0) R.id.nav_single else R.id.nav_multiple
      }
    })
  }
}

class SingleFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_recyclerview, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    recycler_view.layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
    recycler_view.withLayout(R.layout.item_type_two, Data.getMulti()) {
      tv_21.text = it.s1
    }
  }
}

class MultipleFragment : Fragment() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_recyclerview, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    recycler_view.layoutManager = LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
    recycler_view.withLayout(R.layout.item_type_one, Data.getMulti(), { it.type == 0 }) {
      tv_11.text = it.s1
      tv_12.text = it.s2
    }.withLayout(R.layout.item_type_two, { it.type == 1 }) {
      tv_21.text = it.s1
    }.withLayout(R.layout.item_type_three, { it.type == 2 }) {
      tv_31.text = it.s1
      iv_31.setImageResource(it.icon)
    }
    recycler_view.addItem(Data.getSingle())
    recycler_view.addItem(Data.getSingle(), 5)
    recycler_view.removeItemAt(0)
  }
}

class PagerAdapter(fm: FragmentManager, private val fragments: Array<Fragment>) : FragmentStatePagerAdapter(fm) {
  override fun getItem(position: Int): Fragment = fragments[position]
  override fun getCount(): Int = fragments.size
}

object Data {
  private const val str = "Cat ipsum dolor sit amet, claw your carpet in places everyone can see - why hide my amazing artistic clawing skills? yet vommit food and eat it again and stares at human while pushing stuff off a table for jumps off balcony gives owner dead mouse at present then poops in litter box snatches yarn and fights withLayout dog cat chases laser then plays in grass finds tiny spot in cupboard and sleeps all day jumps in bathtub and meows when owner fills food dish the cat knocks over the food dish cat slides down the water slide and into pool and swims even though it does not like water. "
  private val icons by lazy { arrayOf(R.drawable.ic_dashboard_black_24dp, R.drawable.ic_home_black_24dp) }
  private val random by lazy { Random() }
  fun getMulti(): ArrayList<SimpleEntity> {
    val list = ArrayList<SimpleEntity>()
    for (i in 0..200) {
      list.add(SimpleEntity(random.nextInt(3), getString(), getString(), icons[random.nextInt(icons.size)]))
    }
    return list
  }

  fun getSingle(): SimpleEntity {
    return SimpleEntity(random.nextInt(3), getString(), getString(), icons[random.nextInt(icons.size)])
  }

  private fun getString(): String {
    val half = random.nextInt(str.length / 2 - 2) + 1
    return str.substring(half, half + half)
  }
}

class SimpleEntity(val type: Int, val s1: String, val s2: String, val icon: Int)
