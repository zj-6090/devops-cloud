package com.bethlabs.module.system.dal.dataobject.permission;

import com.bethlabs.framework.common.enums.CommonStatusEnum;
import com.bethlabs.framework.common.enums.SystemTypeEnum;
import com.bethlabs.framework.tenant.core.db.TenantBaseDO;
import com.bethlabs.module.system.enums.permission.DataScopeEnum;
import com.bethlabs.module.system.enums.permission.RoleTypeEnum;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 * 角色 DO
 *
 * @author ruoyi
 */
@TableName(value = "system_role", autoResultMap = true)
@KeySequence("system_role_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
public class RoleDO extends TenantBaseDO {

    /**
     * 角色ID
     */
    @TableId
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 系统类型 1：管理端 2：运维端 3：IDS端 4：维修端
     *
     * 枚举 {@link SystemTypeEnum}
     */
    private Integer systemType;
    /**
     * 角色标识
     *
     * 枚举
     */
    private String code;
    /**
     * 角色排序
     */
    private Integer sort;
    /**
     * 角色状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;
    /**
     * 角色类型
     *
     * 枚举 {@link RoleTypeEnum}
     */
    private Integer type;
    /**
     * 备注
     */
    private String remark;

    /**
     * 数据范围
     *
     * 枚举 {@link DataScopeEnum}
     */
    private Integer dataScope;
    /**
     * 数据范围(指定分组数组)
     *
     * 适用于 {@link #dataScope} 的值为 {@link DataScopeEnum#DATASCOPE_DEPT_ONLY, DataScopeEnum#DADASCOPE_DEPT_AND_PROJECT} 时
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<Long> dataScopeDeptIds;
    /**
     * 数据范围(指定项目数组)
     *
     * 适用于 {@link #dataScope} 的值为 {@link DataScopeEnum#DATASCOPE_PROJECT_ONLY, DataScopeEnum#DADASCOPE_DEPT_AND_PROJECT} 时
     */
    @TableField(typeHandler = JacksonTypeHandler.class)
    private Set<Long> dataScopeProjectIds;

    /**
     * js定位概图文件名称
     */
    private String fileName;


}
