cmake_minimum_required(VERSION 3.4.1)

include_directories(${CMAKE_SOURCE_DIR})

aux_source_directory(${CMAKE_SOURCE_DIR} SRC)

add_library(
        crc_utils
        SHARED
        crcutils.cpp log.h Crc32.cpp Crc32.h Crc32Test.cpp)

target_link_libraries(
        crc_utils
        log)


add_library(
        connector
        SHARED
        ${SRC})

target_link_libraries(
        connector
        log)
