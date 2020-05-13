
include_directories(${CMAKE_CURRENT_SOURCE_DIR})

add_library(eventBus
        SHARED
        include/eventbus/EventBus.h
        include/eventbus/EventCollector.h
        include/eventbus/internal/AsyncCallbackVector.h
        include/eventbus/internal/common.h
        include/eventbus/internal/CallbackVector.h
        include/eventbus/internal/TransactionCallbackVector.h
        include/eventbus/TokenHolder.h
        src/eventbus/AsyncEventBus.cpp include/eventbus/AsyncEventBus.h)

#target_compile_options(eventBus PUBLIC
#        -Wall -pedantic
#        -Wnon-virtual-dtor
#        -Werror
#        -Wno-error=deprecated-declarations
#        )



