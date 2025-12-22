package com.bethlabs.module.system.controller.admin.pagemaintenance.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.bethlabs.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.bethlabs.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 页面维护分页 Request VO")
@Data
public class PageMaintenancePageReqVO extends PageParam {

    @Schema(description = "产品id", example = "17273")
    private Long productId;

    @Schema(description = "版本号")
    private String versionNumber;

    @Schema(description = "是否启用：0-未启用 1-启用")
    private Integer isEnable;

}