package com.uiuang.learning.ui

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.uiuang.learning.R
import com.uiuang.learning.utils.FileUtil
import com.uiuang.learning.utils.ImageNativeUtils

/**
 * 图像像素值统计
 */
class PixelStatisticsActivity : AppCompatActivity() {
    private lateinit var bgr: Bitmap //原图
    private lateinit var source: Bitmap //改变颜色后的图
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pixel_statistics)
        supportActionBar?.title = intent.getStringExtra("title")
        bgr = FileUtil.resourceToBitmap(this, R.drawable.lena)
        source = Bitmap.createBitmap(bgr.width, bgr.height, Bitmap.Config.ARGB_8888)
        source = ImageNativeUtils.imgColor(bgr, source)
    }
}