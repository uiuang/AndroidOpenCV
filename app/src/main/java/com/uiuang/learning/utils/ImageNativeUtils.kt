package com.uiuang.learning.utils

import android.graphics.Bitmap

object ImageNativeUtils {

    init {
        System.loadLibrary("native-lib")
    }

    external fun imgRead(fileName: String, flag: Int, bitmap: Bitmap): Bitmap

    external fun imgWrite(fileName: String, bitmap: Bitmap?): Boolean

    external fun imgColor(bitmap1: Bitmap, out_bitmap: Bitmap): Bitmap

    /**
     * bitwise_not是对二进制数据进行“非”操作，
     * 即对图像（灰度图像或彩色图像均可）每个像素值进行二进制“非”操作，~1=0，~0=1
     */
    external fun bitwiseNot(bitmap1: Bitmap, out_bitmap: Bitmap): Bitmap

    /**
     * bitwise_and是对二进制数据进行“与”操作，
     * 即对图像（灰度图像或彩色图像均可）每个像素值进行二进制“与”操作，1&1=1，1&0=0，0&1=0，0&0=0
     */
    external fun bitwiseAnd(bitmap1: Bitmap, bitmap2: Bitmap, out_bitmap: Bitmap): Bitmap

    /**
     * bitwise_or是对二进制数据进行“或”操作，
     * 即对图像（灰度图像或彩色图像均可）每个像素值进行二进制“或”操作，1|1=1，1|0=0，0|1=0，0|0=0
     */
    external fun bitwiseOr(bitmap1: Bitmap, bitmap2: Bitmap, out_bitmap: Bitmap): Bitmap

    /**
     * bitwise_xor是对二进制数据进行“异或”操作，
     * 即对图像（灰度图像或彩色图像均可）每个像素值进行二进制“异或”操作，1^1=0,1^0=1,0^1=1,0^0=0
     */
    external fun bitwiseXor(bitmap1: Bitmap, bitmap2: Bitmap, out_bitmap: Bitmap): Bitmap
    external fun add(bitmap1: Bitmap, bitmap2: Bitmap, out_bitmap: Bitmap): Bitmap
    external fun subtract(bitmap1: Bitmap, bitmap2: Bitmap, out_bitmap: Bitmap): Bitmap
    external fun multiply(bitmap1: Bitmap, bitmap2: Bitmap, out_bitmap: Bitmap): Bitmap
    external fun divide(bitmap1: Bitmap, bitmap2: Bitmap, out_bitmap: Bitmap): Bitmap

    //    external fun scaleAdd(bitmap1: Bitmap,bitmap2: Bitmap,out_bitmap: Bitmap):Bitmap
    external fun addWeighted(bitmap1: Bitmap, alpha: Double, bitmap2: Bitmap, beta: Double, gamma: Double, out_bitmap: Bitmap): Bitmap


}