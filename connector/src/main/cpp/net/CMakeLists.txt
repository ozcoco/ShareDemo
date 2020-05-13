# cmake_minimum_required(VERSION <specify CMake version here>)

include_directories(.)

aux_source_directory(. SRC)

message("---> ${SRC}")

add_library(net-lib SHARED ${SRC})

target_include_directories(net-lib PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/../EventBus/include ${CMAKE_CURRENT_SOURCE_DIR}/..)

target_link_libraries(net-lib
        eventBus
        crc-lib
        log)

#target_compile_options(net-lib PUBLIC
#        -Wall -pedantic
#        -Wnon-virtual-dtor
#        -Werror
#        -Wno-error=deprecated-declarations
#        )

