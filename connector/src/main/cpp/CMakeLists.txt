cmake_minimum_required(VERSION 3.6)

set(CMAKE_CXX_STANDARD 14)

SET(CMAKE_C_FLAGS "${CMAKE_C_FLAGS} -O3")
SET(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -O3")

include_directories(${CMAKE_CURRENT_SOURCE_DIR})

add_subdirectory(crc)

add_subdirectory(EventBus)

add_subdirectory(net)


#crc_utils-lib
add_library(crc_utils-lib
        SHARED
        crcutils.cpp)

target_include_directories(crc_utils-lib PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/crc)

target_link_libraries(crc_utils-lib
        log
        crc-lib)


#connect-lib
add_library(connect-lib
        SHARED
        cmd_server.cpp)

target_include_directories(connect-lib PUBLIC ${CMAKE_CURRENT_SOURCE_DIR}/net)

target_link_libraries(connect-lib
        log
        net-lib)

#target_compile_options(crc_utils-lib PUBLIC
#        -Wall -pedantic
#        -Wnon-virtual-dtor
#        -Werror
#        -Wno-error=deprecated-declarations)
#
#target_compile_options(connect-lib PUBLIC
#        -Wall -pedantic
#        -Wnon-virtual-dtor
#        -Werror
#        -Wno-error=deprecated-declarations
#        )

