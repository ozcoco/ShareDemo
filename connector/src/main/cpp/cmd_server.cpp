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

        test2();

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
#include <fcntl.h>


#define MAX_CONNECT_NUM 10
#define MAX_EVENTS 10


static void do_use_fd(int epoll_fd, int fd, uint32_t events, void *ptr);


static void test2() {

    int sock_fd;

    struct sockaddr_in sockAddr{};
    struct sockaddr_in acceptAddr{};

    socklen_t c_addr_len;

    memset(&sockAddr, 0, sizeof(sockAddr));

    memset(&acceptAddr, 0, sizeof(acceptAddr));

    sock_fd = socket(DOMAIN, TYPE | SOCK_CLOEXEC, PROTOCOL);

    if (sock_fd < 0) {
        perror("create socket failure !");

        exit(errno);
    }

    unsigned int value = 1;
    setsockopt(sock_fd, SOL_SOCKET, SO_REUSEADDR, &value, sizeof(value));

    sockAddr.sin_family = AF_INET;

    sockAddr.sin_port = htons(PORT);

    sockAddr.sin_addr.s_addr = htonl(INADDR_ANY);

    if (bind(sock_fd, (struct sockaddr *) &sockAddr, sizeof(sockAddr)) != 0) {
        perror("bind socket failure !");

        exit(errno);
    }

    if (listen(sock_fd, 200) != 0) {
        perror("listen socket failure !");

        exit(errno);
    }


    int epoll_fd = epoll_create1(EPOLL_CLOEXEC);

    if (epoll_fd < 0) {

        LOGE("create epoll failure !");

        exit(errno);
    }

    struct epoll_event ev{}, events[MAX_EVENTS];

    ev.events = EPOLLIN;

    ev.data.fd = sock_fd;

    if (epoll_ctl(epoll_fd, EPOLL_CTL_ADD, sock_fd, &ev) == -1) {
        LOGE("epoll_ctl: listen_sock");

        exit(EXIT_FAILURE);
    }


    for (;;) {
        int nfds = epoll_wait(epoll_fd, events, MAX_EVENTS, -1);
        if (nfds == -1) {
            perror("epoll_wait");
            exit(EXIT_FAILURE);
        }

        for (int n = 0; n < nfds; ++n) {
            if (events[n].data.fd == sock_fd) {

                int conn_sock = accept4(sock_fd, (struct sockaddr *) &acceptAddr, &c_addr_len,
                                        SOCK_CLOEXEC);
                if (conn_sock == -1) {
                    perror("accept");
                    exit(EXIT_FAILURE);
                }

                fcntl(conn_sock, F_SETFL, O_NONBLOCK);

                fcntl(conn_sock, F_SETFD, O_CLOEXEC);

                ev.events = EPOLLIN | EPOLLERR | EPOLLET;

                ev.data.fd = conn_sock;

                if (epoll_ctl(epoll_fd, EPOLL_CTL_ADD, conn_sock, &ev) == -1) {
                    perror("epoll_ctl: conn_sock");
                    exit(EXIT_FAILURE);
                }
            } else {
                do_use_fd(epoll_fd, events[n].data.fd, events[n].events, events[n].data.ptr);
            }
        }
    }

}


static char buf[BUFSIZ];

struct event_handle {

    void *ptr;

    int len;
};

static struct event_handle eh{};

static void do_use_fd(int epoll_fd, int fd, uint32_t events, void *ptr) {

    switch (events) {

        case EPOLLIN: {

            memset(buf, 0, BUFSIZ);

            int len = read(fd, buf, BUFSIZ);

            LOGI("----> read: %s", buf);

            struct epoll_event event{};

            event.events = EPOLLET | EPOLLERR | EPOLLOUT;

            event.data.fd = fd;

            eh.ptr = buf;

            eh.len = len;

            event.data.ptr = &eh;

            epoll_ctl(epoll_fd, EPOLL_CTL_MOD, fd, &event);

        }
            break;

        case EPOLLERR: {

            epoll_ctl(epoll_fd, EPOLL_CTL_DEL, fd, nullptr);

            close(fd);

        }
            break;

        case EPOLLOUT: {

            struct event_handle *peh = static_cast<event_handle *>(ptr);

            char data[peh->len];

            strcpy(data, static_cast<const char *>(peh->ptr));

            std::transform(data, data + peh->len, data, ToUpper<char>());

            write(fd, data, static_cast<size_t>(peh->len));

            struct epoll_event event{};

            event.events = EPOLLET | EPOLLERR | EPOLLIN;

            event.data.fd = fd;

            epoll_ctl(epoll_fd, EPOLL_CTL_MOD, fd, &event);

        }
            break;

        default:
            break;


    }


}

