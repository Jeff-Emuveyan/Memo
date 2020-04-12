package com.state.memo.model

enum class MediaFileUploadStatus(var totalBytes: Int = 0, var bytesTransferred: Int = 0) {

    DEFAULT,
    UPLOADING,
    UPLOAD_COMPLETED,
    FAILED,
    CANCELLED;

}