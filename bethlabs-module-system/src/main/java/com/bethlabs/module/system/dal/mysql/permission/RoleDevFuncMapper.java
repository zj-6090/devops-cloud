package com.bethlabs.module.system.dal.mysql.permission;

import com.bethlabs.framework.mybatis.core.mapper.BaseMapperX;
import com.bethlabs.module.system.dal.dataobject.permission.RoleDevFuncDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface RoleDevFuncMapper extends BaseMapperX<RoleDevFuncDO> {

    default List<RoleDevFuncDO> selectListByRoleId(Long roleId) {
        return selectList(RoleDevFuncDO::getRoleId, roleId);
    }

    default List<RoleDevFuncDO> selectListByRoleId(Collection<Long> roleIds) {
        return selectList(RoleDevFuncDO::getRoleId, roleIds);
    }

    default List<RoleDevFuncDO> selectListByDevFuncId(Long devFuncId) {
        return selectList(RoleDevFuncDO::getDevFuncId, devFuncId);
    }

    default void deleteListByRoleIdAndDevFuncIds(Long roleId, Collection<Long> devFuncIds) {
        delete(new LambdaQueryWrapper<RoleDevFuncDO>()
                .eq(RoleDevFuncDO::getRoleId, roleId)
                .in(RoleDevFuncDO::getDevFuncId, devFuncIds));
    }

    default void deleteListByDevFuncId(Long devFuncId) {
        delete(new LambdaQueryWrapper<RoleDevFuncDO>().eq(RoleDevFuncDO::getDevFuncId, devFuncId));
    }

    default void deleteListByRoleId(Long roleId) {
        delete(new LambdaQueryWrapper<RoleDevFuncDO>().eq(RoleDevFuncDO::getRoleId, roleId));
    }

}
