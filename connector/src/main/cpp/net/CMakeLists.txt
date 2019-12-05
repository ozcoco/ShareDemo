# cmake_minimum_required(VERSION <specify CMake version here>)

include_directories(.)

aux_source_directory(. SRC)

message("---> ${SRC}")

add_library(net-lib STATIC ${SRC})

