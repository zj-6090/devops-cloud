package com.bethlabs.module.system.dal.mysql.asset;

import com.bethlabs.framework.mybatis.core.mapper.BaseMapperX;
import com.bethlabs.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.bethlabs.module.system.controller.admin.asset.vo.BrandReqVO;
import com.bethlabs.module.system.controller.admin.dept.vo.dept.DeptListReqVO;
import com.bethlabs.module.system.dal.dataobject.asset.BrandDO;
import com.bethlabs.module.system.dal.dataobject.dept.DeptDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface AssetMapper extends BaseMapperX<BrandDO> {

    default List<BrandDO> selectList(BrandReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<BrandDO>()
                .eq(reqVO.getDevType() != null, BrandDO::getDevType, reqVO.getDevType()));
    }

    default BrandDO selectById(BrandDO brandDO) {
        return selectOne(BrandDO::getId, brandDO.getId());
    }

    default BrandDO selectByBrandCode(BrandDO brandDO) {
        return selectOne(BrandDO::getBrandCode, brandDO.getBrandCode());
    }

    default BrandDO selectByBrandName(BrandDO brandDO) {
        return selectOne(BrandDO::getBrandName, brandDO.getBrandName());
    }


}
