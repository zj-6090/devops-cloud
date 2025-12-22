package com.bethlabs.module.infra.service.minio;

import com.bethlabs.module.infra.config.minio.MinIoProperties;
import com.bethlabs.module.infra.config.minio.MinioManage;
import com.bethlabs.module.infra.controller.admin.minio.vo.LocalFileRespVo;
import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Slf4j
@Service
public class MinioMinioFileServiceImpl implements MinioFileService {

    @Autowired
    private MinIoProperties minIoProperties;


    @Autowired
    private MinioClient minioClient;

    @Autowired
    private MinioManage minioManage;

    @Value("${minio.bucketName}")
    private String bucketName;

    // 自定义缓冲区大小（适配大文件解压，比IOUtils默认更高效）
    private static final int BUFFER_SIZE = 8192;

    @Override
    public String upLoadFile(MultipartFile file) {
        // 保留原始文件名后缀
        String filename = getFilename(file);
        if (!StringUtils.hasText(bucketName)) {
            bucketName = minIoProperties.getBucketName();
        }
        try (InputStream inputStream = file.getInputStream()) {
            // 使用新的 PutObjectArgs API
            minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(filename).stream(inputStream, file.getSize(), -1) // 使用实际文件大小
                    .contentType(file.getContentType()) // 设置内容类型
                    .build());

            System.out.println("上传图片对象到minio服务器成功: " + filename);

            // 构建访问URL（包含日期文件夹路径）
            String url = minIoProperties.getEndpoint() + "/" + bucketName + "/" + filename;
            //后期用nginx时修改 补充一层
//            String url = minIoProperties.getPrefix() + "/" + bucketName + "/" + filename;
            return url;
        } catch (Exception e) {
            log.error("MinIO上传失败: " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public String uploadBase64File(String base64Str) {
        // 1. 生成日期文件夹+UUID文件名（保持原逻辑）
        String dateFolder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String filename = dateFolder + "/" + UUID.randomUUID().toString().replaceAll("-", "") + ".jpg";

        // 2. 使用JDK17标准Base64解码器（核心替换点）
        Base64.Decoder decoder = Base64.getDecoder();
        try {
            byte[] imageBytes = decoder.decode(base64Str);

            // 3. 保持原有字节处理逻辑（有符号字节转无符号）
            for (int i = 0; i < imageBytes.length; ++i) {
                if (imageBytes[i] < 0) {
                    imageBytes[i] += 256;
                }
            }

            // 4. MinIO上传（保持原API调用逻辑）
            try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes)) {
                minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(filename).stream(byteArrayInputStream, imageBytes.length, -1).contentType("image/jpeg").build());
                return minIoProperties.getPrefix() + "/" + bucketName + "/" + filename;
            }
        } catch (Exception e) {
            log.error("Base64解码或MinIO上传失败：无效的Base64字符串: {}", e.getMessage(), e);
            return null;
        }

    }

    @Override
    public LocalFileRespVo uploadLocalFile(String localFilePath) {
        File localFile = new File(localFilePath);

        // 1. 校验本地文件是否存在
        if (!localFile.exists()) {
            log.error("本地文件不存在: " + localFilePath);
            return null;
        }
        if (localFile.isDirectory()) {
            log.error("本地路径是目录: " + localFilePath);
            return null;
        }
        String dateFolder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        // 3. 识别文件ContentType（优化文件访问方式，如浏览器预览）
        ContentInfoUtil contentInfoUtil = new ContentInfoUtil();
        ContentInfo contentInfo = null;
        try {
            contentInfo = contentInfoUtil.findMatch(localFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String fileName = localFile.getName();
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String contentType = contentInfo != null ? contentInfo.getMimeType() : "application/octet-stream";
        //拼接一层uuid文件夹层级，防止同名文件覆盖问题
        String objectName = dateFolder + "/" + UUID.randomUUID().toString().replaceAll("-", "") + suffix;
        try (FileInputStream fis = new FileInputStream(localFile)) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(fis, localFile.length(), -1)
                            .contentType(contentType)
                            .build()
            );
            System.out.println("上传成功！本地路径：" + localFilePath + " | MinIO路径：" + bucketName + "/" + objectName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // 构建访问URL（包含日期文件夹路径）
//        String url = minIoProperties.getEndpoint() + "/" + bucketName + "/" + filename;
        //后期用nginx时修改
        String url = minIoProperties.getPrefix() + "/" + bucketName + "/" + objectName;
        LocalFileRespVo localFileRespVo = new LocalFileRespVo();
        localFileRespVo.setUrl(url);
        localFileRespVo.setSize(localFile.length() / 1024.0 / 1024.0); // 单位转换为MB
        return localFileRespVo;
    }

    @Override
    public void downloadToLocal(String visitName, String localPath) {
        //visitName是访问路径，例如：minIoProperties.getPrefix() + "/" + bucketName + "/" + objectName;
        String objectName = visitName.substring(minIoProperties.getPrefix().length() + minIoProperties.getBucketName().length() + 2);
        log.info("开始下载文件：" + objectName);
        // 构建获取文件的参数
        GetObjectArgs getArgs = GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build();

        // 流拷贝（try-with-resources自动关闭流）
        try (InputStream minioIn = minioClient.getObject(getArgs);
             OutputStream localOut = new FileOutputStream(localPath)) {
            byte[] buffer = new byte[1024 * 4]; // 4KB缓冲区
            int readLen;
            while ((readLen = minioIn.read(buffer)) != -1) {
                localOut.write(buffer, 0, readLen);
            }
            log.info("文件已下载到本地：" + localPath);
        } catch (IOException | NoSuchAlgorithmException | InvalidKeyException | ServerException |
                 InsufficientDataException | ErrorResponseException | InvalidResponseException | XmlParserException |
                 InternalException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean unTarToLocal(String visitName, String targetDir) {
        //visitName是访问路径，例如：minIoProperties.getPrefix() + "/" + bucketName + "/" + objectName;
        //本地测试带的是ip前缀
        String objectName = visitName.substring(minIoProperties.getEndpoint().length() + minIoProperties.getBucketName().length() + 2);
        //云服务是修改成这个
//        String objectName = visitName.substring(minIoProperties.getPrefix().length() + minIoProperties.getBucketName().length() + 2);
        try (InputStream minioInputStream = minioClient.getObject(GetObjectArgs.builder()
                .bucket(bucketName)
                .object(objectName)
                .build())) {
            // 4. 流式解压到唯一目录
            unTar(minioInputStream, targetDir);
            log.info("解压完成，目标目录：{}", targetDir);
            return true;
        } catch (Exception e) {
            log.error("解压失败", e);
            // 解压失败时清理目录，避免残留
            deleteDir(new File(targetDir));
        }
        return false;
    }

    @Override
    public void deleteFile(String bucketName, String path) {
        minioManage.deleteFile(bucketName, path);
    }

    @Override
    public void deleteBucket(String bucketName) {
        minioManage.deleteBucket("test2");
    }

    @Override
    public void setFileExpireTime(Integer expireDays) {
        try {
            minioManage.setObjectExpiration(bucketName, expireDays);
        } catch (Exception e) {
            log.error("设置文件过期时间失败: " + e.getMessage(), e);
        }
    }

    @NotNull
    private static String getFilename(MultipartFile file) {
        String dateFolder = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        //添加一次common，表示普通文件的上传层级，
        return dateFolder + "/" + UUID.randomUUID().toString().replaceAll("-", "") + extension;
    }


    // 递归删除目录
    private boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            File[] children = dir.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteDir(child);
                }
            }
        }
        return dir.delete();
    }

    /**
     * 优化版：解压TAR/TAR.GZ包到指定目录
     * 支持纯TAR、TAR.GZ/TGZ格式
     *
     * @param inputStream TAR包输入流（MinIO获取的流）
     * @param targetDir   解压目标目录
     * @throws IOException 解压异常
     */
    public void unTar(InputStream inputStream, String targetDir) throws IOException {
        log.info("开始解压TAR文件到目录: {}", targetDir);

        // 1. 确保目标目录存在
        Path targetPath = Paths.get(targetDir).toAbsolutePath().normalize();
        if (!Files.exists(targetPath)) {
            Files.createDirectories(targetPath);
            log.debug("创建目标目录: {}", targetPath);
        }

        long totalFiles = 0;
        long startTime = System.currentTimeMillis();

        try (TarArchiveInputStream tarInput = createTarInputStream(inputStream)) {
            TarArchiveEntry entry;

            while ((entry = tarInput.getNextTarEntry()) != null) {
                totalFiles++;

                // 安全检查：防止路径遍历攻击
                Path entryPath = targetPath.resolve(entry.getName()).normalize();

                if (!entryPath.startsWith(targetPath)) {
                    throw new SecurityException("恶意 tar 条目：尝试解压到目标目录之外的文件: " + entry.getName());
                }

                // 记录处理进度（每100个文件记录一次）
                if (totalFiles % 100 == 0) {
                    log.debug("已处理 {} 个文件...", totalFiles);
                }

                // 处理不同类型的条目
                processTarEntry(tarInput, entry, entryPath);
            }

            long endTime = System.currentTimeMillis();
            log.info("解压完成，共处理 {} 个文件，耗时 {} ms", totalFiles, (endTime - startTime));

        } catch (IOException e) {
            log.error("解压TAR文件失败: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * 创建TAR输入流，自动检测GZIP压缩
     */
    private TarArchiveInputStream createTarInputStream(InputStream inputStream) throws IOException {
        // 标记输入流以便后续reset检测
        if (!inputStream.markSupported()) {
            inputStream = new BufferedInputStream(inputStream);
        }

        // 尝试检测是否为GZIP压缩
        inputStream.mark(512); // 标记以便reset
        try {
            // 尝试创建GZIP输入流，如果不是GZIP格式会抛出异常
            GZIPInputStream gzipInputStream = new GZIPInputStream(inputStream);
            log.debug("检测到GZIP压缩格式，使用GzipCompressorInputStream解压");
            return new TarArchiveInputStream(gzipInputStream);
        } catch (IOException e) {
            // 不是GZIP格式，重置流并创建普通TAR流
            inputStream.reset();
            log.debug("未检测到GZIP压缩，使用普通TAR格式");
            return new TarArchiveInputStream(inputStream);
        }
    }

    /**
     * 处理单个TAR条目（优化版，使用IOUtils.copy）
     */
    private void processTarEntry(TarArchiveInputStream tarInput, TarArchiveEntry entry, Path entryPath) throws IOException {
        try {
            // 处理符号链接
            if (entry.isSymbolicLink()) {
                log.warn("跳过符号链接: {} -> {}", entry.getName(), entry.getLinkName());
                return;
            }

            // 处理目录
            if (entry.isDirectory()) {
                Files.createDirectories(entryPath);
                if (log.isDebugEnabled()) {
                    log.debug("创建目录: {}", entryPath);
                }
            }
            // 处理普通文件
            else if (entry.isFile()) {
                // 确保父目录存在
                Path parent = entryPath.getParent();
                if (parent != null && !Files.exists(parent)) {
                    Files.createDirectories(parent);
                }

                // 使用IOUtils.copy写入文件（更简洁可靠）
                try (OutputStream outputStream = Files.newOutputStream(entryPath,
                        StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
                    IOUtils.copy(tarInput, outputStream, BUFFER_SIZE);
                }

                // 设置文件修改时间
                if (entry.getLastModifiedDate() != null) {
                    Files.setLastModifiedTime(entryPath,
                            FileTime.from(entry.getLastModifiedDate().toInstant()));
                }

                // 设置简化的文件权限
                setSimplifiedPermissions(entryPath.toFile(), entry.getMode());

                // 记录大文件解压
                if (log.isDebugEnabled() && entry.getSize() > 10 * 1024 * 1024) { // 大于10MB
                    log.debug("解压大文件完成: {} ({} MB)",
                            entry.getName(),
                            entry.getSize() / (1024 * 1024));
                }
            }
            // 处理其他类型
            else {
                log.warn("跳过不支持的文件类型: {} (类型码: {})",
                        entry.getName(),
                        entry.getMode() >> 12);
            }

        } catch (Exception e) {
            log.error("处理TAR条目失败: {} - {}", entry.getName(), e.getMessage(), e);
            throw new IOException("处理文件 '" + entry.getName() + "' 失败", e);
        }
    }

    /**
     * 简化的文件权限设置（只关注可执行权限）
     * 这是跨平台安全的方案
     */
    private void setSimplifiedPermissions(File file, int mode) {
        // 只检查是否包含执行权限
        if (mode > 0) {
            // 检查是否有任何执行位被设置
            boolean isExecutable = (mode & 0111) != 0;

            // 如果是脚本文件，总是设置为可执行
            String fileName = file.getName().toLowerCase();
            boolean isScriptFile = fileName.endsWith(".sh") ||
                    fileName.endsWith(".bash") ||
                    fileName.endsWith(".py") ||
                    fileName.endsWith(".pl") ||
                    fileName.endsWith(".rb");

            // 如果tar条目标记为可执行，或者文件是脚本类型，则设置执行权限
            if (isExecutable || isScriptFile) {
                boolean success = file.setExecutable(true);
                if (log.isDebugEnabled() && success) {
                    log.debug("设置文件可执行权限: {}", file.getPath());
                }
            }
        }
    }

}
