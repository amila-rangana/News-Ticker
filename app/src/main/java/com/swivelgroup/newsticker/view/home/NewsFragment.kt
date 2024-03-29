package com.swivelgroup.newsticker.view.home


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.swivelgroup.newsticker.R
import com.swivelgroup.newsticker.databinding.FragmentNewsBinding
import com.swivelgroup.newsticker.model.NewsItem
import com.swivelgroup.newsticker.utils.Constants
import com.swivelgroup.newsticker.view.home.headlinenews.HeadLineFragment
import com.swivelgroup.newsticker.view.newsdetails.NewsDetailsFragment
import kotlinx.android.synthetic.main.fragment_news.*


/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    lateinit var newsFragmentListener: NewsFragmentListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val layoutBinding = FragmentNewsBinding.inflate(
            inflater, container, false)
        // Inflate the layout for this fragment

        return layoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPager()
    }

    private fun setUpViewPager() {
        viewPagerNewsTicker.adapter = null
        val adapter = NewsPagerAdapter(activity!!.supportFragmentManager, this)
        viewPagerNewsTicker.offscreenPageLimit = 3
        viewPagerNewsTicker.adapter = adapter
        tabLayoutNewsTicker.setupWithViewPager(viewPagerNewsTicker)

        viewPagerNewsTicker.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                when(position){
                    1 -> {
                        newsFragmentListener.updateData()
                    }
                }
            }
        })
    }
}
