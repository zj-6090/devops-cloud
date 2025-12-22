package com.bethlabs.module.system.dal.dataobject.databuckups;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 数据备份文件 DO
 *
 * @author mjl
 */
@TableName("ops_data_buckups")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataBuckupsDO {

    /**
     * 自增主键
     */
    @TableId
    private Long id;
    /**
     * 文件大小
     */
    private String fileSize;
    /**
     * 文件名称
     */
    private String fileName;
    /**
     * 文件下载路径
     */
    private String fileUrl;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;


}