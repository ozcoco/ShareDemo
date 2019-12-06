//
// Created by Administrator on 2019/12/6.
//

#ifndef SHAREDEMO_EPOLL_OP_CLASS_H
#define SHAREDEMO_EPOLL_OP_CLASS_H

namespace xdy_factory_test {

#include <iostream>
#include <map>
#include <sys/epoll.h>

#include "epoll_opt_if.h"

    using namespace xdy_factory_test;


    class EpollOptImpl : public IEpollOpt {

    private:
        std::map<int, struct epoll_event&> events_map_{};

    public:
        virtual void add(int epoll_fd, int fd) override;

        virtual void del(int epoll_fd, int fd) override;

        virtual void mod(int epoll_fd, int fd) override;

    };


    class EpollOpt : public IEpollOpt {

    private:
        EpollOptImpl epoll_opt_{};

        EpollOpt() = default;

    public:
        static IEpollOpt &getInstance();

        virtual void add(int epoll_fd, int fd) override;

        virtual void del(int epoll_fd, int fd) override;

        virtual void mod(int epoll_fd, int fd) override;

    };


}


#endif //SHAREDEMO_EPOLL_OP_CLASS_H
