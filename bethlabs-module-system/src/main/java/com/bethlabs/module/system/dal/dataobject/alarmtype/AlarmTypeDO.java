package com.bethlabs.module.system.dal.dataobject.alarmtype;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 产品的报警类型和设备编号映射表  DO
 *
 * @author mjl
 */
@TableName("ops_alarm_type")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmTypeDO {

    /**
     * 报警级别id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 报警等级Id
     */
    private Integer alarmLevelId;
    /**
     * 设备报警编号（设备上传的报警编号）
     */
    private Long deviceAlarmCode;
    /**
     * 产品编号
     */
    private Long productId;
}