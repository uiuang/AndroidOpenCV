package com.uiuang.learning.utils

class Imgcodecs {
    companion object{
        val IMREAD_UNCHANGED = -1//返回原始图像。alpha通道不会被忽略，如果有的话。
        val IMREAD_GRAYSCALE = 0//返回灰度图像
        val IMREAD_COLOR = 1//返回通道顺序为BGR的彩色图像
        val IMREAD_ANYDEPTH = 2 //当输入具有相应的深度时返回16位/ 32位图像，否则将其转换为8位。
        val IMREAD_ANYCOLOR = 4//则以任何可能的颜色格式读取图像。
        val IMREAD_LOAD_GDAL = 8//使用GDAL的驱动加载图像。
        val IMREAD_REDUCED_GRAYSCALE_2 = 16 //将图像转换为单通道灰度图像，图像大小减少1/2。
        val IMREAD_REDUCED_COLOR_2 = 17  //转换图像的3通道BGR彩色图像和图像的大小减少1/2。
        val IMREAD_REDUCED_GRAYSCALE_4 = 32 //将图像转换为单通道灰度图像，图像大小减少1/4。
        val IMREAD_REDUCED_COLOR_4 = 33 //转换图像的3通道BGR彩色图像和图像的大小减少1/4。
        val IMREAD_REDUCED_GRAYSCALE_8 = 64  //将图像转换为单通道灰度图像，图像大小减少1/8。
        val IMREAD_REDUCED_COLOR_8 = 65  //转换图像的3通道BGR色彩图像和图像大小减少1/8。
        val IMREAD_IGNORE_ORIENTATION = 128  //不旋转图像根据EXIF的定位标志。
    }

}