package com.bethlabs.module.system.api.dept.dto;

import com.bethlabs.framework.common.enums.CommonStatusEnum;
import lombok.Data;

/**
 * 分组 Response DTO
 *
 * @author 芋道源码
 */
@Data
public class DeptRespDTO {

    /**
     * 分组编号
     */
    private Long id;
    /**
     * 分组名称
     */
    private String name;
    /**
     * 父分组编号
     */
    private Long parentId;
    /**
     * 负责人的用户编号
     */
    private Long leaderUserId;
    /**
     * 分组状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

}
