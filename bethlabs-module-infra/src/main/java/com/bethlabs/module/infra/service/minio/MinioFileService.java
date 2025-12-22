package com.bethlabs.module.infra.service.minio;

import com.bethlabs.module.infra.controller.admin.minio.vo.LocalFileRespVo;
import org.springframework.web.multipart.MultipartFile;


public interface MinioFileService {
    /**
     * 文件上传方法
     *
     * @param file
     * @return
     */
    String upLoadFile(MultipartFile file);

    /**
     * 上传base64的图片文件
     *
     * @param base64Str
     */
    String uploadBase64File(String base64Str);

    /**
     * 获取本地文件上传到minio
     *
     * @param filePath
     * @return
     */
    LocalFileRespVo uploadLocalFile(String filePath);

    /**
     * 将文件下载到本地
     * @param objectName
     * @param localPath
     */
    void downloadToLocal(String objectName, String localPath);

    /**
     * 将minio中的tar包解压到本地对应目录
     * @param visitName minio中的tar包路径:访问路径，该路径可能是拼接了路径
     * @param targetDir 本地解压目录
     */
    Boolean unTarToLocal(String visitName, String targetDir);

    void deleteFile(String bucketName, String path);

    void deleteBucket(String bucketName);

    void setFileExpireTime(Integer expireDays);
}
