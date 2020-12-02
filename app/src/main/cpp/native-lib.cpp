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
bool MatToBitmap(JNIEnv *env, cv::Mat &matrix, jobject obj_bitmap,jboolean needPremultiplyAlpha);

JNIEXPORT jobject JNICALL
JNI_API(imgRead)(JNIEnv *env, jobject type, jstring filename, jint flag, jobject bitmap) {
//    Mat src;
//    BitmapToMat(env, bitmap, src);
    const char *nativeScenePath = env->GetStringUTFChars(filename, 0);
    Mat src = imread(nativeScenePath, flag);
    jintArray data = reinterpret_cast<jintArray>(src.data);
    for (int i = 0; i < 10; ++i) {
        LOGD("%d", data[i]);
    }
//    cv::addWeighted(src, 0.1, src1, 0.3, 0.0, src);
    MatToBitmap(env, src, bitmap, false);
    return bitmap;
}

bool MatToBitmap(JNIEnv *env, cv::Mat &matrix, jobject obj_bitmap,jboolean needPremultiplyAlpha) {
    void *bitmapPixels;
    AndroidBitmapInfo bitmapInfo;
    ASSERT_FALSE(AndroidBitmap_getInfo(env, obj_bitmap, &bitmapInfo) >= 0);
    ASSERT_FALSE(bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
                 bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGB_565);
    ASSERT_FALSE(matrix.dims == 2 );// 必须是 2 维矩阵，长宽一致
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

    LOGD("rows:%dcols:%d,channels:%d",matrix.rows,matrix.cols,matrix.channels());
    if (bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        if (matrix.channels() == 0) {

        }
        Mat tmp(matrix.cols, matrix.rows,  CV_8UC4, bitmapPixels);
//        bitmapInfo.width = matrix.rows;
//        bitmapInfo.height = matrix.cols;
        Mat sz(Size(bitmapInfo.height, bitmapInfo.width), CV_8UC4);
        int left = (sz.rows - matrix.rows) / 2;
        int top = (sz.cols - matrix.cols) / 2;
        int right = sz.rows - left;
        int bottom = sz.cols - top;
        LOGD("%d,%d,%d,%d",left,top,right,bottom);
//        for (int row1 = 0; row1 < matrix.rows; ++row1) {
//            for (int col1 = 0; col1 < matrix.cols; ++col1) {
//                Vec4b src_pix  = matrix.at<Vec4b>(row1, col1);
//                for (int row = left; row < right; ++row) {
//                    for (int col = top; col < bottom; ++col) {
//                        sz.at<Vec4b>(row , col ) = src_pix;
//                    }
//                }
//            }
//        }


        if (matrix.type() == CV_8UC1) {
            LOGD("nMatToBitmap: CV_8UC1 -> RGBA_8888");

            cvtColor( matrix, tmp, COLOR_GRAY2RGBA);
//            cv::convertScaleAbs(matrix,tmp,COLOR_GRAY2RGBA)
        } else if (matrix.type() == CV_8UC3) {
            LOGD("nMatToBitmap: CV_8UC3 -> RGBA_8888");
            cvtColor(matrix, tmp, COLOR_RGB2RGBA);
        } else if (matrix.type() == CV_8UC4) {
            LOGD("nMatToBitmap: CV_8UC4 -> RGBA_8888");
            if (needPremultiplyAlpha)
                cvtColor(matrix, tmp, COLOR_RGBA2mRGBA);
            else
                matrix.copyTo(tmp);
        } else{
            matrix.copyTo(tmp);
        }
    } else {
        // info.format == ANDROID_BITMAP_FORMAT_RGB_565
        Mat tmp(bitmapInfo.height, bitmapInfo.width, CV_8UC2, bitmapPixels);
        if (matrix.type() == CV_8UC1) {
            LOGD("nMatToBitmap: CV_8UC1 -> RGB_565");
            cvtColor(matrix, tmp, COLOR_GRAY2BGR565);
        } else if (matrix.type() == CV_8UC3) {
            LOGD("nMatToBitmap: CV_8UC3 -> RGB_565");
            cvtColor(matrix, tmp, COLOR_RGB2BGR565);
        } else if (matrix.type() == CV_8UC4) {
            LOGD("nMatToBitmap: CV_8UC4 -> RGB_565");
            cvtColor(matrix, tmp, COLOR_RGBA2BGR565);
        } else{
            matrix.copyTo(tmp);
        }
    }
    AndroidBitmap_unlockPixels(env, obj_bitmap);
    return true;
}
bool BitmapToMat(JNIEnv *env, jobject obj_bitmap, cv::Mat &matrix) {
    void *bitmapPixels;//保存图片像素
    AndroidBitmapInfo bitmapInfo;//保存图片参数
    ASSERT_FALSE(AndroidBitmap_getInfo(env, obj_bitmap, &bitmapInfo) >= 0);//获取图片参数
    ASSERT_FALSE(bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
                 bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGB_565);//只支持 ARGB_8888和RGB_565
    ASSERT_FALSE(AndroidBitmap_lockPixels(env, obj_bitmap, &bitmapPixels) >= 0);//获取图片参数（锁定内存块）
    ASSERT_FALSE(bitmapPixels);
    if (bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        cv::Mat tmp(bitmapInfo.height, bitmapInfo.width, CV_8UC4, bitmapPixels);//建立临时mat
        tmp.copyTo(matrix);//拷贝目标matrix
    } else {
        cv::Mat tmp(bitmapInfo.height, bitmapInfo.width, CV_8UC2, bitmapPixels);
        cv::cvtColor(tmp, matrix, cv::COLOR_BGRA2BGR565);
    }
    AndroidBitmap_unlockPixels(env, obj_bitmap);//解锁
    return true;

}
}