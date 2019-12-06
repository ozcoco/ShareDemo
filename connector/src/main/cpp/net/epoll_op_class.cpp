//
// Created by Administrator on 2019/12/6.
//

#include "epoll_op_class.h"

void xdy_factory_test::EpollOpt::add(int epoll_fd, int fd) {
    epoll_opt_.add(epoll_fd, fd);
}

void xdy_factory_test::EpollOpt::del(int epoll_fd, int fd) {
    epoll_opt_.del(epoll_fd, fd);
}

void xdy_factory_test::EpollOpt::mod(int epoll_fd, int fd) {
    epoll_opt_.mod(epoll_fd, fd);
}


xdy_factory_test::xdy_factory_test::IEpollOpt &xdy_factory_test::EpollOpt::getInstance() {

    static EpollOpt epollOpt{};

    return epollOpt;
}

void xdy_factory_test::EpollOptImpl::add(int epoll_fd, int fd) {

    struct epoll_event event{};


    events_map_[fd] = event;

}

void xdy_factory_test::EpollOptImpl::del(int epoll_fd, int fd) {

    events_map_.erase(fd);
}

void xdy_factory_test::EpollOptImpl::mod(int epoll_fd, int fd) {

    struct epoll_event event{};


    events_map_[fd] = event;

}
