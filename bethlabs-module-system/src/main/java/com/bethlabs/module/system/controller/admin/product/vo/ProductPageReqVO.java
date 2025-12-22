package com.bethlabs.module.system.controller.admin.product.vo;

import com.bethlabs.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 产品表-设备系列分页 Request VO")
@Data
public class ProductPageReqVO extends PageParam {

    private String productName;

}