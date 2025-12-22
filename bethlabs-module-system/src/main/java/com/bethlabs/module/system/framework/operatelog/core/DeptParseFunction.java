package com.bethlabs.module.system.framework.operatelog.core;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.bethlabs.module.system.dal.dataobject.dept.DeptDO;
import com.bethlabs.module.system.service.dept.DeptService;
import com.mzt.logapi.service.IParseFunction;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 分组名字的 {@link IParseFunction} 实现类
 *
 * @author HUIHUI
 */
@Slf4j
@Component
public class DeptParseFunction implements IParseFunction {

    public static final String NAME = "getDeptById";

    @Resource
    private DeptService deptService;

    @Override
    public String functionName() {
        return NAME;
    }

    @Override
    public String apply(Object value) {
        if (StrUtil.isEmptyIfStr(value)) {
            return "";
        }

        // 获取分组信息
        DeptDO dept = deptService.getDept(Convert.toLong(value));
        if (dept == null) {
            log.warn("[apply][获取分组{{}}为空", value);
            return "";
        }
        return dept.getName();
    }

}
