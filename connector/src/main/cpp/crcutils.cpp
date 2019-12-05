#include <jni.h>
#include "log.h"

extern "C" {
#include "crc/checksum.h"
}

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
Java_com_xdynamics_connector_utils_CRCUtils_test(JNIEnv *env, jclass type) {

    const unsigned char data[] = "122455345654sfdgf53213txqezx2";

    LOGI("%d", crc_16(data, sizeof(data)));

}

JNIEXPORT jchar JNICALL
Java_com_xdynamics_connector_utils_CRCUtils_crc16(JNIEnv *env, jclass type, jstring str) {

    const char *data = env->GetStringUTFChars(str, JNI_FALSE);

    const int32_t &len = env->GetStringLength(str);

    jchar crc16Code = crc_16(reinterpret_cast<const unsigned char *>(data), (size_t) len);

    LOGI("%d", crc16Code);

    env->ReleaseStringUTFChars(str, data);

    return crc16Code;
}


#ifdef __cplusplus
}
#endif