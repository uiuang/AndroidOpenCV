package com.uiuang.learning.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import java.io.File
import java.io.FileOutputStream

object FileUtil {
    fun picFileToBitmap(picFilePath: String?): Bitmap {
        val options = BitmapFactory.Options()
        options.inDither = true
        options.inPreferredConfig = Bitmap.Config.ARGB_8888
        // 通过path得到一个不超过2000*2000的Bitmap
        return decodeSampledBitmapFromFile(picFilePath, options, 2000, 2000)
    }


    fun resourceToBitmap(context: Context, resId: Int): Bitmap {
        val options = BitmapFactory.Options()
        val value = TypedValue()
        context.resources.openRawResource(resId, value)
        options.inTargetDensity = value.density
        options.inScaled = false //不缩放
        return BitmapFactory.decodeResource(context.resources, resId, options)
    }

    fun resourceToFile(context: Context,resId: Int, defaultImgPath: String) {
        val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, resId)
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
    }


    private fun decodeSampledBitmapFromFile(imgPath: String?, options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Bitmap {
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imgPath, options)
        // inSampleSize为缩放比例，举例：options.inSampleSize = 2表示缩小为原来的1/2，3则是1/3，以此类推
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(imgPath, options)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1
        while (height / inSampleSize > reqHeight || width / inSampleSize > reqWidth) {
            inSampleSize *= 2
        }
        println("inSampleSize=$inSampleSize")
        return inSampleSize
    }
}