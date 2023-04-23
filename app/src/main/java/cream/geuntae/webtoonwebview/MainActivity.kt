package cream.geuntae.webtoonwebview

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import cream.geuntae.webtoonwebview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnTabLayoutNameChanged {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreference =
            getSharedPreferences(WebViewFragment.SHARED_PREFERENCE, Context.MODE_PRIVATE)
        val tab0 = sharedPreference?.getString("tab0_name", "깃허브")
        val tab1 = sharedPreference?.getString("tab1_name", "벨로그")
        val tab2 = sharedPreference?.getString("tab2_name", "깃허브저장소")

        binding.viewPager.adapter = ViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            run {
                /*val textView = TextView(this@MainActivity)
                textView.text = when (position) {
                    0 -> tab0
                    1 -> tab1
                    else -> tab2
                }
                textView.gravity = Gravity.CENTER
                tab.customView = textView*/
                tab.text = when (position) {
                    0 -> tab0
                    1 -> tab1
                    else -> tab2
                }
            }
        }.attach()

    }

    override fun onBackPressed() {

        val currentFragment = supportFragmentManager.fragments[binding.viewPager.currentItem]

        if (currentFragment is WebViewFragment) {

            if (currentFragment.canGoBack()) {
                currentFragment.goBack()

            } else {
                super.onBackPressed()
            }

        } else {
            super.onBackPressed()
        }

    }

    override fun nameChanged(position: Int, name: String) {
        val tab = binding.tabLayout.getTabAt(position)
        tab?.text = name
    }
}