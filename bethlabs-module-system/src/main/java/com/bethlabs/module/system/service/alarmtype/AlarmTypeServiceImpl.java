package com.bethlabs.module.system.service.alarmtype;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bethlabs.framework.common.exception.ServiceException;
import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.common.util.object.BeanUtils;
import com.bethlabs.module.system.controller.admin.alarmtype.vo.AlarmTypePageReqVO;
import com.bethlabs.module.system.controller.admin.alarmtype.vo.AlarmTypeRespVO;
import com.bethlabs.module.system.controller.admin.alarmtype.vo.AlarmTypeSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.alarmtype.AlarmTypeDO;
import com.bethlabs.module.system.dal.mysql.alarmtype.AlarmTypeMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.bethlabs.module.system.enums.ErrorCodeConstants.*;

/**
 * 报警类型 Service 实现类
 *
 * @author mjl
 */
@Service
@Validated
public class AlarmTypeServiceImpl implements AlarmTypeService {

    @Resource
    private AlarmTypeMapper alarmTypeMapper;

    @Override
    public Long createAlarmType(AlarmTypeSaveReqVO createReqVO) {
        //校验报警类型是否存在，如果存在，则不允许添加
        validateAlarmType (createReqVO);
        // 插入
        AlarmTypeDO alarmType = BeanUtils.toBean(createReqVO, AlarmTypeDO.class);
        alarmTypeMapper.insert(alarmType);
        // 返回
        return alarmType.getId();
    }

    @Override
    public void updateAlarmType(AlarmTypeSaveReqVO updateReqVO) {
        // 校验存在
        validateAlarmTypeExists(updateReqVO.getId());
        //校验报警类型是否存在，如果存在，则不允许修改
        validateAlarmType (updateReqVO);
        // 更新
        AlarmTypeDO updateObj = BeanUtils.toBean(updateReqVO, AlarmTypeDO.class);
        alarmTypeMapper.updateById(updateObj);
    }

    @Override
    public void deleteAlarmType(Long id) {
        // 校验存在
        validateAlarmTypeExists(id);
        // 删除
        alarmTypeMapper.deleteById(id);
    }

    @Override
    public void deleteAlarmTypeListByIds(List<Long> ids) {
        // 删除
        alarmTypeMapper.deleteByIds(ids);
    }


    private void validateAlarmTypeExists(Long id) {
        if (alarmTypeMapper.selectById(id) == null) {
            throw new ServiceException(ALARM_TYPE_NOT_EXISTS);
        }
    }

    @Override
    public AlarmTypeDO getAlarmType(Long id) {
        return alarmTypeMapper.selectById(id);
    }

    @Override
    public PageResult<AlarmTypeRespVO> getAlarmTypePage(AlarmTypePageReqVO pageReqVO) {
        IPage<AlarmTypeRespVO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        page = alarmTypeMapper.selectPage(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public Long getAlarmTypeCount(Long productId) {
        LambdaQueryWrapper<AlarmTypeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AlarmTypeDO::getProductId, productId);
        return alarmTypeMapper.selectCount(queryWrapper);
    }

    @Override
    public List<AlarmTypeRespVO> getAlarmTypeList(AlarmTypePageReqVO pageReqVO) {
        return alarmTypeMapper.selectPage(pageReqVO);
    }

    private void validateAlarmType(AlarmTypeSaveReqVO createReqVO) {
        LambdaQueryWrapper<AlarmTypeDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AlarmTypeDO::getAlarmLevelId, createReqVO.getAlarmLevelId());
        queryWrapper.eq(AlarmTypeDO::getProductId, createReqVO.getProductId());
        AlarmTypeDO alarmTypeDO = alarmTypeMapper.selectOne(queryWrapper);
        if (alarmTypeDO != null) {
            //判断是否同一个设备接口
            if (createReqVO.getId() != null && alarmTypeDO.getId().equals(createReqVO.getId()))
                return;
            throw new ServiceException(ALARM_TYPE_EXISTS);
        }
    }
}