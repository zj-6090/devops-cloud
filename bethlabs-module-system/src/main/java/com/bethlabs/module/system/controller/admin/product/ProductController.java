package com.bethlabs.module.system.controller.admin.product;

import com.bethlabs.framework.common.pojo.CommonResult;
import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.common.util.object.BeanUtils;
import com.bethlabs.module.system.controller.admin.product.vo.ProductPageReqVO;
import com.bethlabs.module.system.controller.admin.product.vo.ProductRespVO;
import com.bethlabs.module.system.controller.admin.product.vo.ProductSaveReqVO;
import com.bethlabs.module.system.controller.admin.product.vo.ProductSimpleRespVO;
import com.bethlabs.module.system.dal.dataobject.product.ProductDO;
import com.bethlabs.module.system.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.bethlabs.framework.common.pojo.CommonResult.success;


@Tag(name = "管理后台 - 产品表-设备系列")
@RestController
@RequestMapping("/system/product")
@Validated
public class ProductController {

    @Resource
    private ProductService productService;

    @PostMapping("/create")
    @Operation(summary = "创建产品")
    public CommonResult<Long> createProduct(@Valid @RequestBody ProductSaveReqVO createReqVO) {
        return success(productService.createProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新产品")
    public CommonResult<Boolean> updateProduct(@Valid @RequestBody ProductSaveReqVO updateReqVO) {
        productService.updateProduct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "删除产品")
    @Parameter(name = "id", description = "编号", required = true)
    public CommonResult<Boolean> deleteProduct(@RequestParam("id") Long id) {
        productService.deleteProduct(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "获得产品")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ProductRespVO> getProduct(@RequestParam("id") Long id) {
        ProductDO product = productService.getProduct(id);
        return success(BeanUtils.toBean(product, ProductRespVO.class));
    }

    @GetMapping("/page")
    @Operation(summary = "获得产品分页")
    public CommonResult<PageResult<ProductRespVO>> getProductPage(@Valid ProductPageReqVO pageReqVO) {
        PageResult<ProductDO> pageResult = productService.getProductPage(pageReqVO);
        return success(BeanUtils.toBean(pageResult, ProductRespVO.class));
    }

    @GetMapping("/list-simple")
    @Operation(summary = "获得产品列表-下拉列表")
    public CommonResult<List<ProductSimpleRespVO>> getProductSimpleList() {
        List<ProductDO> pageResult = productService.getProductList();
        return success(BeanUtils.toBean(pageResult, ProductSimpleRespVO.class));
    }
}