package com.bethlabs.module.system.controller.admin.asset.vo;

import com.bethlabs.framework.common.pojo.PageParam;
import lombok.Data;

import java.util.List;

@Data
public class BrandReqVO extends PageParam {

    /**
     * 设备类型
     */
    private String devType;

    /**
     * 品牌IDS
     */
    private List<Long> brandIds;
}
