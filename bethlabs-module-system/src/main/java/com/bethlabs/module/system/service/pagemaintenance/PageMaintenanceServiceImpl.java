package com.bethlabs.module.system.service.pagemaintenance;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.bethlabs.framework.common.exception.ServiceException;
import com.bethlabs.framework.common.pojo.PageResult;
import com.bethlabs.framework.common.util.object.BeanUtils;
import com.bethlabs.module.infra.service.minio.MinioFileService;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenanceEnableReqVO;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenancePageReqVO;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenanceRespVO;
import com.bethlabs.module.system.controller.admin.pagemaintenance.vo.PageMaintenanceSaveReqVO;
import com.bethlabs.module.system.dal.dataobject.pagemaintenance.PageMaintenanceDO;
import com.bethlabs.module.system.dal.mysql.pagemaintenance.PageMaintenanceMapper;
import com.bethlabs.module.system.enums.common.EnableEnum;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static com.bethlabs.module.system.enums.ErrorCodeConstants.PAGE_MAINTENANCE_ENABLE_FAIL;
import static com.bethlabs.module.system.enums.ErrorCodeConstants.PAGE_MAINTENANCE_NOT_EXISTS;


/**
 * 页面维护 Service 实现类
 *
 * @author mjl
 */
@Service
@Validated
public class PageMaintenanceServiceImpl implements PageMaintenanceService {

    @Resource
    private PageMaintenanceMapper pageMaintenanceMapper;

    @Autowired
    private MinioFileService minioFileService;

    @Value("${maintenance.terminal.path}")
    private String terminalPath;

    @Value("${maintenance.monitor.path}")
    private String monitorPath;

    @Override
    public Long createPageMaintenance(PageMaintenanceSaveReqVO createReqVO) {
        // 插入
        PageMaintenanceDO pageMaintenance = BeanUtils.toBean(createReqVO, PageMaintenanceDO.class);
        //将对应的上传到minio中的终端参数文件和实时监看文件，下载minio文件进行解压到对应的目录中,并且重新设置终端参数文件和实时监看文件的目标目录文件
        decompressFile(pageMaintenance);
        pageMaintenanceMapper.insert(pageMaintenance);
        // 返回
        return pageMaintenance.getId();
    }


    @Override
    public void updatePageMaintenance(PageMaintenanceSaveReqVO updateReqVO) {
        PageMaintenanceDO pageMaintenanceDB = pageMaintenanceMapper.selectById(updateReqVO.getId());
        // 校验存在
        validatePageMaintenanceExists(pageMaintenanceDB);
        // 更新
        PageMaintenanceDO updateObj = BeanUtils.toBean(updateReqVO, PageMaintenanceDO.class);
        //将修改后的重新上传的tar包进行修改
        if (!updateReqVO.getTerminalDownUrl().equals(pageMaintenanceDB.getTerminalDownUrl())){
            //只有修改后才进行重新解压和上传文件，否则不进行操作
            decompressTerminalFile(updateObj);
        }
        if (!updateReqVO.getMonitorDownUrl().equals(pageMaintenanceDB.getMonitorDownUrl())){
            //只有修改后才进行重新解压和上传文件，否则不进行操作
            decompressMonitorFile(updateObj);
        }
        pageMaintenanceMapper.updateById(updateObj);
    }

    @Override
    public void enablePageMaintenance(PageMaintenanceEnableReqVO enableReqVO) {
        //校验是否存在相同系列和版本已启用的页面维护，如果有则不允许启用
        if (EnableEnum.ENABLE.getKey().equals(enableReqVO.getIsEnable()))
            validateProVerIsEnable(enableReqVO.getId());
        // 更新
        PageMaintenanceDO updateObj = BeanUtils.toBean(enableReqVO, PageMaintenanceDO.class);
        pageMaintenanceMapper.updateById(updateObj);
    }

