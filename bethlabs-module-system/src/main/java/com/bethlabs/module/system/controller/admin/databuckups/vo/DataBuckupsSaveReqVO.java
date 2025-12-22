package com.bethlabs.module.system.controller.admin.databuckups.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 数据备份文件新增/修改 Request VO")
@Data
public class DataBuckupsSaveReqVO {

    @Schema(description = "自增主键", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "文件大小")
    private String fileSize;

    @Schema(description = "文件名称")
    private String fileName;

    @Schema(description = "文件下载路径")
    private String fileUrl;

}