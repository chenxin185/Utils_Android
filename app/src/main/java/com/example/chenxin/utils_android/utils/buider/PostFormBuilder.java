package com.example.chenxin.utils_android.utils.buider;

import com.example.chenxin.utils_android.utils.request.PostFormRequest;
import com.example.chenxin.utils_android.utils.request.RequestCall;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by momo on 2018/4/4.
 */

public class PostFormBuilder extends OkhttpRequestBuider<PostFormBuilder> implements HasParamsable{

    private List<FileInput> mFileInputs = new ArrayList<>();

    @Override
    public RequestCall build() {
        return new PostFormRequest(url, true, params, headers, mFileInputs, id).build();
    }

    @Override
    public PostFormBuilder params(Map<String, String> params) {
        this.params = params;
        return this;
    }

    @Override
    public PostFormBuilder addParams(String key, String val) {
        if (params == null){
            this.params = new LinkedHashMap<>();
        }
        this.params.put(key,val);
        return this;
    }

    public PostFormBuilder files(String key,Map<String,File> files){
        for (String fileName : files.keySet()){
            this.mFileInputs.add(new FileInput(key,fileName,files.get(fileName)));
        }
        return this;
    }
    public PostFormBuilder addFile(String key, String fileName, File file){
        mFileInputs.add(new FileInput(key,fileName,file));
        return this;
    }



    public static class FileInput{
        public String mKey;
        public String mFileName;
        public File mFile;

        public FileInput(String key, String fileName, File file){
            this.mKey = key;
            this.mFileName = fileName;
            this.mFile = file;
        }
        @Override
        public String toString() {
            return "FileInput{" +
                    "mKey='" + mKey + '\'' +
                    ", mFileName='" + mFileName + '\'' +
                    ", mFile=" + mFile +
                    '}';
        }


    }
}
