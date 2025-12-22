package com.bethlabs.module.system.service.product;

import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.module.system.controller.admin.product.vo.ProductPageReqVO;
import com.bethlabs.module.system.controller.admin.product.vo.ProductSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.product.ProductDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 产品表-设备系列 Service 接口
 *
 * @author mjl
 */
public interface ProductService {

    /**
     * 创建产品表-设备系列
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProduct(@Valid ProductSaveReqVO createReqVO);

    /**
     * 更新产品表-设备系列
     *
     * @param updateReqVO 更新信息
     */
    void updateProduct(@Valid ProductSaveReqVO updateReqVO);

    /**
     * 删除产品表-设备系列
     *
     * @param id 编号
     */
    void deleteProduct(Long id);

    /**
     * 批量删除产品表-设备系列
     *
     * @param ids 编号
     */
    void deleteProductListByIds(List<Long> ids);

    /**
     * 获得产品表-设备系列
     *
     * @param id 编号
     * @return 产品表-设备系列
     */
    ProductDO getProduct(Long id);

    /**
     * 获得产品表-设备系列分页
     *
     * @param pageReqVO 分页查询
     * @return 产品表-设备系列分页
     */
    PageResult<ProductDO> getProductPage(ProductPageReqVO pageReqVO);

    /**
     * 获取产品列表
     * @return
     */
    List<ProductDO> getProductList();
}