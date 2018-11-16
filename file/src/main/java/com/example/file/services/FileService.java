package com.example.file.services;

import com.example.file.util.FileUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: HuWeiJian
 * @Date: 2018/11/16 15:06
 * @Description:
 */
@Service
public class FileService {
    @Value("${server.port}")
    String port;
    @Value("${store.access_dir}")
    String accessDir;
    @Value("${store.root_path}")
    String localRootPath;

    public String storeFile(MultipartFile file) throws UnknownHostException {

        String filename = System.currentTimeMillis()+"";
        String module="pics";
        String extname=FileUpload.getExtensionName(file);
        boolean success = FileUpload.storeFile(file, localRootPath+module, filename);
        if (!success) {
            return null;
        }
        String host = null;
        host = InetAddress.getLocalHost().getHostAddress();
        return "http://" + host + ":" + port + "/" + accessDir + "/"+module+"/" + filename+"."+extname;
    }


}
