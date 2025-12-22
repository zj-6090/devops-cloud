package com.bethlabs.module.system.controller.admin.alarmtype;

import com.bethlabs.framework.common.pojo.CommonResult;
import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.common.util.object.BeanUtils;
import com.bethlabs.module.system.controller.admin.alarmtype.vo.AlarmTypePageReqVO;
import com.bethlabs.module.system.controller.admin.alarmtype.vo.AlarmTypeRespVO;
import com.bethlabs.module.system.controller.admin.alarmtype.vo.AlarmTypeSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.alarmtype.AlarmTypeDO;
import com.bethlabs.module.system.service.alarmtype.AlarmTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bethlabs.framework.common.pojo.CommonResult.success;


@Tag(name = "管理后台 - 产品报警类型")
@RestController
@RequestMapping("/system/alarm-type")
@Validated
public class AlarmTypeController {

    @Resource
    private AlarmTypeService alarmTypeService;

    @PostMapping("/create")
    @Operation(summary = "创建报警类型")
    public CommonResult<Long> createAlarmType(@Valid @RequestBody AlarmTypeSaveReqVO createReqVO) {
        return success(alarmTypeService.createAlarmType(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新报警类型")
    public CommonResult<Boolean> updateAlarmType(@Valid @RequestBody AlarmTypeSaveReqVO updateReqVO) {
        alarmTypeService.updateAlarmType(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除报警类型")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteAlarmType(@RequestParam("id") Long id) {
        alarmTypeService.deleteAlarmType(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得报警类型")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<AlarmTypeRespVO> getAlarmType(@RequestParam("id") Long id) {
        AlarmTypeDO alarmType = alarmTypeService.getAlarmType(id);
        return success(BeanUtils.toBean(alarmType, AlarmTypeRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得报警类型分页")
    public CommonResult<PageResult<AlarmTypeRespVO>> getAlarmTypePage(@Valid AlarmTypePageReqVO pageReqVO) {
        PageResult<AlarmTypeRespVO> pageResult = alarmTypeService.getAlarmTypePage(pageReqVO);
        return success(pageResult);
    }

    @GetMapping("/list")
    @Operation(summary = "获得报警类型分页")
    public CommonResult<List<AlarmTypeRespVO>> getAlarmTypeList(@Valid AlarmTypePageReqVO pageReqVO) {
        return success(alarmTypeService.getAlarmTypeList(pageReqVO));
    }

}