package com.btlab.devops.module.system.dal.mysql.mail;

import com.btlab.devops.framework.common.pojo.PageResult;
import com.btlab.devops.framework.mybatis.core.mapper.BaseMapperX;
import com.btlab.devops.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.btlab.devops.module.system.controller.admin.mail.vo.account.MailAccountPageReqVO;
import com.btlab.devops.module.system.dal.dataobject.mail.MailAccountDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MailAccountMapper extends BaseMapperX<MailAccountDO> {

    default PageResult<MailAccountDO> selectPage(MailAccountPageReqVO pageReqVO) {
        return selectPage(pageReqVO, new LambdaQueryWrapperX<MailAccountDO>()
                .likeIfPresent(MailAccountDO::getMail, pageReqVO.getMail())
                .likeIfPresent(MailAccountDO::getUsername , pageReqVO.getUsername())
                .orderByDesc(MailAccountDO::getId));
    }

}
