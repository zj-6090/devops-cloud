package com.bethlabs.module.system.dal.dataobject.productdev;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 产品设备接口 DO
 *
 * @author mjl
 */
@TableName("ops_product_dev")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDevDO {

    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 接口名称
     */
    private String interfaceName;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 自定义协议cmd
     */
    private Integer cmd;
    /**
     * io口
     */
    private Integer item;
    /**
     * 产品id
     */
    private Long productId;


}