package com.example.chenxin.utils_android.utils.request;

import com.example.chenxin.utils_android.utils.callback.Callback;
import com.example.chenxin.utils_android.utils.utils.Exceptions;

import java.io.File;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;


/**
 * Created by momo on 2018/4/4.
 */

public class PostFileRequest extends OkhttpRequest {

    private static MediaType MEDIA_TYPE_STREAM = MediaType.parse("application/octet-stream");
    private File mFile;
    private MediaType mMediaType;

    public PostFileRequest(String url, Object tag, Map<String, String> params,
                           Map<String, String> headers, File file, MediaType mediaType, int id) {
        super(url, tag, params, headers, id);
        this.mFile = file;
        this.mMediaType = mediaType;
        if (this.mFile ==null){
            Exceptions.illegalArgument("the file can not be null !");
        }
        if (this.mMediaType ==null){
            this.mMediaType = mediaType;
        }

    }

    @Override
    public RequestBody buildRequestBody() {
        return RequestBody.create(mMediaType,mFile);
    }

    @Override
    public Request buildRequest(RequestBody requestBody) {
        return getBuilder().post(requestBody).build();
    }

    @Override
    public RequestBody wrappedRequestBody(RequestBody requestBody, final Callback callback) {
        if (callback == null)
            return requestBody;
        CountingRequestBody countingRequestBody =new CountingRequestBody(requestBody,
                new CountingRequestBody.ProgressListener() {
            @Override
            public void onRequestProgress(long byteWritten, long contentLength) {
                callback.inProgress(byteWritten*1.0f/contentLength,contentLength,getId());
            }
        });
        return countingRequestBody;
    }
}
