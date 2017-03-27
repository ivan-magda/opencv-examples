cmake_minimum_required(VERSION 3.6)
project(CartoonifierDesktop)

find_package(OpenCV REQUIRED)

set(CMAKE_CXX_STANDARD 11)
set(SOURCE_FILES main.cpp)
add_executable(CartoonifierDesktop ${SOURCE_FILES})

include_directories(${OpenCV_INCLUDE_DIRS})
target_link_libraries(CartoonifierDesktop ${OpenCV_LIBS})
