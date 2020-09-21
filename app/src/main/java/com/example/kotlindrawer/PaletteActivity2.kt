package com.example.kotlindrawer

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import java.util.*

class PaletteActivity2 : AppCompatActivity() {
    private var mToolbar: Toolbar? = null
    private var mTabLayout: TabLayout? = null
    private val mTitles = arrayOf(
        "风景",
        "美女",
        "花儿"
    )
    private val mFragments: MutableList<PicFragment> =
        ArrayList<PicFragment>()
    private val mImgResources = intArrayOf(
        R.mipmap.scenery,
        R.mipmap.beauty,
        R.mipmap.flower
    )
    private var mVpContent: ViewPager? = null
    private val mSwatchMap: HashMap<Int, Palette.Swatch> = HashMap<Int, Palette.Swatch>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_palette2)
        initView()
        initToolbar()
        initFragments()
        initListener()
    }


    private fun initView() {
        mToolbar = findViewById(R.id.toolbar) as Toolbar?
        mTabLayout = findViewById(R.id.tab) as TabLayout?
        mVpContent = findViewById(R.id.vp_content) as ViewPager?
    }

    private fun initToolbar() {
        setSupportActionBar(mToolbar)
        mToolbar?.setNavigationOnClickListener(View.OnClickListener { finish() })
    }

    private fun initFragments() {
        for (i in mTitles.indices) {
            val fragment = PicFragment()
            val bundle = Bundle()
            bundle.putInt(PicFragment.IMG_RESOURCE, mImgResources[i])
            fragment.setArguments(bundle)
            mFragments.add(fragment) //添加到集合中
        }
    }

    private fun initListener() {
        changeColor(0)
        val tabAdapter =
            PaletteTabAdapter(supportFragmentManager, mFragments, mTitles)

        mVpContent?.run {
            setAdapter(tabAdapter)

            setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
               override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                }

                override fun onPageSelected(position: Int) {
                    changeColor(position)
                }

                override fun onPageScrollStateChanged(state: Int) {}
            })
        }

        mTabLayout?.setupWithViewPager(mVpContent)
    }

    /**
     * 改变各部分的颜色
     * @param position 下标
     */
    private fun changeColor(position: Int) {
        val swatch: Palette.Swatch? = mSwatchMap[position]
        if (swatch != null) {
            setColor(swatch) //设置颜色
            return
        }
        // 用来提取颜色的Bitmap
        val bitmap =
            BitmapFactory.decodeResource(resources, mImgResources[position])
        // Palette的部分
        val builder: Palette.Builder = Palette.from(bitmap)
        builder.generate(object : Palette.PaletteAsyncListener {

            override fun onGenerated(palette: Palette?) {
                //获取到充满活力的样本
                val vibrant: Palette.Swatch = palette?.getVibrantSwatch() as Palette.Swatch
                setColor(vibrant) //设置颜色
                mSwatchMap[position] = vibrant //保存对应位置的样本对象
            }
        })
    }

    /**
     * 设置颜色
     * @param vibrant
     */
    private fun setColor(vibrant: Palette.Swatch) {
        // 将颜色设置给状态栏
        if (Build.VERSION.SDK_INT >= 21) {
            val window = window
            window.statusBarColor = deepenColor(vibrant.getRgb()) //设置状态栏的颜色，设置颜色之前对颜色进行加深处理
            window.navigationBarColor = deepenColor(vibrant.getRgb()) //设置导航栏的颜色，设置颜色之前对颜色进行加深处理
        }
        mToolbar?.setBackgroundColor(vibrant.getRgb()) //设置Toolbar背景色
        mTabLayout?.setBackgroundColor(vibrant.getRgb()) //设置TabLayout背景色
        mTabLayout?.setSelectedTabIndicatorColor(deepenColor(vibrant.getRgb())) //设置TabLayout指示器的颜色
    }

    /**
     * 颜色加深处理
     *
     * @param RGBValues RGB的值，由alpha（透明度）、red（红）、green（绿）、blue（蓝）构成，
     * Android中我们一般使用它的16进制，
     * 例如："#FFAABBCC",最左边到最右每两个字母就是代表alpha（透明度）、
     * red（红）、green（绿）、blue（蓝）。每种颜色值占一个字节(8位)，值域0~255
     * 所以下面使用移位的方法可以得到每种颜色的值，然后每种颜色值减小一下，在合成RGB颜色，颜色就会看起来深一些了
     * @return
     */
    private fun deepenColor(RGBValues: Int): Int {
        val alpha = RGBValues shr 24
        var red = RGBValues shr 16 and 0xFF
        var green = RGBValues shr 8 and 0xFF
        var blue = RGBValues and 0xFF
        red = Math.floor(red * (1 - 0.1)).toInt()
        green = Math.floor(green * (1 - 0.1)).toInt()
        blue = Math.floor(blue * (1 - 0.1)).toInt()
        return Color.rgb(red, green, blue)
    }
}