# cmake_minimum_required(VERSION <specify CMake version here>)


include_directories(.)

aux_source_directory(. SRC)

message("---> ${SRC}")

add_library(crc-lib SHARED ${SRC})

#target_compile_options(crc-lib PUBLIC
#        -Wall -pedantic
#        -Wnon-virtual-dtor
#        -Werror
#        -Wno-error=deprecated-declarations
#        )