package com.bethlabs.module.system.controller.admin.databuckups.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 数据备份文件 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DataBuckupsRespVO {

    @Schema(description = "自增主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @Schema(description = "文件大小")
    private String fileSize;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件下载路径")
    private String fileUrl;

}