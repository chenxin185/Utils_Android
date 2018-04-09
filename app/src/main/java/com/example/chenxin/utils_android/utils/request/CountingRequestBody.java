package com.example.chenxin.utils_android.utils.request;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by momo on 2018/4/4.
 */

public class CountingRequestBody extends RequestBody {
    public RequestBody mRequestBody;
    public ProgressListener mProgressListener;
    public CountingSink mCountingSink;
    public CountingRequestBody(RequestBody requestBody,ProgressListener listener){
        this.mRequestBody = requestBody;
        this.mProgressListener = listener;
    }
    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        mCountingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(mCountingSink);
        mRequestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    @Override
    public long contentLength() throws IOException {
        try {
            return mRequestBody.contentLength();
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public class CountingSink extends ForwardingSink{
        public long byteWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            byteWritten+= byteCount;
            mProgressListener.onRequestProgress(byteWritten,contentLength());
        }
    }
    public static interface ProgressListener{
        public void onRequestProgress(long byteWritten, long contentLength);
    }
}
