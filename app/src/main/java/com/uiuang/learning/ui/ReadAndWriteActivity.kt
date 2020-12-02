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
import com.uiuang.learning.utils.FileUtil
import com.uiuang.learning.utils.ImageNativeUtils
import com.uiuang.learning.utils.Imgcodecs
import kotlinx.android.synthetic.main.activity_read_and_write.*
import java.io.File
import java.io.FileOutputStream

/**
 * 读写图像
 */
class ReadAndWriteActivity : AppCompatActivity() {
    private val sLeanName = "lena.png"
    private lateinit var mLenaPath: String
    private var currentImreadMode = Imgcodecs.IMREAD_UNCHANGED
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_and_write)
        supportActionBar?.title = intent.getStringExtra("title")
        mLenaPath = cacheDir.path + File.separator + sLeanName

        drawableToFile(
                R.drawable.lena,
                sLeanName
        )
        onImreadModeChange()
        bt_save.setOnClickListener { v ->
            onImreadModeChange()
        }

    }

    private fun onImreadModeChange() {
        loadLenaFromFile()
        tv_mode.text = getModeName(currentImreadMode)
    }

    private fun loadLenaFromFile() {
        if (!File(mLenaPath).exists()) {
            Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show()
            return
        }
        var picFileToBitmap: Bitmap = FileUtil.picFileToBitmap(mLenaPath)
        var width: Int = 0
        var height: Int = 0
        when (currentImreadMode) {
            Imgcodecs.IMREAD_REDUCED_COLOR_2,
            Imgcodecs.IMREAD_REDUCED_GRAYSCALE_2->{
                width = picFileToBitmap.width / 2
                height = picFileToBitmap.height / 2
            }
            Imgcodecs.IMREAD_REDUCED_COLOR_4,
            Imgcodecs.IMREAD_REDUCED_GRAYSCALE_4->{
                width = picFileToBitmap.width / 4
                height = picFileToBitmap.height / 4
            }
            Imgcodecs.IMREAD_REDUCED_COLOR_8,
            Imgcodecs.IMREAD_REDUCED_GRAYSCALE_8->{
                width = picFileToBitmap.width / 8
                height = picFileToBitmap.height / 8
            }
            Imgcodecs.IMREAD_IGNORE_ORIENTATION,
            Imgcodecs.IMREAD_ANYDEPTH,
            Imgcodecs.IMREAD_GRAYSCALE,
            Imgcodecs.IMREAD_UNCHANGED,
            Imgcodecs.IMREAD_ANYCOLOR,
            Imgcodecs.IMREAD_LOAD_GDAL,
            Imgcodecs.IMREAD_COLOR->{
                width = picFileToBitmap.width
                height = picFileToBitmap.height
            }
        }
        val bitmap =
                Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        var bitmap1 = ImageNativeUtils.imgRead(mLenaPath, currentImreadMode, bitmap)
        Log.d("bitmap", "width:${bitmap1?.width},height:${bitmap1?.height}")

//        currentMat = Imgcodecs.imread(mLenaPath, currentImreadMode)
//        Utils.matToBitmap(currentMat, bitmap)
        iv_lena.setImageBitmap(bitmap1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_imread, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.IMREAD_UNCHANGED -> currentImreadMode = Imgcodecs.IMREAD_UNCHANGED
            R.id.IMREAD_GRAYSCALE -> currentImreadMode = Imgcodecs.IMREAD_GRAYSCALE
            R.id.IMREAD_COLOR -> currentImreadMode = Imgcodecs.IMREAD_COLOR
            R.id.IMREAD_ANYDEPTH -> currentImreadMode = Imgcodecs.IMREAD_ANYDEPTH
            R.id.IMREAD_ANYCOLOR -> currentImreadMode = Imgcodecs.IMREAD_ANYCOLOR
            R.id.IMREAD_LOAD_GDAL -> currentImreadMode = Imgcodecs.IMREAD_LOAD_GDAL
            R.id.IMREAD_REDUCED_GRAYSCALE_2 -> currentImreadMode =
                    Imgcodecs.IMREAD_REDUCED_GRAYSCALE_2
            R.id.IMREAD_REDUCED_COLOR_2 -> currentImreadMode = Imgcodecs.IMREAD_REDUCED_COLOR_2
            R.id.IMREAD_REDUCED_GRAYSCALE_4 -> currentImreadMode =
                    Imgcodecs.IMREAD_REDUCED_GRAYSCALE_4
            R.id.IMREAD_REDUCED_COLOR_4 -> currentImreadMode = Imgcodecs.IMREAD_REDUCED_COLOR_4
            R.id.IMREAD_REDUCED_GRAYSCALE_8 -> currentImreadMode =
                    Imgcodecs.IMREAD_REDUCED_GRAYSCALE_8
            R.id.IMREAD_REDUCED_COLOR_8 -> currentImreadMode = Imgcodecs.IMREAD_REDUCED_COLOR_8
            R.id.IMREAD_IGNORE_ORIENTATION -> currentImreadMode =
                    Imgcodecs.IMREAD_IGNORE_ORIENTATION
        }
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

    /**
     * Drawable to File
     */
    private fun drawableToFile(drawableId: Int, fileName: String): File? {
        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, drawableId)
        val defaultImgPath = "$cacheDir/$fileName"
        val file = File(defaultImgPath)
        val fOut = FileOutputStream(file)
        try {
            file.createNewFile()
            bitmap.compress(Bitmap.CompressFormat.PNG, 20, fOut)
            fOut.flush()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            fOut.close()
        }
        return file
    }
}