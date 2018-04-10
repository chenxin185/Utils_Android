package com.example.chenxin.utils_android.utils.request;

import com.example.chenxin.utils_android.utils.buider.PostFormBuilder;
import com.example.chenxin.utils_android.utils.callback.Callback;

import java.io.UnsupportedEncodingException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by momo on 2018/4/4.
 */

public class PostFormRequest extends OkhttpRequest {
    List<PostFormBuilder.FileInput> mList;
    public PostFormRequest(String url, Object tag, Map<String, String> params, Map<String, String> headers, List<PostFormBuilder.FileInput> list, int id) {
        super(url, tag, params, headers, id);
        this.mList = list;
    }

    @Override
    public RequestBody buildRequestBody() {
        if (mList ==null||mList.isEmpty()){
            FormBody.Builder builder = new FormBody.Builder();
            addParams(builder);
            FormBody formBody = builder.build();
            return formBody;
        }else {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            addParams(builder);
            for (int i =0;i<mList.size();i++){
                PostFormBuilder.FileInput fileInput = mList.get(i);
                RequestBody fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileInput.mFileName))
                        ,fileInput.mFile);
                builder.addFormDataPart(fileInput.mKey,fileInput.mFileName,fileBody);
            }
            return builder.build();
        }


    }

    @Override
    public Request buildRequest(RequestBody responseBody) {
        return getBuilder().post(responseBody).build();
    }

    private String guessMimeType(String path){
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor= null;
        try {
            contentTypeFor = fileNameMap.getContentTypeFor(URLEncoder.encode(path,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (contentTypeFor ==null){
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;

    }

    @Override
    public RequestBody wrappedRequestBody(RequestBody requestBody, final Callback callback) {
        if (callback ==null)
            return requestBody;
        CountingRequestBody countingRequestBody = new CountingRequestBody(requestBody,
                new CountingRequestBody.ProgressListener() {
            @Override
            public void onRequestProgress(long byteWritten, long contentLength) {
                callback.inProgress(byteWritten*1.0f/contentLength,contentLength,getId());
            }
        });
        return countingRequestBody;
    }

    private void addParams(MultipartBody.Builder builder){
        if (params!=null&&params.isEmpty()){
            for (String key:params.keySet()){
                Headers headers = Headers.of("Content-Disposition", "form-data; name=\"" + key + "\"");
                builder.addPart(headers,RequestBody.create(null,params.get(key)));
            }
        }
    }
    private void addParams(FormBody.Builder builder){
        if (params !=null&&params.isEmpty()){
            for (String key:params.keySet()){
                builder.add(key,params.get(key));
            }
        }
    }
}
