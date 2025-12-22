package com.bethlabs.module.system.controller.admin.databuckups.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.bethlabs.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.bethlabs.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 数据备份文件分页 Request VO")
@Data
public class DataBuckupsPageReqVO extends PageParam {

}