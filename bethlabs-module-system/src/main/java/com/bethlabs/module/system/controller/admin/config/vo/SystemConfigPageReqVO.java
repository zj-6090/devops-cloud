package com.bethlabs.module.system.controller.admin.config.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.bethlabs.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - 系统配置分页 Request VO")
@Data
public class SystemConfigPageReqVO extends PageParam {

}