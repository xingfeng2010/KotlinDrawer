package com.example.kotlindrawer

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette

class PaletteActivity : AppCompatActivity() {
    private var mTvVibrant: TextView? = null
    private var mTvLightVibrant: TextView? = null
    private var mTvDarkVibrant: TextView? = null
    private var mTvMuted: TextView? = null
    private var mTvLightMuted: TextView? = null
    private var mTvDarkMuted: TextView? = null
    private var mIvPic: ImageView? = null
    private var mTvTitle: TextView? = null
    private var mTvBody: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_palette)
        initView()
        pickPicColors()
    }

    private fun initView() {
        mIvPic = findViewById(R.id.iv_pic) as ImageView
        mTvTitle = findViewById(R.id.tv_title) as TextView
        mTvBody = findViewById(R.id.tv_body) as TextView
        mTvVibrant = findViewById(R.id.tv_vibrant) as TextView
        mTvLightVibrant = findViewById(R.id.tv_light_vibrant) as TextView
        mTvDarkVibrant = findViewById(R.id.tv_dark_vibrant) as TextView
        mTvMuted = findViewById(R.id.tv_muted) as TextView
        mTvLightMuted = findViewById(R.id.tv_light_muted) as TextView
        mTvDarkMuted = findViewById(R.id.tv_dark_muted) as TextView
    }

    fun pickPicColors() {
        val bitmap = (mIvPic!!.drawable as BitmapDrawable).bitmap
        val builder: Palette.Builder = Palette.from(bitmap)
        builder.generate { palette ->

            palette?.run {
                //提取有活力的颜色
                val vibrantColor = getVibrantColor(Color.RED)
                getLightVibrantColor(Color.RED)
                mTvVibrant!!.setBackgroundColor(vibrantColor)


                //提取有活力的 亮色
                val lightVibrantColor: Int =
                    palette.getLightVibrantColor(Color.RED)
                mTvLightVibrant!!.setBackgroundColor(lightVibrantColor)

                //提取有活力的 暗色
                val darkVibrantColor: Int = getDarkVibrantColor(Color.RED)
                mTvDarkVibrant!!.setBackgroundColor(darkVibrantColor)

                //提取柔和的颜色
                val mutedColor: Int = getMutedColor(Color.RED)
                mTvMuted!!.setBackgroundColor(mutedColor)

                //提取柔和的亮色
                val lightMutedColor: Int = getLightMutedColor(Color.RED)
                mTvLightMuted!!.setBackgroundColor(lightMutedColor)

                //提取柔和的暗色
                val darkMutedColor: Int = getDarkMutedColor(Color.RED)
                mTvDarkMuted!!.setBackgroundColor(darkMutedColor)
                val vibrantSwatch: Palette.Swatch = getVibrantSwatch() as Palette.Swatch//获取有活力的颜色样本
                val lightVibrantSwatch: Palette.Swatch? =
                    palette.getLightVibrantSwatch() //获取有活力 亮色的样本
                val drakVibrantSwatch: Palette.Swatch = getDarkVibrantSwatch() as Palette.Swatch//获取有活力 暗色的样本
                val mutedSwatch: Palette.Swatch? = getMutedSwatch() //获取柔和的颜色样本
                val lightMutedSwatch: Palette.Swatch? = getLightMutedSwatch() //获取柔和 亮色的样本
                val darkMutedSwatch: Palette.Swatch? = getDarkMutedSwatch() //获取柔和 暗色的样本

//                int rgb = vibrantSwatch.getRgb();//获取对应样本的rgb
//                float[] hsl = vibrantSwatch.getHsl();//获取hsl颜色
//                int population = vibrantSwatch.getPopulation();//获取像素的数量
//                int titleTextColor = vibrantSwatch.getTitleTextColor();//获取适合标题文字的颜色
//                int bodyTextColor = vibrantSwatch.getBodyTextColor();//获取适配内容文字的颜色


                //获取适合标题文字的颜色
                val titleTextColor: Int = drakVibrantSwatch
                    .getTitleTextColor()
                mTvTitle!!.setTextColor(titleTextColor)
                //获取适合内容文字的颜色
                val bodyTextColor: Int = drakVibrantSwatch.getBodyTextColor()
                mTvBody!!.setTextColor(bodyTextColor)
            }
        }
    }

    private fun generateTransparentColor(percent: Float, rgb: Int): Int {
        val red = Color.red(rgb)
        val green = Color.green(rgb)
        val blue = Color.blue(rgb)
        var alpha = Color.alpha(rgb)
        alpha = (alpha * percent).toInt()
        return Color.argb(alpha, red, green, blue)
    }
}