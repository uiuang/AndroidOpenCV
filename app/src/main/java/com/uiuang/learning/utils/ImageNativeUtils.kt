package com.uiuang.learning.utils

import android.graphics.Bitmap

object ImageNativeUtils {

    init {
        System.loadLibrary("native-lib")
    }

    external fun imgRead(fileName: String, flag: Int,bitmap: Bitmap): Bitmap?

    external fun imgWrite(fileName: String,bitmap: Bitmap?):Boolean


}