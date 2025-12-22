package com.bethlabs.module.system.controller.admin.databuckups;

import com.bethlabs.module.system.controller.admin.databuckups.vo.DataBuckupsPageReqVO;
import com.bethlabs.module.system.controller.admin.databuckups.vo.DataBuckupsRespVO;
import com.bethlabs.module.system.controller.admin.databuckups.vo.DataBuckupsSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.databuckups.DataBuckupsDO;
import com.bethlabs.module.system.service.databuckups.DataBuckupsService;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;
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

@Tag(name = "管理后台 - 数据备份文件")
@RestController
@RequestMapping("/system/data-buckups")
@Validated
public class DataBuckupsController {

    @Resource
    private DataBuckupsService dataBuckupsService;

    @PostMapping("/create")
    @Operation(summary = "创建数据备份文件")
    public CommonResult<Long> createDataBuckups(@Valid @RequestBody DataBuckupsSaveReqVO createReqVO) {
        return success(dataBuckupsService.createDataBuckups(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新数据备份文件")
    public CommonResult<Boolean> updateDataBuckups(@Valid @RequestBody DataBuckupsSaveReqVO updateReqVO) {
        dataBuckupsService.updateDataBuckups(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除数据备份文件")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteDataBuckups(@RequestParam("id") Long id) {
        dataBuckupsService.deleteDataBuckups(id);
        return success(true);
    }

    @DeleteMapping("/delete-list")
    @Parameter(name = "ids", description = "编号", required = true)
    @Operation(summary = "批量删除数据备份文件")
    public CommonResult<Boolean> deleteDataBuckupsList(@RequestParam("ids") List<Long> ids) {
        dataBuckupsService.deleteDataBuckupsListByIds(ids);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得数据备份文件")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<DataBuckupsRespVO> getDataBuckups(@RequestParam("id") Long id) {
        DataBuckupsDO dataBuckups = dataBuckupsService.getDataBuckups(id);
        return success(BeanUtils.toBean(dataBuckups, DataBuckupsRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得数据备份文件分页")
    public CommonResult<PageResult<DataBuckupsRespVO>> getDataBuckupsPage(@Valid DataBuckupsPageReqVO pageReqVO) {
        PageResult<DataBuckupsDO> pageResult = dataBuckupsService.getDataBuckupsPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, DataBuckupsRespVO.class));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出数据备份文件 Excel")
    @ApiAccessLog(operateType = EXPORT)
    public void exportDataBuckupsExcel(@Valid DataBuckupsPageReqVO pageReqVO,
              HttpServletResponse response) throws IOException {
        pageReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<DataBuckupsDO> list = dataBuckupsService.getDataBuckupsPage(pageReqVO).getList();
        // 导出 Excel
        ExcelUtils.write(response, "数据备份文件.xls", "数据", DataBuckupsRespVO.class,
                        BeanUtils.toBean(list, DataBuckupsRespVO.class));
    }


    @GetMapping("/databuckup")
    public void databuckup(){
        dataBuckupsService.dataBaseBackUp();
    }
}