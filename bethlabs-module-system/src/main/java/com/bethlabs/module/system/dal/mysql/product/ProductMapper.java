package com.bethlabs.module.system.dal.mysql.product;

import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.mybatis.core.mapper.BaseMapperX;
import com.bethlabs.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.bethlabs.module.system.controller.admin.product.vo.ProductPageReqVO;
import com.bethlabs.module.system.dal.dataobject.product.ProductDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 产品表-设备系列 Mapper
 *
 * @author mjl
 */
@Mapper
public interface ProductMapper extends BaseMapperX<ProductDO> {

    default PageResult<ProductDO> selectPage(ProductPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<ProductDO>()
                .likeIfPresent(ProductDO::getProductName, reqVO.getProductName())
                .orderByDesc(ProductDO::getId));
    }

}