LOCAL_PATH := $(call my-dir)

# Same source builds on Gingerbread (API 9) and Jelly Bean: only API 9 calls,
# hidden SystemProperties reached through reflection.
include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := optional
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_PACKAGE_NAME := OtgSwitch
LOCAL_CERTIFICATE := platform
include $(BUILD_PACKAGE)
