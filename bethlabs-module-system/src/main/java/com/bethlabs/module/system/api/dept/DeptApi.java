package com.bethlabs.module.system.api.dept;

import com.bethlabs.framework.common.util.collection.CollectionUtils;
import com.bethlabs.module.system.api.dept.dto.DeptRespDTO;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 分组 API 接口
 *
 * @author 芋道源码
 */
public interface DeptApi {

    /**
     * 获得分组信息
     *
     * @param id 分组编号
     * @return 分组信息
     */
    DeptRespDTO getDept(Long id);

    /**
     * 获得分组信息数组
     *
     * @param ids 分组编号数组
     * @return 分组信息数组
     */
    List<DeptRespDTO> getDeptList(Collection<Long> ids);

    /**
     * 校验分组们是否有效。如下情况，视为无效：
     * 1. 分组编号不存在
     * 2. 分组被禁用
     *
     * @param ids 角色编号数组
     */
    void validateDeptList(Collection<Long> ids);

    /**
     * 获得指定编号的分组 Map
     *
     * @param ids 分组编号数组
     * @return 分组 Map
     */
    default Map<Long, DeptRespDTO> getDeptMap(Collection<Long> ids) {
        List<DeptRespDTO> list = getDeptList(ids);
        return CollectionUtils.convertMap(list, DeptRespDTO::getId);
    }

    /**
     * 获得指定分组的所有子分组
     *
     * @param id 分组编号
     * @return 子分组列表
     */
    List<DeptRespDTO> getChildDeptList(Long id);

}
