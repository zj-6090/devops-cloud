package com.bethlabs.module.system.dal.dataobject.pagemaintenance;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 页面维护 DO
 *
 * @author mjl
 */
@TableName("ops_page_maintenance")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageMaintenanceDO {

    /**
     * 自增id
     */
    @TableId
    private Long id;
    /**
     * 名称
     */
    private String name;
    /**
     * 产品id
     */
    private Long productId;
    /**
     * 版本号
     */
    private String versionNumber;
    /**
     * 终端参数文件名称
     */
    private String terminalName;
    /**
     * 实时监看名称
     */
    private String monitorName;
    /**
     * 终端参数文件下载路径
     */
    private String terminalDownUrl;
    /**
     * 实时监看下载路径
     */
    private String monitorDownUrl;
    /**
     * 终端参数文件访问路径
     */
    private String terminalVisitUrl;
    /**
     * 实时监看访问路径
     */
    private String monitorVisitUrl;
    /**
     * 备注
     */
    private String remark;
    /**
     * 更新者
     */
    private String updateUser;
    /**
     * 是否启用：0-未启用 1-启用
     */
    private Integer isEnable;


}