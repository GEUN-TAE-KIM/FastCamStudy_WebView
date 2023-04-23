package cream.geuntae.webtoonwebview

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

// 뷰페이저를 프래그먼트로 해서 탭마다 웹뷰를 보여주는 것
class ViewPagerAdapter(private val mainActivity: MainActivity) :
    FragmentStateAdapter(mainActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                return WebViewFragment()
            }
            1 -> {
                return WebViewFragment()
            }
            else -> {
                return WebViewFragment()
            }
        }
    }
}