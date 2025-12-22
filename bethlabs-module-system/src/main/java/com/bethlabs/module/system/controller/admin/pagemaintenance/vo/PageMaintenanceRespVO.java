package com.bethlabs.module.system.controller.admin.pagemaintenance.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import com.alibaba.excel.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

@Schema(description = "管理后台 - 页面维护 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PageMaintenanceRespVO {

    @Schema(description = "自增id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1424")
    private Long id;

    @Schema(description = "名称", example = "赵六")
    private String name;

    @Schema(description = "产品id", example = "17273")
    private Long productId;

    @Schema(description = "产品名称", example = "赵六")
    private String productName;

    @Schema(description = "版本号")
    private String versionNumber;

    @Schema(description = "终端参数文件名称", example = "赵六")
    private String terminalName;

    @Schema(description = "实时监看名称", example = "李四")
    private String monitorName;

    @Schema(description = "终端参数文件下载路径", example = "https://www.iocoder.cn")
    private String terminalDownUrl;

    @Schema(description = "实时监看下载路径", example = "https://www.iocoder.cn")
    private String monitorDownUrl;

    @Schema(description = "终端参数文件访问路径", example = "https://www.iocoder.cn")
    private String terminalVisitUrl;

    @Schema(description = "实时监看访问路径", example = "https://www.iocoder.cn")
    private String monitorVisitUrl;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "更新者")
    private String updateUser;

    @Schema(description = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Schema(description = "是否启用：0-未启用 1-启用")
    private Integer isEnable;

}