//
// Created by Administrator on 2019/12/6.
//

#ifndef SHAREDEMO_EVENT_CALLBACK_H
#define SHAREDEMO_EVENT_CALLBACK_H

namespace xdy_factory_test {

    class EventCallback {

    public:

        virtual void onSend(int epoll_fd, int fd, void *arg) = 0;

        virtual void onReceive(int epoll_fd, int fd, void *arg) = 0;

        virtual void onError(int epoll_fd, int fd, void *arg) = 0;
    };


}


#endif //SHAREDEMO_EVENT_CALLBACK_H
