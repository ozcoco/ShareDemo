#include <jni.h>
#import "log.h"
#include <Crc32.h>

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
    
    LOGI("%d", crc32_fast(data, sizeof(data)));
}

#ifdef __cplusplus
}
#endif