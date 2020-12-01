package com.uiuang.learning.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.uiuang.learning.R

/**
 * 读写图像
 */
class ReadAndWriteActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_and_write)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_imread, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
//            R.id.IMREAD_UNCHANGED -> currentImreadMode = Imgcodecs.IMREAD_UNCHANGED
//            R.id.IMREAD_GRAYSCALE -> currentImreadMode = Imgcodecs.IMREAD_GRAYSCALE
//            R.id.IMREAD_COLOR -> currentImreadMode = Imgcodecs.IMREAD_COLOR
//            R.id.IMREAD_ANYDEPTH -> currentImreadMode = Imgcodecs.IMREAD_ANYDEPTH
//            R.id.IMREAD_ANYCOLOR -> currentImreadMode = Imgcodecs.IMREAD_ANYCOLOR
//            R.id.IMREAD_LOAD_GDAL -> currentImreadMode = Imgcodecs.IMREAD_LOAD_GDAL
//            R.id.IMREAD_REDUCED_GRAYSCALE_2 -> currentImreadMode =
//                    Imgcodecs.IMREAD_REDUCED_GRAYSCALE_2
//            R.id.IMREAD_REDUCED_COLOR_2 -> currentImreadMode = Imgcodecs.IMREAD_REDUCED_COLOR_2
//            R.id.IMREAD_REDUCED_GRAYSCALE_4 -> currentImreadMode =
//                    Imgcodecs.IMREAD_REDUCED_GRAYSCALE_4
//            R.id.IMREAD_REDUCED_COLOR_4 -> currentImreadMode = Imgcodecs.IMREAD_REDUCED_COLOR_4
//            R.id.IMREAD_REDUCED_GRAYSCALE_8 -> currentImreadMode =
//                    Imgcodecs.IMREAD_REDUCED_GRAYSCALE_8
//            R.id.IMREAD_REDUCED_COLOR_8 -> currentImreadMode = Imgcodecs.IMREAD_REDUCED_COLOR_8
//            R.id.IMREAD_IGNORE_ORIENTATION -> currentImreadMode =
//                    Imgcodecs.IMREAD_IGNORE_ORIENTATION
        }
        return true
    }

    private fun getModeName(mode: Int): String {
        return when (mode) {
//            Imgcodecs.IMREAD_UNCHANGED -> "IMREAD_UNCHANGED"
//            Imgcodecs.IMREAD_GRAYSCALE -> "IMREAD_GRAYSCALE"
//            Imgcodecs.IMREAD_COLOR -> "IMREAD_COLOR"
//            Imgcodecs.IMREAD_ANYDEPTH -> "IMREAD_ANYDEPTH"
//            Imgcodecs.IMREAD_ANYCOLOR -> "IMREAD_ANYCOLOR"
//            Imgcodecs.IMREAD_LOAD_GDAL -> "IMREAD_LOAD_GDAL"
//            Imgcodecs.IMREAD_REDUCED_GRAYSCALE_2 -> "IMREAD_REDUCED_GRAYSCALE_2"
//            Imgcodecs.IMREAD_REDUCED_COLOR_2 -> "IMREAD_REDUCED_COLOR_2"
//            Imgcodecs.IMREAD_REDUCED_GRAYSCALE_4 -> "IMREAD_REDUCED_GRAYSCALE_4"
//            Imgcodecs.IMREAD_REDUCED_COLOR_4 -> "IMREAD_REDUCED_COLOR_4"
//            Imgcodecs.IMREAD_REDUCED_GRAYSCALE_8 -> "IMREAD_REDUCED_GRAYSCALE_8"
//            Imgcodecs.IMREAD_REDUCED_COLOR_8 -> "IMREAD_REDUCED_COLOR_8"
//            Imgcodecs.IMREAD_IGNORE_ORIENTATION -> "IMREAD_IGNORE_ORIENTATION"
//            else -> "IMREAD_UNCHANGED"
        }
    }

}