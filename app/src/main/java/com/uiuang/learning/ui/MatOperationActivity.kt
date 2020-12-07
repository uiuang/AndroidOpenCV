package com.uiuang.learning.ui

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Menu
import android.view.MenuItem
import com.uiuang.learning.R
import com.uiuang.learning.utils.FileUtil
import com.uiuang.learning.utils.ImageNativeUtils
import kotlinx.android.synthetic.main.activity_mat_operation.*

/**
 * Mat操作
 */
class MatOperationActivity : AppCompatActivity() {
    private lateinit var bgr: Bitmap //原图
    private lateinit var source: Bitmap //改变颜色后的图


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mat_operation)
        bgr = FileUtil.resourceToBitmap(this, R.drawable.lena)
        source = Bitmap.createBitmap(bgr.width, bgr.height, Bitmap.Config.ARGB_8888)
        source = ImageNativeUtils.imgColor(bgr, source)
        iv_source.setImageBitmap(source)
//        val bitmap = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
//        bitmap.density = DisplayMetrics.DENSITY_XXHIGH
        iv_bgr.setImageBitmap(bgr)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_mat_operation, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.bitwise_not -> bitwiseNot(source)
            R.id.bitwise_and -> bitwiseAnd(source, bgr)
            R.id.bitwise_xor -> bitwiseXor(source, bgr)
            R.id.bitwise_or -> bitwiseOr(source, bgr)
//            R.id.add
//            -> add(source, bgr)
//            R.id.subtract
//            -> subtract(source, bgr)
//            R.id.multiply
//            -> multiply(source, bgr)
//            R.id.divide
//            -> divide(source, bgr)
//            R.id.addWeight
//            -> addWeight(source, bgr)
        }
        return true
    }

    private fun bitwiseNot(source: Bitmap) {
        var dst: Bitmap = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        dst = ImageNativeUtils.bitwiseNot(source, dst)
        iv_result.setImageBitmap(dst)
    }

    private fun bitwiseAnd(source: Bitmap, attach: Bitmap) {
        var dst: Bitmap = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        dst = ImageNativeUtils.bitwiseAnd(source, attach, dst)
        iv_result.setImageBitmap(dst)
    }

    private fun bitwiseOr(source: Bitmap, attach: Bitmap) {
        var dst: Bitmap = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        dst = ImageNativeUtils.bitwiseOr(source, attach, dst)
        iv_result.setImageBitmap(dst)
    }

    private fun bitwiseXor(source: Bitmap, attach: Bitmap) {
        var dst: Bitmap = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        dst= ImageNativeUtils.bitwiseXor(source, attach, dst)
        iv_result.setImageBitmap(dst)
    }


}