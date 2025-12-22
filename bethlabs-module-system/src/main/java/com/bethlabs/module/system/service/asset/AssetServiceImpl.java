package com.bethlabs.module.system.service.asset;

import cn.hutool.core.util.ObjectUtil;
import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.bethlabs.framework.common.enums.CommonStatusEnum;
import com.bethlabs.framework.common.enums.UserTypeEnum;
import com.bethlabs.framework.common.exception.ServiceException;
import com.bethlabs.framework.common.exception.enums.GlobalErrorCodeConstants;
import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.common.util.monitor.TracerUtils;
import com.bethlabs.framework.common.util.servlet.ServletUtils;
import com.bethlabs.framework.common.util.validation.ValidationUtils;
import com.bethlabs.module.system.api.logger.dto.LoginLogCreateReqDTO;
import com.bethlabs.module.system.api.sms.SmsCodeApi;
import com.bethlabs.module.system.api.sms.dto.code.SmsCodeUseReqDTO;
import com.bethlabs.module.system.api.social.dto.SocialUserBindReqDTO;
import com.bethlabs.module.system.api.social.dto.SocialUserRespDTO;
import com.bethlabs.module.system.controller.admin.asset.vo.BrandReqVO;
import com.bethlabs.module.system.controller.admin.auth.vo.*;
import com.bethlabs.module.system.convert.auth.AuthConvert;
import com.bethlabs.module.system.dal.dataobject.asset.BrandDO;
import com.bethlabs.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;
import com.bethlabs.module.system.dal.dataobject.user.AdminUserDO;
import com.bethlabs.module.system.dal.mysql.asset.AssetMapper;
import com.bethlabs.module.system.enums.logger.LoginLogTypeEnum;
import com.bethlabs.module.system.enums.logger.LoginResultEnum;
import com.bethlabs.module.system.enums.oauth2.OAuth2ClientConstants;
import com.bethlabs.module.system.enums.sms.SmsSceneEnum;
import com.bethlabs.module.system.service.logger.LoginLogService;
import com.bethlabs.module.system.service.member.MemberService;
import com.bethlabs.module.system.service.oauth2.OAuth2TokenService;
import com.bethlabs.module.system.service.social.SocialUserService;
import com.bethlabs.module.system.service.user.AdminUserService;
import com.google.common.annotations.VisibleForTesting;
import jakarta.annotation.Resource;
import jakarta.validation.Validator;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.bethlabs.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.bethlabs.framework.common.util.servlet.ServletUtils.getClientIP;
import static com.bethlabs.module.system.enums.ErrorCodeConstants.*;

/**
 * Auth Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Slf4j
public class AssetServiceImpl implements AssetService {

    @Resource
    private AssetMapper assetMapper;

    @Override
    public Long create(BrandDO brandDO) {
        checkBrandNameOrCodeExists(brandDO);
        assetMapper.insert(brandDO);
        return brandDO.getId();
    }

    @Override
    public Integer delete(BrandReqVO reqVO) {
        return assetMapper.deleteBatchIds(reqVO.getBrandIds());
    }

    @Override
    public Integer update(BrandDO brandDO) {
        checkBrandNameOrCodeExists(brandDO);
        return assetMapper.updateById(brandDO);
    }

    @Override
    public PageResult<BrandDO> page(BrandReqVO reqVO) {
        QueryWrapper<BrandDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq( Objects.nonNull(reqVO.getDevType()), "dev_type", reqVO.getDevType());
        return assetMapper.selectPage(reqVO, queryWrapper);
    }

    /**
     * 检查品牌名称或编号是否存在
     * @param brandDO
     */
    private void checkBrandNameOrCodeExists(BrandDO brandDO) {
        if (assetMapper.selectByBrandName(brandDO)!=null) throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "品牌名称已存在！");
        if (assetMapper.selectByBrandCode(brandDO)!=null) throw new ServiceException(GlobalErrorCodeConstants.BAD_REQUEST.getCode(), "品牌编号已存在！");
    }
}
