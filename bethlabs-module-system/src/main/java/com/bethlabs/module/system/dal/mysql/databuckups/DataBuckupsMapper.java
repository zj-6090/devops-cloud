package com.bethlabs.module.system.dal.mysql.databuckups;

import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.mybatis.core.mapper.BaseMapperX;
import com.bethlabs.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.bethlabs.module.system.controller.admin.databuckups.vo.DataBuckupsPageReqVO;
import com.bethlabs.module.system.dal.dataobject.databuckups.DataBuckupsDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 数据备份文件 Mapper
 *
 * @author mjl
 */
@Mapper
public interface DataBuckupsMapper extends BaseMapperX<DataBuckupsDO> {

    default PageResult<DataBuckupsDO> selectPage(DataBuckupsPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<DataBuckupsDO>()
                .orderByDesc(DataBuckupsDO::getCreateTime));
    }

}