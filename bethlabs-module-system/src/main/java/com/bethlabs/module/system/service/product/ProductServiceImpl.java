package com.bethlabs.module.system.service.product;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.bethlabs.framework.common.exception.ServiceException;
import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.common.util.object.BeanUtils;
import com.bethlabs.module.system.controller.admin.product.vo.ProductPageReqVO;
import com.bethlabs.module.system.controller.admin.product.vo.ProductSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.product.ProductDO;
import com.bethlabs.module.system.dal.mysql.product.ProductMapper;
import com.bethlabs.module.system.enums.ErrorCodeConstants;
import com.bethlabs.module.system.service.alarmtype.AlarmTypeService;
import com.bethlabs.module.system.service.productdev.ProductDevService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import static com.bethlabs.module.system.enums.ErrorCodeConstants.PRODUCT_NOT_EXISTS;


/**
 * 产品表-设备系列 Service 实现类
 *
 * @author mjl
 */
@Service
@Validated
public class ProductServiceImpl implements ProductService {

    @Resource
    private ProductMapper productMapper;

    @Autowired
    private ProductDevService productDevService;

    @Autowired
    private AlarmTypeService alarmTypeService;

    @Override
    public Long createProduct(ProductSaveReqVO createReqVO) {
        //校验
        validateProductCodeAndName(createReqVO);
        // 插入
        ProductDO product = BeanUtils.toBean(createReqVO, ProductDO.class);
        productMapper.insert(product);
        // 返回
        return product.getId();
    }


    @Override
    public void updateProduct(ProductSaveReqVO updateReqVO) {
        // 校验存在
        validateProductExists(updateReqVO.getId());

        // 校验产品编码和名称是否重复
        validateProductCodeAndName(updateReqVO);
        // 更新
        ProductDO updateObj = BeanUtils.toBean(updateReqVO, ProductDO.class);
        productMapper.updateById(updateObj);
    }

    @Override
    public void deleteProduct(Long id) {
        //校验
        productDeleteValidate(id);
        // 删除
        productMapper.deleteById(id);
    }

    /**
     * 删除产品接口校验
     * @param id
     */
    private void productDeleteValidate(Long id) {
        // 1.校验存在
        validateProductExists(id);
        //2.校验是否存在设备接口
        validateProductDevExists(id);
        //3.校验是否存在报警类型
        validateProductAlarmTypeExists(id);
        //4.todo 校验该产品是否绑定在设备
        validateDeviceExists(id);
    }

    @Override
    public void deleteProductListByIds(List<Long> ids) {
        // 删除
        productMapper.deleteByIds(ids);
    }


    private void validateProductExists(Long id) {
        if (productMapper.selectById(id)== null) {
            throw new ServiceException(PRODUCT_NOT_EXISTS);
        }
    }

    @Override
    public ProductDO getProduct(Long id) {
        return productMapper.selectById(id);
    }

    @Override
    public PageResult<ProductDO> getProductPage(ProductPageReqVO pageReqVO) {
        return productMapper.selectPage(pageReqVO);
    }

    @Override
    public List<ProductDO> getProductList() {
        return productMapper.selectList();
    }

    /**
     * 校验产品的编码和名称是否重复
     * @param createReqVO
     */
    private void validateProductCodeAndName(ProductSaveReqVO createReqVO) {
        //1.校验产品编码是否重复
        validateProductCode(createReqVO);
        //2.校验产品名称是否重复
        validateProductName(createReqVO);
    }

    /**
     * 校验产品编码是否重复
     * @param createReqVO
     */
    private void validateProductCode(ProductSaveReqVO createReqVO) {
        LambdaQueryWrapper<ProductDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductDO::getProductCode, createReqVO.getProductCode());
        ProductDO productDO = productMapper.selectOne(queryWrapper);
        if (productDO != null) {
            //判断是否是更新时，同一个产品,则直接返回，可以进行修改
            if (createReqVO.getId() != null && createReqVO.getId().equals(productDO.getId()))
                return;
            throw new ServiceException(ErrorCodeConstants.PRODUCT_CODE_EXISTS);
        }
    }

    /**
     * 校验产品名称是否重复
     * @param createReqVO
     */
    private void validateProductName(ProductSaveReqVO createReqVO) {
        LambdaQueryWrapper<ProductDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ProductDO::getProductName, createReqVO.getProductName());
        ProductDO productDO = productMapper.selectOne(queryWrapper);
        if (productDO != null) {
            //判断是否是更新时，同一个产品,则直接返回，可以进行修改
            if (createReqVO.getId() != null && createReqVO.getId().equals(productDO.getId()))
                return;
            throw new ServiceException(ErrorCodeConstants.PRODUCT_NAME_EXISTS);
        }
    }

    /**
     * 校验该产品是否绑定在设备
     * @param id
     */
    private void validateDeviceExists(Long id) {
    }

    /**
     * 校验是否存在产品报警类型
     * @param id
     */
    private void validateProductAlarmTypeExists(Long id) {
        if (alarmTypeService.getAlarmTypeCount(id) > 0) {
            throw new ServiceException(ErrorCodeConstants.PRODUCT_ALARM_TYPE_EXISTS);
        }
    }

    /**
     * 校验是否存在产品设备接口
     * @param id
     */
    private void validateProductDevExists(Long id) {
        if (productDevService.getProductDevCount(id) > 0) {
            throw new ServiceException(ErrorCodeConstants.PRODUCT_DEV_EXISTS);
        }
    }
}