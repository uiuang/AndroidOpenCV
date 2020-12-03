package com.uiuang.learning.ui

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.uiuang.learning.R
import com.uiuang.learning.utils.*
import kotlinx.android.synthetic.main.activity_read_and_write.*
import java.io.File
import java.io.FileOutputStream

/**
 * 读写图像
 */
class ReadAndWriteActivity : AppCompatActivity() {
    private val name = "lena.png"
    private lateinit var path: String
    private var source: Bitmap? = null


    private var currentMode = Imgcodecs.IMREAD_UNCHANGED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_and_write)
        supportActionBar?.title = intent.getStringExtra("title")
        path = cacheDir.path + File.separator + name

        FileUtil.resourceToFile(this, R.drawable.lena, path)
        onReedModeChange()
        bt_save.setOnClickListener { v ->
            saveToStorage()
        }

    }

    private fun onReedModeChange() {
        loadLenaFromFile()
        tv_mode.text = getModeName(currentMode)
    }

    private fun loadLenaFromFile() {
        if (!File(path).exists()) {
            "文件不存在".toast()
            return
        }
        val picFileToBitmap: Bitmap = FileUtil.picFileToBitmap(path)
        var width = 0
        var height = 0
        when (currentMode) {
            Imgcodecs.IMREAD_REDUCED_COLOR_2,
            Imgcodecs.IMREAD_REDUCED_GRAYSCALE_2 -> {
                width = picFileToBitmap.width / 2
                height = picFileToBitmap.height / 2
            }
            Imgcodecs.IMREAD_REDUCED_COLOR_4,
            Imgcodecs.IMREAD_REDUCED_GRAYSCALE_4 -> {
                width = picFileToBitmap.width / 4
                height = picFileToBitmap.height / 4
            }
            Imgcodecs.IMREAD_REDUCED_COLOR_8,
            Imgcodecs.IMREAD_REDUCED_GRAYSCALE_8 -> {
                width = picFileToBitmap.width / 8
                height = picFileToBitmap.height / 8
            }
            Imgcodecs.IMREAD_IGNORE_ORIENTATION,
            Imgcodecs.IMREAD_ANYDEPTH,
            Imgcodecs.IMREAD_GRAYSCALE,
            Imgcodecs.IMREAD_UNCHANGED,
            Imgcodecs.IMREAD_ANYCOLOR,
            Imgcodecs.IMREAD_LOAD_GDAL,
            Imgcodecs.IMREAD_COLOR -> {
                width = picFileToBitmap.width
                height = picFileToBitmap.height
            }
        }
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        source = ImageNativeUtils.imgRead(path, currentMode, bitmap)

        iv_lena.setImageBitmap(source)
    }

    private fun saveToStorage() {
        val file = File(cacheDir.path + File.separator + "${System.currentTimeMillis()}.jpg")
        if (!file.exists()) {
            file.createNewFile()
        }
        val imgWrite = ImageNativeUtils.imgWrite(file.path, source)
        if (imgWrite) {
            "保存成功${file.absolutePath}".logd()
        }else{
            "保存失败".loge()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_imread, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.IMREAD_UNCHANGED -> currentMode = Imgcodecs.IMREAD_UNCHANGED
            R.id.IMREAD_GRAYSCALE -> currentMode = Imgcodecs.IMREAD_GRAYSCALE
            R.id.IMREAD_COLOR -> currentMode = Imgcodecs.IMREAD_COLOR
            R.id.IMREAD_ANYDEPTH -> currentMode = Imgcodecs.IMREAD_ANYDEPTH
            R.id.IMREAD_ANYCOLOR -> currentMode = Imgcodecs.IMREAD_ANYCOLOR
            R.id.IMREAD_LOAD_GDAL -> currentMode = Imgcodecs.IMREAD_LOAD_GDAL
            R.id.IMREAD_REDUCED_GRAYSCALE_2 -> currentMode =
                    Imgcodecs.IMREAD_REDUCED_GRAYSCALE_2
            R.id.IMREAD_REDUCED_COLOR_2 -> currentMode = Imgcodecs.IMREAD_REDUCED_COLOR_2
            R.id.IMREAD_REDUCED_GRAYSCALE_4 -> currentMode =
                    Imgcodecs.IMREAD_REDUCED_GRAYSCALE_4
            R.id.IMREAD_REDUCED_COLOR_4 -> currentMode = Imgcodecs.IMREAD_REDUCED_COLOR_4
            R.id.IMREAD_REDUCED_GRAYSCALE_8 -> currentMode =
                    Imgcodecs.IMREAD_REDUCED_GRAYSCALE_8
            R.id.IMREAD_REDUCED_COLOR_8 -> currentMode = Imgcodecs.IMREAD_REDUCED_COLOR_8
            R.id.IMREAD_IGNORE_ORIENTATION -> currentMode =
                    Imgcodecs.IMREAD_IGNORE_ORIENTATION
        }
        onReedModeChange()
        return true
    }

    private fun getModeName(mode: Int): String {
        return when (mode) {
            Imgcodecs.IMREAD_UNCHANGED -> "IMREAD_UNCHANGED"
            Imgcodecs.IMREAD_GRAYSCALE -> "IMREAD_GRAYSCALE"
            Imgcodecs.IMREAD_COLOR -> "IMREAD_COLOR"
            Imgcodecs.IMREAD_ANYDEPTH -> "IMREAD_ANYDEPTH"
            Imgcodecs.IMREAD_ANYCOLOR -> "IMREAD_ANYCOLOR"
            Imgcodecs.IMREAD_LOAD_GDAL -> "IMREAD_LOAD_GDAL"
            Imgcodecs.IMREAD_REDUCED_GRAYSCALE_2 -> "IMREAD_REDUCED_GRAYSCALE_2"
            Imgcodecs.IMREAD_REDUCED_COLOR_2 -> "IMREAD_REDUCED_COLOR_2"
            Imgcodecs.IMREAD_REDUCED_GRAYSCALE_4 -> "IMREAD_REDUCED_GRAYSCALE_4"
            Imgcodecs.IMREAD_REDUCED_COLOR_4 -> "IMREAD_REDUCED_COLOR_4"
            Imgcodecs.IMREAD_REDUCED_GRAYSCALE_8 -> "IMREAD_REDUCED_GRAYSCALE_8"
            Imgcodecs.IMREAD_REDUCED_COLOR_8 -> "IMREAD_REDUCED_COLOR_8"
            Imgcodecs.IMREAD_IGNORE_ORIENTATION -> "IMREAD_IGNORE_ORIENTATION"
            else -> "IMREAD_UNCHANGED"
        }
    }


}