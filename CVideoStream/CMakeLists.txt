cmake_minimum_required(VERSION 3.6)
project(CVideoStream)

find_package(OpenCV REQUIRED)

set(CMAKE_CXX_STANDARD 11)
set(SOURCE_FILES main.cpp)
add_executable(CVideoStream ${SOURCE_FILES})

include_directories(${OpenCV_INCLUDE_DIRS})
target_link_libraries(CVideoStream ${OpenCV_LIBS})