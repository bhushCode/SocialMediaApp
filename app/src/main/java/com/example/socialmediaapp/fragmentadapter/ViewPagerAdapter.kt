package com.example.socialmediaapp.fragmentadapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.socialmediaapp.fragment.FollowingPostFragment
import com.example.socialmediaapp.fragment.PostFragment
import com.example.socialmediaapp.homefragment.HomeFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {

        if(position == 0)
            return PostFragment()
       return FollowingPostFragment()

    }
}