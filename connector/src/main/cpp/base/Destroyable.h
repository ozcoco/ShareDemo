//
// Created by Administrator on 2019/12/5.
//

#ifndef SHAREDEMO_DESTROYABLE_H
#define SHAREDEMO_DESTROYABLE_H

namespace xdy {

    /**
     * Destroyable
     *  资源回收接口
     * **/
    class Destroyable {

        virtual void destroy() = 0;

    };


}


#endif //SHAREDEMO_DESTROYABLE_H
