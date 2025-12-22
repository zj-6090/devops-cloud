package com.bethlabs.module.system.controller.admin.productdev;

import com.bethlabs.framework.common.pojo.CommonResult;
import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.common.util.object.BeanUtils;
import com.bethlabs.module.system.controller.admin.productdev.vo.ProductDevPageReqVO;
import com.bethlabs.module.system.controller.admin.productdev.vo.ProductDevRespVO;
import com.bethlabs.module.system.controller.admin.productdev.vo.ProductDevSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.productdev.ProductDevDO;
import com.bethlabs.module.system.service.productdev.ProductDevService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bethlabs.framework.common.pojo.CommonResult.success;


@Tag(name = "管理后台 - 产品设备接口")
@RestController
@RequestMapping("/system/product-dev")
@Validated
public class ProductDevController {

    @Resource
    private ProductDevService productDevService;

    @PostMapping("/create")
    @Operation(summary = "创建产品设备接口")
    public CommonResult<Long> createProductDev(@Valid @RequestBody ProductDevSaveReqVO createReqVO) {
        return success(productDevService.createProductDev(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品设备接口")
    public CommonResult<Boolean> updateProductDev(@Valid @RequestBody ProductDevSaveReqVO updateReqVO) {
        productDevService.updateProductDev(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品设备接口")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteProductDev(@RequestParam("id") Long id) {
        productDevService.deleteProductDev(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品设备接口")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ProductDevRespVO> getProductDev(@RequestParam("id") Long id) {
        ProductDevDO productDev = productDevService.getProductDev(id);
        return success(BeanUtils.toBean(productDev, ProductDevRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得产品设备接口分页")
    public CommonResult<PageResult<ProductDevRespVO>> getProductDevPage(@Valid ProductDevPageReqVO pageReqVO) {
        PageResult<ProductDevDO> pageResult = productDevService.getProductDevPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ProductDevRespVO.class));
    }

    @GetMapping("/list")
    @Operation(summary = "获得产品设备接口列表")
    public CommonResult<List<ProductDevRespVO>> listProductDevs(@Valid ProductDevPageReqVO pageReqVO) {
        List<ProductDevDO> list = productDevService.getProductDevList(pageReqVO);
        return success(BeanUtils.toBean(list, ProductDevRespVO.class));
    }
}