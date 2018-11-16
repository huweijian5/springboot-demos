package com.example.file.util;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.lang.NonNull;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class FileUpload {
    /**
     * 文件将会保存在localPath目录下
     *
     * @param file
     * @param localPath 指定文件的本地保存路径，如 C:/Users/huweijian/Desktop/rentHouse/
     * @param fileName  要保存的文件名,不包含扩展名,扩展名同原本的文件一致，如 myfilename
     *                  如果为空则自动生成文件名
     * @return 是否成功
     */
    public static boolean storeFile(@NonNull MultipartFile file, @NonNull String localPath, @NonNull String fileName) {
        if (file == null || localPath == null || fileName == null) {
            return false;
        }

        String realpath = localPath + (localPath.endsWith("/") ? "" : "/");
        File fileDir = new File(realpath);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }

        fileName = fileName + "." + getExtensionName(file);
        InputStream input = null;
        FileOutputStream fos = null;
        try {
            input = file.getInputStream();
            fos = new FileOutputStream(realpath + "/" + fileName);
            IOUtils.copy(input, fos);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            IOUtils.closeQuietly(input);
            IOUtils.closeQuietly(fos);
        }
        return true;
    }

    /**
     * 获得扩展名
     *
     * @param file
     * @return
     */
    public static String getExtensionName(@NonNull MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        //获得文件的扩展名
        String extname = FilenameUtils.getExtension(originalFilename);
        return extname;
    }


}