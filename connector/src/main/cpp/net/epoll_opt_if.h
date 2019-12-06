
/*!
  *@file epoll_opt_if
  *@brief epoll事件操作
  *@author oz
  *@date    06，12，2019
  */

#ifndef SHAREDEMO_EPOLL_OPT_IF_H
#define SHAREDEMO_EPOLL_OPT_IF_H

namespace xdy_factory_test {

    class IEpollOpt {

    public:
        IEpollOpt() = delete;

        virtual void add(int epoll_fd, int fd) = 0;

        virtual void del(int epoll_fd, int fd) = 0;

        virtual void mod(int epoll_fd, int fd) = 0;
    };


}
#endif //SHAREDEMO_EPOLL_OPT_IF_H
