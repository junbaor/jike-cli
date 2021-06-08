package com.github.junbaor.jike;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;

import java.io.IOException;

public class QiNiu {

    public static void main(String[] args) throws IOException {
        Configuration cfg = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(cfg);
        uploadManager.put("", "jike", "");
    }

}
