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
bool MatToBitmap(JNIEnv *env, cv::Mat &matrix, jobject obj_bitmap, jboolean needPremultiplyAlpha);

JNIEXPORT jobject JNICALL
JNI_API(imgRead)(JNIEnv *env, jobject type, jstring filename, jint flag, jobject bitmap) {
    const char *nativeScenePath = env->GetStringUTFChars(filename, 0);
    Mat src = imread(nativeScenePath, flag);
    MatToBitmap(env, src, bitmap, false);
    return bitmap;
}

JNIEXPORT jboolean JNICALL
JNI_API(imgWrite)(JNIEnv *env, jobject type, jstring filename, jobject bitmap) {
    const char *nativeScenePath = env->GetStringUTFChars(filename, 0);
    Mat src;
    BitmapToMat(env, bitmap, src);
    /**
     * 第一个参数const String& filename表示需要写入的文件名，必须要加上后缀，比如“123.png”。
     * 第二个参数InputArray img表示Mat类型的图像数据。
     * 第三个参数const std::vector& params表示为特定格式保存的参数编码
     */
    bool write = imwrite(nativeScenePath, src);
    return write;
}

JNIEXPORT jobject JNICALL
JNI_API(imgColor)(JNIEnv *env, jobject type, jobject bitmap1, jobject out_bitmap) {
    Mat src;
    Mat out;
    BitmapToMat(env, bitmap1, src);
    cv::cvtColor(src, out, COLOR_BGR2RGB);
    MatToBitmap(env, out, out_bitmap, false);
    return out_bitmap;
}

JNIEXPORT jobject JNICALL
JNI_API(bitwiseNot)(JNIEnv *env, jobject type, jobject bitmap1, jobject out_bitmap) {
    Mat src;
    Mat out;
    BitmapToMat(env, bitmap1, src);
    cv::cvtColor(src, src, COLOR_BGR2RGB);
    cv::bitwise_not(src, out);
    MatToBitmap(env, out, out_bitmap, false);
    return out_bitmap;
}

JNIEXPORT jobject JNICALL
JNI_API(bitwiseAnd)(JNIEnv *env, jobject type, jobject bitmap1, jobject bitmap2,
                    jobject out_bitmap) {
    Mat in1;
    Mat in2;
    Mat out;
    BitmapToMat(env, bitmap1, in1);
    BitmapToMat(env, bitmap2, in2);
    cv::bitwise_and(in1, in2, out);
    MatToBitmap(env, out, out_bitmap, false);
    return out_bitmap;
}

JNIEXPORT jobject JNICALL
JNI_API(bitwiseOr)(JNIEnv *env, jobject type, jobject bitmap1, jobject bitmap2,
                   jobject out_bitmap) {
    Mat in1;
    Mat in2;
    Mat out;
    BitmapToMat(env, bitmap1, in1);
    BitmapToMat(env, bitmap2, in2);
    cv::bitwise_or(in1, in2, out);
    MatToBitmap(env, out, out_bitmap, false);
    return out_bitmap;
}

JNIEXPORT jobject JNICALL
JNI_API(bitwiseXor)(JNIEnv *env, jobject type, jobject bitmap1, jobject bitmap2,
                    jobject out_bitmap) {
    Mat in1;
    Mat in2;
    Mat out;
    BitmapToMat(env, bitmap1, in1);
    cv::cvtColor(in1, in1, COLOR_BGR2RGB);
    BitmapToMat(env, bitmap2, in2);
    cv::cvtColor(in2, in2, COLOR_BGR2RGB);
    bitwise_xor(in1, in2, out);
    MatToBitmap(env, out, out_bitmap, false);
    return out_bitmap;
}

JNIEXPORT jobject JNICALL
JNI_API(add)(JNIEnv *env, jobject type, jobject bitmap1, jobject bitmap2,
             jobject out_bitmap) {
    Mat in1;
    Mat in2;
    Mat out;
    BitmapToMat(env, bitmap1, in1);
    BitmapToMat(env, bitmap2, in2);
    cv::add(in1, in2, out);//out = in1 + in2
    MatToBitmap(env, out, out_bitmap, false);
    return out_bitmap;
}

