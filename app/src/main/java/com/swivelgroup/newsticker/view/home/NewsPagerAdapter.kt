package com.swivelgroup.newsticker.view.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.swivelgroup.newsticker.view.home.customnews.CustomNewsFragment
import com.swivelgroup.newsticker.view.home.headlinenews.HeadLineFragment
import com.swivelgroup.newsticker.view.home.profile.ProfileFragment

class NewsPagerAdapter(fragmentManager: FragmentManager, val targetFragment: NewsFragment) :
    FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragment = HeadLineFragment()
                fragment
            }
            1 -> {
                val fragment = CustomNewsFragment()
                targetFragment.newsFragmentListener = fragment

                fragment
            }
            else -> {
                val fragment = ProfileFragment()
                fragment
            }
        }
    }

    override fun getCount(): Int {
        return 3
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "Head Lines"
            }
            1 -> {
                "Custom"
            }
            else -> {
                "Profile"
            }
        }
    }
}