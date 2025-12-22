package com.bethlabs.module.system.service.productdev;

import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.module.system.controller.admin.productdev.vo.ProductDevPageReqVO;
import com.bethlabs.module.system.controller.admin.productdev.vo.ProductDevSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.productdev.ProductDevDO;
import jakarta.validation.Valid;

import java.util.List;

/**
 * 产品设备接口 Service 接口
 *
 * @author mjl
 */
public interface ProductDevService {

    /**
     * 创建产品设备接口
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductDev(@Valid ProductDevSaveReqVO createReqVO);

    /**
     * 更新产品设备接口
     *
     * @param updateReqVO 更新信息
     */
    void updateProductDev(@Valid ProductDevSaveReqVO updateReqVO);

    /**
     * 删除产品设备接口
     *
     * @param id 编号
     */
    void deleteProductDev(Long id);

    /**
     * 批量删除产品设备接口
     *
     * @param ids 编号
     */
    void deleteProductDevListByIds(List<Long> ids);

    /**
     * 获得产品设备接口
     *
     * @param id 编号
     * @return 产品设备接口
     */
    ProductDevDO getProductDev(Long id);

    /**
     * 获得产品设备接口分页
     *
     * @param pageReqVO 分页查询
     * @return 产品设备接口分页
     */
    PageResult<ProductDevDO> getProductDevPage(ProductDevPageReqVO pageReqVO);

    List<ProductDevDO> getProductDevList(ProductDevPageReqVO pageReqVO);

    /**
     * 通过产品id查看产品设备的数量
     *
     * @param productId
     * @return
     */
    Long getProductDevCount(Long productId);
}