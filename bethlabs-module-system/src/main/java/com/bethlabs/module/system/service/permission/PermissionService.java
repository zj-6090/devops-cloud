package com.bethlabs.module.system.service.permission;

import com.bethlabs.framework.common.biz.system.permission.dto.DeptDataPermissionRespDTO;

import java.util.Collection;
import java.util.Set;

import static java.util.Collections.singleton;

/**
 * 权限 Service 接口
 * <p>
 * 提供用户-角色、角色-菜单、角色-分组的关联权限处理
 *
 * @author 芋道源码
 */
public interface PermissionService {

    /**
     * 判断是否有权限，任一一个即可
     *
     * @param userId      用户编号
     * @param permissions 权限
     * @return 是否
     */
    boolean hasAnyPermissions(Long userId, String... permissions);

    /**
     * 判断是否有角色，任一一个即可
     *
     * @param roles 角色数组
     * @return 是否
     */
    boolean hasAnyRoles(Long userId, String... roles);

    // ========== 角色-菜单的相关方法  ==========

    /**
     * 设置角色菜单
     *
     * @param roleId  角色编号
     * @param menuIds 菜单编号集合
     */
    void assignRoleMenu(Long roleId, Set<Long> menuIds);

    /**
     * 处理角色删除时，删除关联授权数据
     *
     * @param roleId 角色编号
     */
    void processRoleDeleted(Long roleId);

    /**
     * 处理菜单删除时，删除关联授权数据
     *
     * @param menuId 菜单编号
     */
    void processMenuDeleted(Long menuId);

    /**
     * 获得角色拥有的菜单编号集合
     *
     * @param roleId 角色编号
     * @return 菜单编号集合
     */
    default Set<Long> getRoleMenuListByRoleId(Long roleId) {
        return getRoleMenuListByRoleId(singleton(roleId));
    }

    /**
     * 获得角色们拥有的菜单编号集合
     *
     * @param roleIds 角色编号数组
     * @return 菜单编号集合
     */
    Set<Long> getRoleMenuListByRoleId(Collection<Long> roleIds);

    /**
     * 获得拥有指定菜单的角色编号数组，从缓存中获取
     *
     * @param menuId 菜单编号
     * @return 角色编号数组
     */
    Set<Long> getMenuRoleIdListByMenuIdFromCache(Long menuId);

    // ========== 用户-角色的相关方法  ==========

    /**
     * 设置用户角色
     *
     * @param userId  角色编号
     * @param roleIds 角色编号集合
     */
    void assignUserRole(Long userId, Set<Long> roleIds);

    /**
     * 处理用户删除时，删除关联授权数据
     *
     * @param userId 用户编号
     */
    void processUserDeleted(Long userId);

    /**
     * 获得拥有多个角色的用户编号集合
     *
     * @param roleIds 角色编号集合
     * @return 用户编号集合
     */
    Set<Long> getUserRoleIdListByRoleId(Collection<Long> roleIds);

    /**
     * 获得用户拥有的角色编号集合
     *
     * @param userId 用户编号
     * @return 角色编号集合
     */
    Set<Long> getUserRoleIdListByUserId(Long userId);

    /**
     * 获得用户拥有的角色编号集合，从缓存中获取
     *
     * @param userId 用户编号
     * @return 角色编号集合
     */
    Set<Long> getUserRoleIdListByUserIdFromCache(Long userId);

    // ========== 用户-分组的相关方法  ==========

    /**
     * 设置角色的数据权限
     *
     * @param roleId           角色编号
     * @param dataScope        数据范围
     * @param dataScopeDeptIds 分组编号数组
     * @param dataScopeProjectIds 项目编号数组
     */
    void assignRoleDataScope(Long roleId,
                             Integer dataScope,
                             Set<Long> dataScopeDeptIds,
                             Set<Long> dataScopeProjectIds);

    /**
     * 获得登陆用户的分组数据权限
     *
     * @param userId 用户编号
     * @return 分组数据权限
     */
    DeptDataPermissionRespDTO getDeptDataPermission(Long userId);


    // ========== 角色-设备功能权限的相关方法  ==========
     /**
     * 设置角色的设备功能权限
     *
     * @param roleId      角色编号
     * @param devFuncIds  设备功能编号集合
     */
    void assignRoleDevFunc(Long roleId, Set<Long> devFuncIds);

    /**
     * 获得角色拥有的设备功能编号集合
     *
     * @param roleId 角色编号
     * @return 设备功能编号集合
     */
    default Set<Long> getRoleDevFuncListByRoleId(Long roleId) {
        return getRoleDevFuncListByRoleId(singleton(roleId));
    }

    /**
     * 获得角色们拥有的设备功能编号集合
     *
     * @param roleIds 角色编号数组
     * @return 设备功能编号集合
     */
    Set<Long> getRoleDevFuncListByRoleId(Collection<Long> roleIds);
}
