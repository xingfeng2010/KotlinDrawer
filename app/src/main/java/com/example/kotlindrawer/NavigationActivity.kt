package com.example.kotlindrawer

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_navigation.*


class NavigationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val navController : NavController = Navigation.findNavController(this, R.id.nav_host_fragment)

        val appBarConfiguration : AppBarConfiguration = AppBarConfiguration.Builder(R.id.nav_home,
            R.id.nav_gallery, R.id.nav_slideshow).build()

        //这种使用方式app bar 有返回按钮
//        val appBarConfiguration : AppBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()

        //页面头部标题
        NavigationUI.setupActionBarWithNavController(this, navController,  appBarConfiguration)

        NavigationUI.setupWithNavController(nav_view, navController)
    }


    //设置返回
    override fun onSupportNavigateUp(): Boolean {
        //return super.onSupportNavigateUp();
        val controller = Navigation.findNavController(this, R.id.nav_host_fragment)
        return controller.navigateUp()
    }
}