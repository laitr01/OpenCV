LOCAL_PATH := $(call my-dir) #This variable indicates the location of the source files in the development tree
include $(CLEAR_VARS)

#opencv
OPENCVROOT:=C:\Users\trach\Downloads\OpenCV-android-sdk
OPENCV_CAMERA_MODULES:=on
OPENCV_INSTALL_MODULES:=on
OPENCV_LIB_TYPE:=SHARED
include ${OPENCVROOT}/sdk/native/jni/OpenCV.mk

LOCAL_SOURCE_FILES:=medialib_reactive_trach_com_opencvfacedetector_OpenCVMiddle.cpp
LOCAL_LDLIBS += -llog
LOCAL_MODULE:=MyLibs

include $(BUILD_SHARED_LIBRARY)

