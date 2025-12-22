package com.bethlabs.module.system.controller.admin.pagemaintenance;

import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenanceEnableReqVO;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenancePageReqVO;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenanceRespVO;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenanceSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.pagemaintenance.PageMaintenanceDO;
import com.bethlabs.module.system.service.pagemaintenance.PageMaintenanceService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import jakarta.validation.constraints.*;
import jakarta.validation.*;
import jakarta.servlet.http.*;
import java.util.*;
import java.io.IOException;

import com.bethlabs.framework.common.pojo.PageParam;
import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.common.pojo.CommonResult;
import com.bethlabs.framework.common.util.object.BeanUtils;
import static com.bethlabs.framework.common.pojo.CommonResult.success;

import com.bethlabs.framework.excel.core.util.ExcelUtils;

import com.bethlabs.framework.apilog.core.annotation.ApiAccessLog;
import static com.bethlabs.framework.apilog.core.enums.OperateTypeEnum.*;


@Tag(name = "管理后台 - 页面维护")
@RestController
@RequestMapping("/system/page-maintenance")
@Validated
public class PageMaintenanceController {

    @Resource
    private PageMaintenanceService pageMaintenanceService;

    @PostMapping("/create")
    @Operation(summary = "创建页面维护")
    public CommonResult<Long> createPageMaintenance(@Valid @RequestBody PageMaintenanceSaveReqVO createReqVO) {
        return success(pageMaintenanceService.createPageMaintenance(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新页面维护")
    public CommonResult<Boolean> updatePageMaintenance(@Valid @RequestBody PageMaintenanceSaveReqVO updateReqVO) {
        pageMaintenanceService.updatePageMaintenance(updateReqVO);
        return success(true);
    }

    @PutMapping("/enable")
    @Operation(summary = "启用页面维护")
    public CommonResult<Boolean> enablePageMaintenance(@Valid @RequestBody PageMaintenanceEnableReqVO enableReqVO) {
        pageMaintenanceService.enablePageMaintenance(enableReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除页面维护")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deletePageMaintenance(@RequestParam("id") Long id) {
        pageMaintenanceService.deletePageMaintenance(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得页面维护")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<PageMaintenanceRespVO> getPageMaintenance(@RequestParam("id") Long id) {
        PageMaintenanceDO pageMaintenance = pageMaintenanceService.getPageMaintenance(id);
        return success(BeanUtils.toBean(pageMaintenance, PageMaintenanceRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得页面维护分页")
    public CommonResult<PageResult<PageMaintenanceRespVO>> getPageMaintenancePage(@Valid PageMaintenancePageReqVO pageReqVO) {
        PageResult<PageMaintenanceRespVO> pageResult = pageMaintenanceService.getPageMaintenancePage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/getPageMaintenanceUrl")
    @Operation(summary = "获得页面维护访问地址")
    public CommonResult<PageMaintenanceRespVO> getPageMaintenanceUrl(@RequestParam("productId") Long productId,@RequestParam("versionNumber")String versionNumber) {
        PageMaintenanceDO pageMaintenanceUrl = pageMaintenanceService.getPageMaintenanceUrl(productId, versionNumber);
        return success(BeanUtils.toBean(pageMaintenanceUrl, PageMaintenanceRespVO.class));
    }

}