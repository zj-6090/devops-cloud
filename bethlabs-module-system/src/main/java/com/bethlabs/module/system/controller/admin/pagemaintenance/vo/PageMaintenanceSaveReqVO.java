package com.bethlabs.module.system.controller.admin.pagemaintenance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import jakarta.validation.constraints.*;

@Schema(description = "管理后台 - 页面维护新增/修改 Request VO")
@Data
public class PageMaintenanceSaveReqVO {

    @Schema(description = "自增id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1424")
    private Long id;

    @Schema(description = "名称", example = "赵六")
    @NotBlank(message = "名称不能为空")
    private String name;

    @Schema(description = "产品id", example = "17273")
    @NotNull(message = "产品id不能为空")
    private Long productId;

    @Schema(description = "版本号")
    @NotBlank(message = "版本号不能为空")
    private String versionNumber;

    @Schema(description = "终端参数文件名称", example = "赵六")
    @NotBlank(message = "终端参数文件名称不能为空")
    private String terminalName;

    @Schema(description = "实时监看名称", example = "李四")
    @NotBlank(message = "实时监看名称不能为空")
    private String monitorName;

    @Schema(description = "终端参数文件下载路径", example = "https://www.iocoder.cn")
    @NotBlank(message = "终端参数文件下载路径不能为空")
    private String terminalDownUrl;

    @Schema(description = "实时监看下载路径", example = "https://www.iocoder.cn")
    @NotBlank(message = "实时监看下载路径不能为空")
    private String monitorDownUrl;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "更新者")
    private String updateUser;

    @Schema(description = "是否启用：0-未启用 1-启用")
    private Integer isEnable;

}