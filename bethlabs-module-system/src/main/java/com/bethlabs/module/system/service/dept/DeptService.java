package com.bethlabs.module.system.service.dept;

import com.bethlabs.framework.common.util.collection.CollectionUtils;
import com.bethlabs.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import com.bethlabs.module.system.controller.admin.dept.vo.dept.DeptSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.dept.DeptDO;

import java.util.*;

/**
 * 分组 Service 接口
 *
 * @author 芋道源码
 */
public interface DeptService {

    /**
     * 创建分组
     *
     * @param createReqVO 分组信息
     * @return 分组编号
     */
    Long createDept(DeptSaveReqVO createReqVO);

    /**
     * 更新分组
     *
     * @param updateReqVO 分组信息
     */
    void updateDept(DeptSaveReqVO updateReqVO);

    /**
     * 删除分组
     *
     * @param id 分组编号
     */
    void deleteDept(Long id);

    /**
     * 获得分组信息
     *
     * @param id 分组编号
     * @return 分组信息
     */
    DeptDO getDept(Long id);

    /**
     * 获得分组信息数组
     *
     * @param ids 分组编号数组
     * @return 分组信息数组
     */
    List<DeptDO> getDeptList(Collection<Long> ids);

    /**
     * 筛选分组列表
     *
     * @param reqVO 筛选条件请求 VO
     * @return 分组列表
     */
    List<DeptDO> getDeptList(DeptListReqVO reqVO);

    /**
     * 获得指定编号的分组 Map
     *
     * @param ids 分组编号数组
     * @return 分组 Map
     */
    default Map<Long, DeptDO> getDeptMap(Collection<Long> ids) {
        List<DeptDO> list = getDeptList(ids);
        return CollectionUtils.convertMap(list, DeptDO::getId);
    }

    /**
     * 获得指定分组的所有子分组
     *
     * @param id 分组编号
     * @return 子分组列表
     */
    default List<DeptDO> getChildDeptList(Long id) {
        return getChildDeptList(Collections.singleton(id));
    }

    /**
     * 获得指定分组的所有子分组
     *
     * @param ids 分组编号数组
     * @return 子分组列表
     */
    List<DeptDO> getChildDeptList(Collection<Long> ids);

    /**
     * 获得指定领导者的分组列表
     *
     * @param id 领导者编号
     * @return 分组列表
     */
    List<DeptDO> getDeptListByLeaderUserId(Long id);

    /**
     * 获得所有子分组，从缓存中
     *
     * @param id 父分组编号
     * @return 子分组列表
     */
    Set<Long> getChildDeptIdListFromCache(Long id);

    /**
     * 校验分组们是否有效。如下情况，视为无效：
     * 1. 分组编号不存在
     * 2. 分组被禁用
     *
     * @param ids 角色编号数组
     */
    void validateDeptList(Collection<Long> ids);

}
