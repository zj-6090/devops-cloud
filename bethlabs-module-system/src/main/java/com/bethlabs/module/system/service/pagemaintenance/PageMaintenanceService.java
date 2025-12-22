package com.bethlabs.module.system.service.pagemaintenance;

import java.util.*;

import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenanceEnableReqVO;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenancePageReqVO;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenanceRespVO;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenanceSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.pagemaintenance.PageMaintenanceDO;
import jakarta.validation.*;

/**
 * 页面维护 Service 接口
 *
 * @author mjl
 */
public interface PageMaintenanceService {

    /**
     * 创建页面维护
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPageMaintenance(@Valid PageMaintenanceSaveReqVO createReqVO);

    /**
     * 更新页面维护
     *
     * @param updateReqVO 更新信息
     */
    void updatePageMaintenance(@Valid PageMaintenanceSaveReqVO updateReqVO);


    /**
     * 启用禁用页面维护
     * @param enableReqVO
     */
    void enablePageMaintenance( PageMaintenanceEnableReqVO enableReqVO);

    /**
     * 删除页面维护
     *
     * @param id 编号
     */
    void deletePageMaintenance(Long id);

    /**
    * 批量删除页面维护
    *
    * @param ids 编号
    */
    void deletePageMaintenanceListByIds(List<Long> ids);

    /**
     * 获得页面维护
     *
     * @param id 编号
     * @return 页面维护
     */
    PageMaintenanceDO getPageMaintenance(Long id);

    /**
     * 获得页面维护分页
     *
     * @param pageReqVO 分页查询
     * @return 页面维护分页
     */
    PageResult<PageMaintenanceRespVO> getPageMaintenancePage(PageMaintenancePageReqVO pageReqVO);

    PageMaintenanceDO getPageMaintenanceUrl(Long productId, String versionNumber);
}