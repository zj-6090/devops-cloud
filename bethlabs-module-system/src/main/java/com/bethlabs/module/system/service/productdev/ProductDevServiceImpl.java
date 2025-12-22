package com.bethlabs.module.system.service.productdev;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bethlabs.framework.common.exception.ServiceException;
import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.common.util.object.BeanUtils;
import com.bethlabs.module.system.controller.admin.productdev.vo.ProductDevPageReqVO;
import com.bethlabs.module.system.controller.admin.productdev.vo.ProductDevSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.productdev.ProductDevDO;
import com.bethlabs.module.system.dal.mysql.productdev.ProductDevMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.bethlabs.module.system.enums.ErrorCodeConstants.PRODUCT_DEV_NAME_EXISTS;
import static com.bethlabs.module.system.enums.ErrorCodeConstants.PRODUCT_DEV_NOT_EXISTS;


/**
 * 产品设备接口 Service 实现类
 *
 * @author mjl
 */
@Service
@Validated
public class ProductDevServiceImpl implements ProductDevService {

    @Resource
    private ProductDevMapper productDevMapper;

    @Override
    public Long createProductDev(ProductDevSaveReqVO createReqVO) {
        //校验设备接口名称是否存在
        validateProductDevName(createReqVO);
        // 插入
        ProductDevDO productDev = BeanUtils.toBean(createReqVO, ProductDevDO.class);
        productDevMapper.insert(productDev);
        // 返回
        return productDev.getId();
    }

    @Override
    public void updateProductDev(ProductDevSaveReqVO updateReqVO) {
        // 校验存在
        validateProductDevExists(updateReqVO.getId());
        //校验设备接口名称是否存在
        validateProductDevName(updateReqVO);
        // 更新
        ProductDevDO updateObj = BeanUtils.toBean(updateReqVO, ProductDevDO.class);
        productDevMapper.updateById(updateObj);
    }

    @Override
    public void deleteProductDev(Long id) {
        // 校验存在
        validateProductDevExists(id);
        // 删除
        productDevMapper.deleteById(id);
    }

    @Override
    public void deleteProductDevListByIds(List<Long> ids) {
        // 删除
        productDevMapper.deleteByIds(ids);
    }

    @Override
    public ProductDevDO getProductDev(Long id) {
        return productDevMapper.selectById(id);
    }

    @Override
    public PageResult<ProductDevDO> getProductDevPage(ProductDevPageReqVO pageReqVO) {
        return productDevMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ProductDevDO> getProductDevList(ProductDevPageReqVO pageReqVO) {
        LambdaQueryWrapper<ProductDevDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductDevDO::getProductId, pageReqVO.getProductId());
        queryWrapper.orderByAsc(ProductDevDO::getId);
        return productDevMapper.selectList(queryWrapper);
    }

    @Override
    public Long getProductDevCount(Long productId) {
        LambdaQueryWrapper<ProductDevDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductDevDO::getProductId, productId);
        return productDevMapper.selectCount(queryWrapper);
    }

    /**
     * 校验设备接口名称是否存在
     * @param createReqVO
     */
    private void validateProductDevName(ProductDevSaveReqVO createReqVO) {
        LambdaQueryWrapper<ProductDevDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductDevDO::getInterfaceName, createReqVO.getInterfaceName());
        queryWrapper.eq(ProductDevDO::getProductId, createReqVO.getProductId());
        ProductDevDO productDevDO = productDevMapper.selectOne(queryWrapper);
        if (productDevDO != null) {
            //判断是否同一个设备接口
            if (createReqVO.getId() != null && productDevDO.getId().equals(createReqVO.getId()))
                return;
            throw new ServiceException(PRODUCT_DEV_NAME_EXISTS);
        }
    }

    private void validateProductDevExists(Long id) {
        if (productDevMapper.selectById(id) == null) {
            throw new ServiceException(PRODUCT_DEV_NOT_EXISTS);
        }
    }
}