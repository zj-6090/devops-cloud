package com.bethlabs.module.system.service.databuckups;

import java.util.*;

import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.module.system.controller.admin.databuckups.vo.DataBuckupsPageReqVO;
import com.bethlabs.module.system.controller.admin.databuckups.vo.DataBuckupsSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.databuckups.DataBuckupsDO;
import jakarta.validation.*;


/**
 * 数据备份文件 Service 接口
 *
 * @author mjl
 */
public interface DataBuckupsService {

    /**
     * 创建数据备份文件
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDataBuckups(@Valid DataBuckupsSaveReqVO createReqVO);

    /**
     * 更新数据备份文件
     *
     * @param updateReqVO 更新信息
     */
    void updateDataBuckups(@Valid DataBuckupsSaveReqVO updateReqVO);

    /**
     * 删除数据备份文件
     *
     * @param id 编号
     */
    void deleteDataBuckups(Long id);

    /**
    * 批量删除数据备份文件
    *
    * @param ids 编号
    */
    void deleteDataBuckupsListByIds(List<Long> ids);

    /**
     * 获得数据备份文件
     *
     * @param id 编号
     * @return 数据备份文件
     */
    DataBuckupsDO getDataBuckups(Long id);

    /**
     * 获得数据备份文件分页
     *
     * @param pageReqVO 分页查询
     * @return 数据备份文件分页
     */
    PageResult<DataBuckupsDO> getDataBuckupsPage(DataBuckupsPageReqVO pageReqVO);

    /**
     * 数据库sql文件备份
     */
    void dataBaseBackUp();

}