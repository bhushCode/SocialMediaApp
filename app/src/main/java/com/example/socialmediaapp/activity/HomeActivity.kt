package com.example.socialmediaapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.socialmediaapp.R
import com.example.socialmediaapp.bottomnavigationfragments.BottomSearchFragment
import com.example.socialmediaapp.bottomnavigationfragments.NotificationsFragment
import com.example.socialmediaapp.bottomnavigationfragments.UserProfileFragment
import com.example.socialmediaapp.databinding.ActivityHomeBinding
import com.example.socialmediaapp.fragment.FollowingPostFragment
import com.example.socialmediaapp.fragment.PostFragment
import com.example.socialmediaapp.fragmentadapter.ViewPagerAdapter
import com.example.socialmediaapp.homefragment.HomeFragment
import com.example.socialmediaapp.newpostfragment.NewPostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.coroutineScope

class HomeActivity : AppCompatActivity() {
    var backpress = 0
    lateinit var binding:ActivityHomeBinding
    lateinit var followingPostFragment: FollowingPostFragment
    lateinit var postFragment: PostFragment
    lateinit var searchFragment: BottomSearchFragment
    lateinit var profileFragment: UserProfileFragment
    lateinit var notificationsFragment: NotificationsFragment
    lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      binding = DataBindingUtil.setContentView(this,R.layout.activity_home)
//    callfragment(HomeFragment())
//        binding.homeFloatingBtn.setOnClickListener()
//        {
//           callfragment(NewPostFragment())
//        }

        initdata()
        initiallyHideAllFragment()
        setTabLayoutWithViewpager()
        setUpBottomNavigation()




    }

    private fun setUpBottomNavigation() {
        binding.fragmentHomeBottomNaviewgationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.homeFragment -> {
                    showFragment(postFragment)
                    showTabLayout()
                    true
                }
                R.id.bottomSearchFragment -> {
                    showFragment(searchFragment)
                    hideTabLayout()
                    true
                }
                R.id.notificationsFragment ->{
                    showFragment(notificationsFragment)
                    hideTabLayout()

                    true
                }
                R.id.userProfileFragment ->{
                    showFragment(profileFragment)
                    hideTabLayout()

                    true
                }
                // Handle other items as needed
                else -> false
            }
        }

    }

    private fun hideTabLayout() {
        binding.homeTabLayout.visibility = View.GONE
    }

    private fun showTabLayout() {
        binding.homeTabLayout.visibility=View.VISIBLE
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .hide(postFragment)
            .hide(followingPostFragment)
            .hide(searchFragment)
            .hide(notificationsFragment)
            .hide(profileFragment)
            .show(fragment)
            .commitNow()

        //conditions = when we are in home fragment then set visibility of view pager VISIBLE and FrameLayout GONE, when we are in searchFragment,notificationFragment,userProfileFragment then set visibility of frameLayout to VISIBLE and view pager GONE
      binding.homeViewpager.visibility = if(fragment ==searchFragment ||fragment==notificationsFragment||fragment==profileFragment)View.GONE else View.VISIBLE
        binding.homeFrameLayout.visibility=if(fragment ==searchFragment ||fragment==notificationsFragment||fragment==profileFragment)View.VISIBLE else View.GONE

    }

    private fun setTabLayoutWithViewpager() {
        // tab layout mediator ki help se ham tab layout and viewPager ko bind krenge
        TabLayoutMediator(binding.homeTabLayout, binding.homeViewpager){tab, position ->
            tab.text = when(position){
                0-> "For you"
                1-> "Following"
                else -> "For you"
            }
        }.attach()
    }

    private fun initiallyHideAllFragment() {
     supportFragmentManager.beginTransaction()
         .add(R.id.home_frameLayout,postFragment)
         .hide(postFragment)
         .add(R.id.home_frameLayout,followingPostFragment)
         .hide(followingPostFragment)
         .add(R.id.home_frameLayout,searchFragment)
         .hide(searchFragment)
         .add(R.id.home_frameLayout,notificationsFragment)
         .hide(notificationsFragment)
         .add(R.id.home_frameLayout,profileFragment)
         .hide(profileFragment)
         .commitNow()


    }

    private fun initdata() {
      postFragment = PostFragment()
        followingPostFragment = FollowingPostFragment()
        searchFragment = BottomSearchFragment()
        notificationsFragment = NotificationsFragment()
        profileFragment = UserProfileFragment()
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.homeViewpager.adapter = viewPagerAdapter
        binding.homeViewpager.offscreenPageLimit  =2
    }

    private fun callfragment(fragment: Fragment)
    {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.home_frameLayout,fragment)
       fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

    }

    override fun onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();

        } else {
            super.onBackPressed();
            finish()
            finishAffinity()
        }


    }
}