LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

#TARGET_ARCH_ABI := armeabi-v7a
#$(warning " TARGET_ARCH_ABI is $(TARGET_ARCH_ABI)")
LOCAL_MODULE := luajit
LOCAL_SRC_FILES := $(LOCAL_PATH)/$(TARGET_ARCH_ABI)/libluajit.a
include $(PREBUILT_STATIC_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE     := luajava
LOCAL_SRC_FILES  := luajava.c
LOCAL_STATIC_LIBRARIES := luajit
include $(BUILD_SHARED_LIBRARY)
