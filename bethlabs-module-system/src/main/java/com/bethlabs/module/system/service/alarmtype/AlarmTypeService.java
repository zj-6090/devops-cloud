package com.bethlabs.module.system.service.alarmtype;

import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.module.system.controller.admin.alarmtype.vo.AlarmTypePageReqVO;
import com.bethlabs.module.system.controller.admin.alarmtype.vo.AlarmTypeRespVO;
import com.bethlabs.module.system.controller.admin.alarmtype.vo.AlarmTypeSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.alarmtype.AlarmTypeDO;
import jakarta.validation.Valid;

import java.util.List;


/**
 * 报警类型 Service 接口
 *
 * @author mjl
 */
public interface AlarmTypeService {

    /**
     * 创建报警类型
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAlarmType(@Valid AlarmTypeSaveReqVO createReqVO);

    /**
     * 更新报警类型
     *
     * @param updateReqVO 更新信息
     */
    void updateAlarmType(@Valid AlarmTypeSaveReqVO updateReqVO);

    /**
     * 删除报警类型
     *
     * @param id 编号
     */
    void deleteAlarmType(Long id);

    /**
     * 批量删除报警类型
     *
     * @param ids 编号
     */
    void deleteAlarmTypeListByIds(List<Long> ids);

    /**
     * 获得报警类型
     *
     * @param id 编号
     * @return 报警类型
     */
    AlarmTypeDO getAlarmType(Long id);

    /**
     * 获得报警类型分页
     *
     * @param pageReqVO 分页查询
     * @return 报警类型分页
     */
    PageResult<AlarmTypeRespVO> getAlarmTypePage(AlarmTypePageReqVO pageReqVO);

    /**
     * 根据产品id查询告警类型总数
     *
     * @param productId 产品id
     * @return
     */
    Long getAlarmTypeCount(Long productId);

    /**
     * 查询告警类型列表
     * @param pageReqVO
     * @return
     */
    List<AlarmTypeRespVO> getAlarmTypeList(AlarmTypePageReqVO pageReqVO);
}