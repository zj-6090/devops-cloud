package com.btlab.devops.module.system.controller.admin.logger;

import com.btlab.devops.framework.apilog.core.annotation.ApiAccessLog;
import com.btlab.devops.framework.common.pojo.CommonResult;
import com.btlab.devops.framework.common.pojo.PageParam;
import com.btlab.devops.framework.common.pojo.PageResult;
import com.btlab.devops.framework.common.util.object.BeanUtils;
import com.btlab.devops.framework.excel.core.util.ExcelUtils;
import com.btlab.devops.framework.translate.core.TranslateUtils;
import com.btlab.devops.module.system.controller.admin.logger.vo.operatelog.OperateLogPageReqVO;
import com.btlab.devops.module.system.controller.admin.logger.vo.operatelog.OperateLogRespVO;
import com.btlab.devops.module.system.dal.dataobject.logger.OperateLogDO;
import com.btlab.devops.module.system.service.logger.OperateLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.btlab.devops.framework.apilog.core.enums.OperateTypeEnum.EXPORT;
import static com.btlab.devops.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 操作日志")
@RestController
@RequestMapping("/system/operate-log")
@Validated
public class OperateLogController {

    @Resource
    private OperateLogService operateLogService;

    @GetMapping("/page")
    @Operation(summary = "查看操作日志分页列表")
    @PreAuthorize("@ss.hasPermission('system:operate-log:query')")
    public CommonResult<PageResult<OperateLogRespVO>> pageOperateLog(@Valid OperateLogPageReqVO pageReqVO) {
        PageResult<OperateLogDO> pageResult = operateLogService.getOperateLogPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, OperateLogRespVO.class));
    }

    @Operation(summary = "导出操作日志")
    @GetMapping("/export")
    @PreAuthorize("@ss.hasPermission('system:operate-log:export')")
    @ApiAccessLog(operateType = EXPORT)
    public void exportOperateLog(HttpServletResponse response, @Valid OperateLogPageReqVO exportReqVO) throws IOException {
        exportReqVO.setPageSize(PageParam.PAGE_SIZE_NONE);
        List<OperateLogDO> list = operateLogService.getOperateLogPage(exportReqVO).getList();
        ExcelUtils.write(response, "操作日志.xls", "数据列表", OperateLogRespVO.class,
                TranslateUtils.translate(BeanUtils.toBean(list, OperateLogRespVO.class)));
    }

}
