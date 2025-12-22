package com.bethlabs.module.system.dal.mysql.productdev;

import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.mybatis.core.mapper.BaseMapperX;
import com.bethlabs.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.bethlabs.module.system.controller.admin.productdev.vo.ProductDevPageReqVO;
import com.bethlabs.module.system.dal.dataobject.productdev.ProductDevDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品设备接口 Mapper
 *
 * @author mjl
 */
@Mapper
public interface ProductDevMapper extends BaseMapperX<ProductDevDO> {

    default PageResult<ProductDevDO> selectPage(ProductDevPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductDevDO>()
                .orderByDesc(ProductDevDO::getId));
    }

}