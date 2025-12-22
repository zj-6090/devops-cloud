package com.bethlabs.module.system.service.databuckups;

import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.module.infra.controller.admin.minio.vo.LocalFileRespVo;
import com.bethlabs.module.infra.service.minio.MinioFileService;
import com.bethlabs.module.system.controller.admin.databuckups.vo.DataBuckupsPageReqVO;
import com.bethlabs.module.system.controller.admin.databuckups.vo.DataBuckupsSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.databuckups.DataBuckupsDO;
import com.bethlabs.module.system.dal.mysql.databuckups.DataBuckupsMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import com.bethlabs.framework.common.util.object.BeanUtils;
import static com.bethlabs.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.bethlabs.module.system.enums.ErrorCodeConstants.DATA_BUCKUPS_NOT_EXISTS;


/**
 * 数据备份文件 Service 实现类
 *
 * @author mjl
 */
@Slf4j
@Service
@Validated
public class DataBuckupsServiceImpl implements DataBuckupsService {

    @Resource
    private DataBuckupsMapper dataBuckupsMapper;

    @Autowired
    private MinioFileService minioFileService;

    @Value("${sql.username}")
    private String username;
    @Value("${sql.password}")
    private String password;
    @Value("${sql.databaseName}")
    private String databaseName;
    @Value("${sql.sqlPath}")
    private String sqlPath;
    @Value("${sql.container")
    private String container;

    @Override
    public Long createDataBuckups(DataBuckupsSaveReqVO createReqVO) {
        // 插入
        DataBuckupsDO dataBuckups = BeanUtils.toBean(createReqVO, DataBuckupsDO.class);
        dataBuckupsMapper.insert(dataBuckups);

        // 返回
        return dataBuckups.getId();
    }

    @Override
    public void updateDataBuckups(DataBuckupsSaveReqVO updateReqVO) {
        // 校验存在
        validateDataBuckupsExists(updateReqVO.getId());
        // 更新
        DataBuckupsDO updateObj = BeanUtils.toBean(updateReqVO, DataBuckupsDO.class);
        dataBuckupsMapper.updateById(updateObj);
    }

    @Override
    public void deleteDataBuckups(Long id) {
        // 校验存在
        validateDataBuckupsExists(id);
        // 删除
        dataBuckupsMapper.deleteById(id);
    }

    @Override
        public void deleteDataBuckupsListByIds(List<Long> ids) {
        // 删除
        dataBuckupsMapper.deleteByIds(ids);
        }


    private void validateDataBuckupsExists(Long id) {
        if (dataBuckupsMapper.selectById(id) == null) {
            throw exception(DATA_BUCKUPS_NOT_EXISTS);
        }
    }

    @Override
    public DataBuckupsDO getDataBuckups(Long id) {
        return dataBuckupsMapper.selectById(id);
    }

    @Override
    public PageResult<DataBuckupsDO> getDataBuckupsPage(DataBuckupsPageReqVO pageReqVO) {
        return dataBuckupsMapper.selectPage(pageReqVO);
    }


    @Override
    public void dataBaseBackUp() {
        //todo 待测试-需要java测试，本地无法测试
        //数据库文件备份，提供给定时任务调用
        //1.将数据文件备份到本地
        String localPath = dataBaseBackUpToLocal();
        if (localPath != null) {
            //2.将备份的文件上传到服务器
            LocalFileRespVo localFileRespVo = minioFileService.uploadLocalFile(localPath);
            //3.保存备份记录数据
            DataBuckupsDO dataBuckupsDO = new DataBuckupsDO();
            dataBuckupsDO.setFileName("icapweb_cloud.sql");
            dataBuckupsDO.setFileUrl(localFileRespVo.getUrl());
            dataBuckupsDO.setFileSize(localFileRespVo.getSize().toString() + "M");
            dataBuckupsMapper.insert(dataBuckupsDO);
            //4.删除本地备份的文件
            deleteLocalFile(localPath);
        }else {
            log.error("数据库备份到本地失败");
        }
    }

    private void deleteLocalFile(String localPath) {
        // 1. 封装路径（自动适配 Windows/Linux 分隔符，无需手动处理 \ 或 /）
        Path file = Paths.get(localPath);

        try {
            // 核心校验：如果路径是目录，直接返回失败，避免误操作
            if (Files.isDirectory(file)) {
                log.error("删除失败：路径【" + localPath + "】是目录，本方法仅支持删除文件！");
            }

            // 2. 删除文件（Files.deleteIfExists 特性：文件不存在时返回 true，无需额外判空）
            boolean isDeleted = Files.deleteIfExists(file);
            if (isDeleted) {
                log.info("文件删除成功：" + localPath);
            } else {
                log.info("文件无需删除：路径【" + localPath + "】不存在");
            }

        } catch (Exception e) {
            // 捕获权限不足、路径非法等异常
            log.error("删除文件失败！路径：" + localPath + "，原因：" + e.getMessage());
            e.printStackTrace();
        }
    }

    private String dataBaseBackUpToLocal() {
        String randomUUID = UUID.randomUUID().toString().replace("-", "");
        String fileName = randomUUID + ".sql";
        File saveFile = new File(sqlPath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        //拼接命令行的命令
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("docker exec ").append(container).append(" mysqldump").append(" --opt");
        stringBuilder.append(" --user=").append(username).append(" --password=").append(password)
                .append(" --lock-all-tables=true");
//        stringBuilder.append(" --result-file=").append("d:/fileinfo/icapweb.sql").append(" --default-character-set=utf8 ")
//                .append(databaseName);
        stringBuilder.append(" --result-file=").append(sqlPath + fileName).append(" --default-character-set=utf8 ")
                .append(databaseName);
        // mysqldump --opt --user=root --password=bethlabs --lock-all-tables=true --result-file=C:\bethlabs\sql --default-character-set=utf8 icapweb

        //调用外部执行exe文件的javaAPI
        try {
            log.info("开始备份数据库,执行命令:{}", stringBuilder);
            Process process = Runtime.getRuntime().exec(stringBuilder.toString());
            // 0 表示线程正常终止。
            if (process.waitFor() == 0) {
                return sqlPath + fileName;
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}