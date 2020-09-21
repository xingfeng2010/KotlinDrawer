package com.example.kotlindrawer

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        setupToobar()

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navView.setNavigationItemSelectedListener { item: MenuItem ->
            when(item.itemId) {
                R.id.nav_home -> {
                    Log.i("DEBUG_TEST","nav_home click")
                }

                R.id.nav_gallery -> {
                    Log.i("DEBUG_TEST","nav_gallery click")
                }

                R.id.nav_slideshow -> {
                    Log.i("DEBUG_TEST","nav_slideshow click")
                }

            }

            true
        }

        navController.addOnDestinationChangedListener {controller ,destination, arguments ->

            Log.i("DEBUG_TEST","destination:" + destination);
            Toast.makeText(
                this@MainActivity,
                "onDestinationChanged() called",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    /**
     * 测试使用toolbar相关代码
     */
    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupToobar() {
        val toolbar: Toolbar = findViewById(R.id.toolbar)

        //设置Toolbar的背景颜色
        toolbar.setBackgroundColor(Color.GREEN)

        //设置导航的图标
        toolbar.setNavigationIcon(R.mipmap.ic_menu)
        //设置logo
        toolbar.setLogo(R.mipmap.ic_launcher)

        //设置标题
        toolbar.title = "title"
        //设置子标题
        toolbar.subtitle = "subtitle"

        //设置标题的字体颜色
        toolbar.setTitleTextColor(getColor(android.R.color.white))
        //设置子标题的字体颜色
        toolbar.setSubtitleTextColor(getColor(android.R.color.white))

        setSupportActionBar(toolbar)

        //继承AppCompatActivity隐藏导航栏
       // supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        //继承Activity隐藏导航栏
        //requestWindowFeature(Window.FEATURE_NO_TITLE)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        var supportNav : Boolean
        val navController = findNavController(R.id.nav_host_fragment)
        supportNav = navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()

        Log.i("DEBUG_TEST","supportNav:" + supportNav);
        return supportNav
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        var tip = ""
        when (id) {
            R.id.home -> tip = "菜单"
            R.id.action_search -> tip = "搜索"
            R.id.action_add -> tip = "添加"
            R.id.action_setting -> tip = "设置"
            R.id.action_help -> tip = "帮助"
        }
        Toast.makeText(this, tip, Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }
}