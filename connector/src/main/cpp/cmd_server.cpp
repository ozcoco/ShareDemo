//
// Created by Administrator on 2019/12/5.
//
#include <jni.h>
#include "log.h"
#include <iostream>
#include <thread>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {

    LOGI("%s", "---------------->  JNI_OnLoad");


    return JNI_VERSION_1_6;

}


JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {

    LOGI("%s", "JNI_OnUnload  <----------------");

}


JNIEXPORT void JNICALL
Java_com_xdynamics_connector_CMDServerNative_nativeInit(JNIEnv *env, jobject obj) {

    LOGI("%s", " ET Server --------init-------->");


}

JNIEXPORT void JNICALL
Java_com_xdynamics_connector_CMDServerNative_nativeDestroy(JNIEnv *env, jobject obj) {

    LOGI("%s", " ET Server --------destroy-------->");

}


JNIEXPORT jboolean JNICALL
Java_com_xdynamics_connector_CMDServerNative_nativeIsRunning(JNIEnv *env, jobject obj) {

    LOGI("%s", " ET Server --------IsRunning-------->");

    return JNI_FALSE;

}


JNIEXPORT void JNICALL
Java_com_xdynamics_connector_CMDServerNative_nativeTest(JNIEnv *env, jobject obj) {

    LOGI("%s", "  Server --------test-------->");

    std::thread([]() {


    }).detach();

}

#ifdef __cplusplus
}
#endif
