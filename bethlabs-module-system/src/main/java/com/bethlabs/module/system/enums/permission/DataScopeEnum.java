package com.bethlabs.module.system.enums.permission;

import com.bethlabs.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 数据范围枚举类
 *
 * 用于实现数据级别的权限
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum DataScopeEnum implements ArrayValuable<Integer> {

    DATASCOPE_ALL(1), // 全部数据权限

    DATASCOPE_DEPT_ONLY(2), // 指定分组数据权限
    DATASCOPE_PROJECT_ONLY(3), // 指定项目数据权限
    DADASCOPE_DEPT_AND_PROJECT(4); // 指定分组和项目数据权限


    /**
     * 范围
     */
    private final Integer scope;

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(DataScopeEnum::getScope).toArray(Integer[]::new);

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

}
