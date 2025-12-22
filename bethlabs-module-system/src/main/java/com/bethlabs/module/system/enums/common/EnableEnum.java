package com.bethlabs.module.system.enums.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EnableEnum {

    INABLE(0,"禁用"),
    ENABLE(1,"启用");


    private final Integer key;

    private final String name;
}
