#include <jni.h>

JNIEXPORT jstring JNICALL
Java_com_ivanmagda_androidopencv_MainActivity_getMsgFromJni(
        JNIEnv *env,
        jobject instance) {
   return (*env)->NewStringUTF(env, "Hello From JNI");
}