JNIEXPORT jobject JNICALL
JNI_API(subtract)(JNIEnv *env, jobject type, jobject bitmap1, jobject bitmap2,
                  jobject out_bitmap) {
    Mat in1;
    Mat in2;
    Mat out;
    BitmapToMat(env, bitmap1, in1);
    cv::cvtColor(in1, in1, COLOR_BGR2RGB);
    BitmapToMat(env, bitmap2, in2);
    cv::cvtColor(in2, in2, COLOR_BGR2RGB);
    cv::subtract(in1, in2, out);
    MatToBitmap(env, out, out_bitmap, false);
    in1.release();
    in2.release();
    out.release();
    return out_bitmap;
}

JNIEXPORT jobject JNICALL
JNI_API(multiply)(JNIEnv *env, jobject type, jobject bitmap1, jobject bitmap2,
                  jobject out_bitmap) {
    Mat in1;
    Mat in2;
    Mat out;
    BitmapToMat(env, bitmap1, in1);
    BitmapToMat(env, bitmap2, in2);
    cv::multiply(in1, in2, out);
    MatToBitmap(env, out, out_bitmap, false);
    return out_bitmap;
}

JNIEXPORT jobject JNICALL
JNI_API(divide)(JNIEnv *env, jobject type, jobject bitmap1, jobject bitmap2,
                jobject out_bitmap) {
    Mat in1;
    Mat in2;
    Mat out;
    BitmapToMat(env, bitmap1, in1);
    BitmapToMat(env, bitmap2, in2);
    cv::divide(in1, in2, out, 50.0, -1);
    convertScaleAbs(out, out);
    MatToBitmap(env, out, out_bitmap, false);
    return out_bitmap;
}


JNIEXPORT jobject JNICALL
JNI_API(addWeighted)(JNIEnv *env, jobject type, jobject bitmap1, jdouble alpha, jobject bitmap2,
                     jdouble beta, jdouble gamma,
                     jobject out_bitmap) {
    Mat in1;
    Mat in2;
    Mat out;
    BitmapToMat(env, bitmap1, in1);
    BitmapToMat(env, bitmap2, in2);
    cv::addWeighted(in1, alpha, in2, beta, gamma, out);
    MatToBitmap(env, out, out_bitmap, false);
    return out_bitmap;
}

JNIEXPORT jstring JNICALL
JNI_API(getImgInfo)(JNIEnv *env, jobject type, jobject bitmap) {
    Mat src;
    BitmapToMat(env, bitmap, src);
    std::string result_str = "";
    char str[10];
    snprintf(str, 10, "%d", src.cols);
    result_str.append("cols:%d", src.cols);//列数
    result_str.append(",");
    result_str.append("rows:", src.rows);//行数
    result_str.append(",");
    result_str.append("channels:", src.channels());//通道数
    result_str.append(",");
    result_str.append("depth:", src.depth());//深度
//    result_str.append(",");
    return env->NewStringUTF(result_str.c_str());
}
bool MatToBitmap(JNIEnv *env, cv::Mat &matrix, jobject obj_bitmap, jboolean needPremultiplyAlpha) {
    void *bitmapPixels;
    AndroidBitmapInfo bitmapInfo;
    ASSERT_FALSE(AndroidBitmap_getInfo(env, obj_bitmap, &bitmapInfo) >= 0);
    ASSERT_FALSE(bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888 ||
                 bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGB_565);
    ASSERT_FALSE(matrix.dims == 2);// 必须是 2 维矩阵，长宽一致
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

    LOGD("rows:%dcols:%d,channels:%d", matrix.rows, matrix.cols, matrix.channels());
    if (bitmapInfo.format == ANDROID_BITMAP_FORMAT_RGBA_8888) {
        Mat tmp(bitmapInfo.width, bitmapInfo.height, CV_8UC4, bitmapPixels);
        if (matrix.type() == CV_8UC1) {
            LOGD("nMatToBitmap: CV_8UC1 -> RGBA_8888");
            cvtColor(matrix, tmp, COLOR_GRAY2RGBA);
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
        } else {
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
        } else {
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