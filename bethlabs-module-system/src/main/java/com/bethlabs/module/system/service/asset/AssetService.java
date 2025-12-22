package com.bethlabs.module.system.service.asset;

import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.module.system.controller.admin.asset.vo.BrandReqVO;
import com.bethlabs.module.system.controller.admin.auth.vo.*;
import com.bethlabs.module.system.dal.dataobject.asset.BrandDO;
import com.bethlabs.module.system.dal.dataobject.user.AdminUserDO;
import jakarta.validation.Valid;

public interface AssetService {

    Long create(BrandDO brandDO);

    Integer delete(BrandReqVO reqVO);

    Integer update(BrandDO brandDO);

    PageResult<BrandDO> page(BrandReqVO reqVO);
}
