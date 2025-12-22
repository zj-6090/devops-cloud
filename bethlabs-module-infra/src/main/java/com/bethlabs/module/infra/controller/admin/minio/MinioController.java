package com.bethlabs.module.infra.controller.admin.minio;


import com.bethlabs.framework.common.pojo.CommonResult;
import com.bethlabs.module.infra.service.minio.MinioFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/infra/minioFile")
public class MinioController {

    @Autowired
    private MinioFileService minioFileService;

    @PostMapping("/upload")
    public CommonResult upload(MultipartFile file){
        return CommonResult.success(minioFileService.upLoadFile(file));
    }

    @PostMapping("/uploadBase64")
    public CommonResult uploadBase64(@RequestBody  String imgStr){
        return CommonResult.success(minioFileService.uploadBase64File(imgStr));
    }

    @PutMapping("/setFileExpireTime")
    public CommonResult setFileExpireTime(Integer expireDays) {
        minioFileService.setFileExpireTime(expireDays);
        return CommonResult.success(true);
    }

    @DeleteMapping("/delete")
    public CommonResult delete(String bucketName,String path) {
        minioFileService.deleteFile(bucketName,path);
        return CommonResult.success(true);
    }

    @DeleteMapping("/deleteBucket")
    public CommonResult deleteBucket(String bucketName) {
        minioFileService.deleteBucket(bucketName);
        return  CommonResult.success(true);
    }

}
