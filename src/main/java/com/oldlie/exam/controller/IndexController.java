package com.oldlie.exam.controller;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

import com.oldlie.exam.http.reponse.BaseResponse;
import com.oldlie.exam.http.reponse.ImageResponse;
import com.oldlie.exam.service.ManagerService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class IndexController {

    @Value("${zs.upload}")
    private String uploadDirectory;

    @Value("${zs.uploadUrl}")
    private String uploadURL;

    private ManagerService managerService;

    public IndexController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping("/")
    public BaseResponse init() {
        return this.managerService.init("1.0.6");
    }

    @PostMapping("/image")
    public ImageResponse uploadImage(@RequestBody MultipartFile file) {
        ImageResponse response = new ImageResponse();
        if (file.isEmpty()) {
            response.setStatus(1);
            response.setMessage("选择需要上传的文件");
            return response;
        }
        Calendar calendar = Calendar.getInstance();
        String fileName = file.getOriginalFilename();
        int length = fileName.length();
        if (fileName.length() > 15) {
            fileName = fileName.substring(length - 10, length);
        }
        StringBuilder fileNameBuilder = new StringBuilder(64);
        fileNameBuilder.append(calendar.getTime().getTime()).append("_").append(fileName);

        StringBuilder path = new StringBuilder(128);
        path.append(calendar.get(Calendar.YEAR)).append(File.separator) // 上传年
                .append(calendar.get(Calendar.MONTH)).append(File.separator) // 上传月
                .append(fileNameBuilder.toString());

        File savedFile = new File(this.uploadDirectory + File.separator + path.toString());
        if (!savedFile.getParentFile().exists()) {
            savedFile.getParentFile().mkdirs();
        }

        try {
            file.transferTo(savedFile);
            response.setLink(this.uploadURL + "/" + path.toString().replace("\\", "/"));
        } catch (IOException e) {
            response.setStatus(1);
            response.setMessage(e.getLocalizedMessage());
        }

        return response;
    }
}