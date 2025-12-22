package com.bethlabs.module.system.controller.admin.asset;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.bethlabs.framework.common.enums.CommonStatusEnum;
import com.bethlabs.framework.common.enums.UserTypeEnum;
import com.bethlabs.framework.common.pojo.CommonResult;
import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.security.config.SecurityProperties;
import com.bethlabs.framework.security.core.util.SecurityFrameworkUtils;
import com.bethlabs.module.system.controller.admin.asset.vo.BrandReqVO;
import com.bethlabs.module.system.controller.admin.auth.vo.*;
import com.bethlabs.module.system.convert.auth.AuthConvert;
import com.bethlabs.module.system.dal.dataobject.asset.BrandDO;
import com.bethlabs.module.system.dal.dataobject.permission.MenuDO;
import com.bethlabs.module.system.dal.dataobject.permission.RoleDO;
import com.bethlabs.module.system.dal.dataobject.user.AdminUserDO;
import com.bethlabs.module.system.enums.logger.LoginLogTypeEnum;
import com.bethlabs.module.system.service.asset.AssetService;
import com.bethlabs.module.system.service.auth.AdminAuthService;
import com.bethlabs.module.system.service.permission.MenuService;
import com.bethlabs.module.system.service.permission.PermissionService;
import com.bethlabs.module.system.service.permission.RoleService;
import com.bethlabs.module.system.service.social.SocialClientService;
import com.bethlabs.module.system.service.user.AdminUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.annotation.security.PermitAll;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.bethlabs.framework.common.pojo.CommonResult.success;
import static com.bethlabs.framework.common.util.collection.CollectionUtils.convertSet;
import static com.bethlabs.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "资产管理 - 品牌管理")
@RestController
@RequestMapping("/system/asset/brand")
@Validated
@Slf4j
public class BrandController {

    @Resource
    private AssetService assetService;

    @PostMapping("/create")
    @PermitAll
    @Operation(summary = "添加品牌管理")
    public CommonResult<Long> create(@RequestBody BrandDO brandDO) {
        return success(assetService.create(brandDO));
    }
    @DeleteMapping("/delete")
    @PermitAll
    @Operation(summary = "删除品牌管理")
    public CommonResult<Integer> delete(BrandReqVO reqVO) {
        return success(assetService.delete(reqVO));
    }
    @PutMapping("/update")
    @PermitAll
    @Operation(summary = "更新品牌管理")
    public CommonResult<Integer> update(@RequestBody BrandDO brandDO) {
        return success(assetService.update(brandDO));
    }
    @GetMapping("/page")
    @PermitAll
    @Operation(summary = "品牌管理分页")
    public CommonResult<PageResult<BrandDO>> page(BrandReqVO reqVO) {
        return success(assetService.page(reqVO));
    }

}
