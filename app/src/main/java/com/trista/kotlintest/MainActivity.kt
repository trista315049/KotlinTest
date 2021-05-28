package com.trista.kotlintest

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.trista.kotlintest.base.BaseActivity
import com.trista.kotlintest.ui.gallery.GalleryFragment
import com.trista.kotlintest.ui.home.HomeFragment
import com.trista.kotlintest.ui.slideshow.SlideshowFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private var firstFragment: HomeFragment? = null
    private var secondFragment: GalleryFragment? = null
    private var threeFragment: SlideshowFragment? = null
    private var toolbar: Toolbar? = null
    private var   navView: NavigationView? = null
    private var   bottomTab: BottomNavigationView? = null


    override fun setLayoutId(): Int {
       return  R.layout.activity_main
    }
    override fun initView(savedInstanceState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        bottomTab = findViewById(R.id.bottom_tab)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView?.setupWithNavController(navController)
        initFragment(savedInstanceState)
    }
    override fun initData() {
        setListener()
        bottomTab?.setOnNavigationItemSelectedListener(this)
    }





    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    private fun initFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            //异常情况
            val mFragments: List<Fragment> = supportFragmentManager.fragments
            for (item in mFragments) {
                if (item is HomeFragment) {
                    firstFragment = item
                }
                if (item is GalleryFragment) {
                    secondFragment = item
                }
                if (item is SlideshowFragment) {
                    threeFragment = item
                }
            }
        } else {
            firstFragment = HomeFragment()
            secondFragment = GalleryFragment()
            threeFragment = SlideshowFragment()
            val fragmentTrans = supportFragmentManager.beginTransaction()
            fragmentTrans.add(R.id.nav_host_fragment, firstFragment!!)
            fragmentTrans.add(R.id.nav_host_fragment, secondFragment!!)
            fragmentTrans.add(R.id.nav_host_fragment, threeFragment!!)
            fragmentTrans.commit()
        }
        changeFragment()
    }

    /*设置监听器*/
    private fun setListener() {
        navView?.setNavigationItemSelectedListener { item ->
            onItem(item)
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun onItem(item: MenuItem) {
        when (item.itemId) {
            R.id.nav_home -> {
                toolbar?.title = "home"
                changeFragment()
            }
            R.id.nav_gallery -> {
                toolbar?.title = "gallery"
                secondFragment?.let {
                    firstFragment?.let { it1 ->
                        threeFragment?.let { it2 ->
                            supportFragmentManager.beginTransaction().show(it)
                                .hide(it1)
                                .hide(it2)
                                .commit()
                        }
                    }
                }
            }
            R.id.nav_slideshow -> {
                toolbar?.title = "slideshow"
                threeFragment?.let {
                    firstFragment?.let { it1 ->
                        secondFragment?.let { it2 ->
                            supportFragmentManager.beginTransaction().show(it)
                                .hide(it1)
                                .hide(it2)
                                .commit()
                        }
                    }
                }
            }
        }
    }

    private fun changeFragment() {
        firstFragment?.let {
            secondFragment?.let { it1 ->
                threeFragment?.let { it2 ->
                    supportFragmentManager.beginTransaction().show(it)
                        .hide(it1)
                        .hide(it2)
                        .commit()
                }
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        onItem(item)

        return true
    }


}
