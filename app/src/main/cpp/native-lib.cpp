#include <jni.h>
#include <string>
#include <android/bitmap.h>
#include <android/log.h>
#include <opencv2/imgcodecs.hpp>
#include <opencv2/imgproc.hpp>

using namespace cv;
using namespace std;

#define LOGD(...) ((void)__android_log_print(ANDROID_LOG_DEBUG, "debug", __VA_ARGS__))

#define JNI_API(name) Java_com_uiuang_learning_utils_ImageNativeUtils_##name

#define ASSERT(status, ret) if(!(status)){return ret;}
#define ASSERT_FALSE(status) ASSERT(status,false)

extern "C" {
bool BitmapToMat(JNIEnv *env, jobject obj_bitmap, cv::Mat &matrix);
bool MatToBitmap(JNIEnv *env, cv::Mat &matrix, jobject obj_bitmap);

JNIEXPORT jobject JNICALL JNI_API(imgRead)(JNIEnv *env, jobject type, jstring filename, jint flag,jobject bitmap) {
    Mat src= imread((const String) reinterpret_cast<const char *>(filename), flag);
    MatToBitmap(env, src, bitmap);
    return bitmap;
}

bool MatToBitmap(JNIEnv *env, cv::Mat &matrix, jobject obj_bitmap) {
    void *bitmapPixels;
    AndroidBitmapInfo bitmapInfo;
    ASSERT_FALSE(AndroidBitmap_getInfo(env, obj_bitmap, &bitmapInfo) >= 0);
    ASSERT_FALSE(bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
                 bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGB_565);
    ASSERT_FALSE(matrix.dims == 2 && bitmapInfo.height == (uint32_t) matrix.rows &&
                 bitmapInfo.width == (uint32_t) matrix.cols);// 必须是 2 维矩阵，长宽一致
    ASSERT_FALSE(matrix.type() == CV_8UC1 || matrix.type() == CV_8UC3 || matrix.type() == CV_8UC4);
    ASSERT_FALSE(AndroidBitmap_lockPixels(env, obj_bitmap, &bitmapPixels) >= 0);
    ASSERT_FALSE(bitmapPixels);
    //CV_8UC1，CV_8UC2，CV_8UC3。 其中 U代表 Unsigned 无符号、C代表CvMat 后面的数字代表通道数
    //（最后的1、2、3表示通道数，譬如RGB3通道就用CV_8UC3）
    //1--bit_depth---比特数---代表8bite,16bites,32bites,64bites---举个例子吧--比如说,如
    //        如果你现在创建了一个存储--灰度图片的Mat对象,这个图像的大小为宽100,高100,那么,现在这张
    //        灰度图片中有10000个像素点，它每一个像素点在内存空间所占的空间大小是8bite,8位--所以它对
    //        应的就是CV_8
    //     2--S|U|F--S--代表---signed int---有符号整形
    //               U--代表--unsigned int--无符号整形
    //               F--代表--float---------单精度浮点型
    //     3--C<number_of_channels>----代表---一张图片的通道数,比如:
    //         1--灰度图片--grayImg---是--单通道图像
    //         2--RGB彩色图像---------是--3通道图像
    //         3--带Alph通道的RGB图像--是--4通道图像
    if (bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        cv::Mat tmp(bitmapInfo.height, bitmapInfo.width, CV_8UC4, bitmapPixels);
        switch (matrix.type()) {
            case CV_8UC1:
//                cv::cvtColor(matrix, tmp, CV_GRAY2BGRA);
                cv::cvtColor(matrix, tmp, cv::COLOR_GRAY2BGRA);
                break;
            case CV_8UC2:
                cv::cvtColor(matrix, tmp, cv::COLOR_BGR5652BGRA);
                break;
            case CV_8UC3:
                cv::cvtColor(matrix, tmp, cv::COLOR_RGB2BGRA);
                break;
            case CV_8UC4:
                matrix.copyTo(tmp);
                break;
            default:
                AndroidBitmap_unlockPixels(env, obj_bitmap);
                return false;
        }
    } else {
        cv::Mat tmp(bitmapInfo.height, bitmapInfo.width, CV_8UC2, bitmapPixels);
        switch (matrix.type()) {
            case CV_8UC1:
                cv::cvtColor(matrix, tmp, cv::COLOR_GRAY2BGR565);
                break;
            case CV_8UC2:
                matrix.copyTo(tmp);
                break;
            case CV_8UC3:
                cv::cvtColor(matrix, tmp, cv::COLOR_RGB2BGR565);
                break;
            case CV_8UC4:
                //对应bitmap   RGBA_8888
//                cv::cvtColor(mat, tmp, CV_BGRA2BGR565);
                cv::cvtColor(matrix, tmp, cv::COLOR_BGRA2BGR565);
                break;
            default:
                AndroidBitmap_unlockPixels(env, obj_bitmap);
                return false;
        }
    }
    AndroidBitmap_unlockPixels(env, obj_bitmap);
    return true;
}
}