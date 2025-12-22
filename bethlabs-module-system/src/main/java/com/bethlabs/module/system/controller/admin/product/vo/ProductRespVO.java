package com.bethlabs.module.system.controller.admin.product.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Schema(description = "管理后台 - 产品表-设备系列 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ProductRespVO {

    @Schema(description = "自增id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "设备产品编号")
    private String productCode;

    @Schema(description = "设备产品名称")
    private String productName;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "更新者")
    private String updateUser;

    @Schema(description = "更新时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

}