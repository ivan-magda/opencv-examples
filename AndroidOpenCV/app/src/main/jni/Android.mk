LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

OPENCV_ROOT := /Users/ivanmagda/Documents/OpenCV-android-sdk
OPENCV_CAMERA_MODULES := on
OPENCV_INSTALL_MODULES := on
OPENCV_LIB_TYPE :=  SHARED

include ${OPENCV_ROOT}/sdk/native/jni/OpenCV.mk

NDK_MODULE_PATH=/Users/ivanmagda/Library/Android/sdk/ndk-bundle
APP_ABI := all
LOCAL_CPPFLAGS := -std=gnu++0x
LOCAL_CFLAGS += -O2
LOCAL_LDLIBS += -llog -ldl
LOCAL_MODULE := native

LOCAL_SRC_FILES := hello-android-jni.c

include $(BUILD_SHARED_LIBRARY)
