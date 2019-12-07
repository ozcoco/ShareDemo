cmake_minimum_required(VERSION 3.4.1)

include_directories(${CMAKE_SOURCE_DIR})

add_subdirectory(crc)

#add_subdirectory(net)

add_library(
        crc_utils
        SHARED
        crcutils.cpp log.h)

target_link_libraries(
        crc_utils
        crc-lib
        log)


aux_source_directory(${CMAKE_SOURCE_DIR} SRC)

add_library(
        connector
        SHARED
        ${SRC})

target_link_libraries(
        connector
        crc_utils
#        net-lib
        log)
