package com.example.file.web;

import com.example.file.services.FileService;
import com.example.file.util.FileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.UnknownHostException;

@RestController
public class AdvertController {
    @Autowired
    FileService fileService;

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws UnknownHostException {

        String url = fileService.storeFile(file);
        return url;

    }


}