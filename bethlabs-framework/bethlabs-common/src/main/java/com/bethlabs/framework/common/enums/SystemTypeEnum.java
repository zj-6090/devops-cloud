package com.bethlabs.framework.common.enums;

import cn.hutool.core.util.ObjUtil;
import com.bethlabs.framework.common.core.ArrayValuable;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 系统类型枚举
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum SystemTypeEnum implements ArrayValuable<Integer> {

    ADMIN(1, "系统管理"),
    OPER(2, "运维调度"),
    IDS(3, "安全审计"),
    REPAIR(4, "外勤维修");

    public static final Integer[] ARRAYS = Arrays.stream(values()).map(SystemTypeEnum::getType).toArray(Integer[]::new);

    /**
     * 系统类型值
     */
    private final Integer type;
    /**
     * 系统类型名
     */
    private final String name;

    @Override
    public Integer[] array() {
        return ARRAYS;
    }

    public static boolean isAdmin(Integer type) {
        return ObjUtil.equal(ADMIN.type, type);
    }

    public static boolean isOper(Integer type) {
        return ObjUtil.equal(OPER.type, type);
    }

    public static boolean isIds(Integer type) {
        return ObjUtil.equal(IDS.type, type);
    }

    public static boolean isRepair(Integer type) {
        return ObjUtil.equal(REPAIR.type, type);
    }

}
