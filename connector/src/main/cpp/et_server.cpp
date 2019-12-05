//
// Created by Administrator on 2019/12/5.
//
#include <jni.h>
#include "log.h"
#include <iostream>
#include <thread>

static bool isRunning = false;

static void test();

static void test2();


#ifdef __cplusplus
extern "C" {
#endif

/*JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {

    LOGI("%s", "---------------->  JNI_OnLoad");


    return JNI_VERSION_1_6;

}


JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {

    LOGI("%s", "JNI_OnUnload  <----------------");

}*/


JNIEXPORT void JNICALL
Java_com_xdynamics_connector_ETServerNative_nativeInit(JNIEnv *env, jobject obj) {

    LOGI("%s", " ET Server --------init-------->");

    isRunning = true;

}

JNIEXPORT void JNICALL
Java_com_xdynamics_connector_ETServerNative_nativeDestroy(JNIEnv *env, jobject obj) {

    LOGI("%s", " ET Server --------destroy-------->");

    isRunning = false;

}


JNIEXPORT jboolean JNICALL
Java_com_xdynamics_connector_ETServerNative_nativeIsRunning(JNIEnv *env, jobject obj) {

    LOGI("%s", " ET Server --------IsRunning-------->");

    return static_cast<jboolean>(isRunning ? JNI_TRUE : JNI_FALSE);

}


JNIEXPORT void JNICALL
Java_com_xdynamics_connector_ETServerNative_nativeTest(JNIEnv *env, jobject obj) {

    LOGI("%s", " ET Server --------test-------->");

    std::thread([]() {

        test();

    }).detach();

}


#ifdef __cplusplus
}
#endif


#include <sys/types.h>
#include <unistd.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <sys/un.h>
#include <thread>
#include <cstring>
#include <algorithm>
#include <functional>

#define PROTOCOL 0
#define PORT 9999
#define DOMAIN AF_INET
#define TYPE  SOCK_STREAM
#define S_IP "127.0.0.1"


template<typename __Type>
class ToUpper {
public:
    inline __Type operator()(__Type &__t) {
        return toupper(__t);
    }
};


static void test() {

    int listen_fd;
    int c_fd;

    struct sockaddr_in sockAddr{};
    struct sockaddr_in acceptAddr{};

    socklen_t c_addr_len;

    char buf[BUFSIZ];

    memset(buf, 0, BUFSIZ);

    memset(&sockAddr, 0, sizeof(sockAddr));

    memset(&acceptAddr, 0, sizeof(acceptAddr));

    listen_fd = socket(DOMAIN, TYPE | SOCK_CLOEXEC, PROTOCOL);

    if (listen_fd < 0) {
        perror("create socket failure !");

        exit(errno);
    }

    sockAddr.sin_family = AF_INET;

    sockAddr.sin_port = htons(PORT);

    sockAddr.sin_addr.s_addr = htonl(INADDR_ANY);

    if (bind(listen_fd, (struct sockaddr *) &sockAddr, sizeof(sockAddr)) != 0) {
        perror("bind socket failure !");

        exit(errno);
    }

    if (listen(listen_fd, 200) != 0) {
        perror("listen socket failure !");

        exit(errno);
    }

    c_fd = accept4(listen_fd, (struct sockaddr *) &acceptAddr, &c_addr_len, SOCK_CLOEXEC);

    if (c_fd < 0) {
        perror("accept socket failure !");

        exit(errno);
    }

    while (isRunning) {

        const size_t &len = static_cast<const size_t &>(read(c_fd, buf, sizeof(buf)));

        if (len == 0) continue;

        if (strstr(buf, "exit")) {
            break;
        }

        printf("%s\n", buf);

        std::transform(buf, buf + len, buf, ToUpper<char>());

        write(c_fd, buf, len);

        memset(buf, 0, len);
    }

    close(c_fd);

    close(listen_fd);

    printf("exit!!!");


}


#include <sys/epoll.h>

#define MAX_CONNECT_NUM 10

static void test2() {

    int listen_fd;

    listen_fd = socket(DOMAIN, TYPE | SOCK_CLOEXEC, PROTOCOL);



    int epoll_fd = epoll_create1(EPOLL_CLOEXEC);


}