    @Override
    public void deletePageMaintenance(Long id) {
        // 校验存在
        PageMaintenanceDO pageMaintenanceDO = pageMaintenanceMapper.selectById(id);
        validatePageMaintenanceExists(pageMaintenanceDO);
        // 删除
        pageMaintenanceMapper.deleteById(id);
    }

    @Override
    public void deletePageMaintenanceListByIds(List<Long> ids) {
        // 删除
        pageMaintenanceMapper.deleteByIds(ids);
    }


    private void validatePageMaintenanceExists(PageMaintenanceDO pageMaintenanceDB) {
        if (pageMaintenanceDB == null) {
            throw new ServiceException(PAGE_MAINTENANCE_NOT_EXISTS);
        }
    }

    @Override
    public PageMaintenanceDO getPageMaintenance(Long id) {
        return pageMaintenanceMapper.selectById(id);
    }

    @Override
    public PageResult<PageMaintenanceRespVO> getPageMaintenancePage(PageMaintenancePageReqVO pageReqVO) {
        IPage<PageMaintenanceRespVO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        page = pageMaintenanceMapper.selectPage(page, pageReqVO);
        return new PageResult<>(page.getRecords(), page.getTotal());
    }

    @Override
    public PageMaintenanceDO getPageMaintenanceUrl(Long productId, String versionNumber) {
        LambdaQueryWrapper<PageMaintenanceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PageMaintenanceDO::getProductId, productId)
                .eq(PageMaintenanceDO::getVersionNumber, versionNumber)
                .eq(PageMaintenanceDO::getIsEnable, EnableEnum.ENABLE.getKey());
        return pageMaintenanceMapper.selectOne(queryWrapper);
    }


    private void decompressFile(PageMaintenanceDO pageMaintenance) {
        //1.将对应的上传到minio中的终端参数文件解压到指定目录文件
        //生成随机目录，保证唯一性，将文件解压到该目录下
        decompressTerminalFile(pageMaintenance);
        //2.将对应的上传到minio中的实时监看文件解压到指定目录文件
        decompressMonitorFile(pageMaintenance);
    }

    private void decompressMonitorFile(PageMaintenanceDO pageMaintenance) {
        String monitorRandom = UUID.randomUUID().toString().replace("-", "");
        String monitorVisitUrl = Paths.get(monitorPath, monitorRandom).toString();
        minioFileService.unTarToLocal(pageMaintenance.getMonitorDownUrl(), monitorVisitUrl);
        pageMaintenance.setMonitorVisitUrl(monitorVisitUrl);
    }

    private void decompressTerminalFile(PageMaintenanceDO pageMaintenance) {
        String terminalRandom = UUID.randomUUID().toString().replace("-", "");
        //本地windows
        String terminalVisitUrl = Paths.get(terminalPath, terminalRandom).toString();
        minioFileService.unTarToLocal(pageMaintenance.getTerminalDownUrl(), terminalVisitUrl);
        pageMaintenance.setTerminalVisitUrl(terminalVisitUrl);
    }


    private void validateProVerIsEnable(Long id) {
        PageMaintenanceDO pageMaintenanceDO = pageMaintenanceMapper.selectById(id);
        if (pageMaintenanceDO == null) {
            throw new ServiceException(PAGE_MAINTENANCE_NOT_EXISTS);
        }
        LambdaQueryWrapper<PageMaintenanceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(PageMaintenanceDO::getProductId, pageMaintenanceDO.getProductId())
                .eq(PageMaintenanceDO::getVersionNumber, pageMaintenanceDO.getVersionNumber())
                .eq(PageMaintenanceDO::getIsEnable, EnableEnum.ENABLE.getKey());
        PageMaintenanceDO pageMaintenanceDB = pageMaintenanceMapper.selectOne(queryWrapper);
        if (pageMaintenanceDB != null) {
            throw new ServiceException(PAGE_MAINTENANCE_ENABLE_FAIL);
        }
    }
}