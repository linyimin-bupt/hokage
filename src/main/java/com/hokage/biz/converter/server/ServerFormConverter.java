package com.hokage.biz.converter.server;

import com.hokage.biz.converter.Converter;
import com.hokage.biz.form.server.HokageServerForm;
import com.hokage.persistence.dataobject.HokageServerDO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.stream.Collectors;

/**
 * @author linyimin
 * @date 2021/2/6 20:46
 * @email linyimin520812@gmail.com
 * @description server form converter
 */
@Component
public class ServerFormConverter implements Converter<HokageServerForm, HokageServerDO> {

    @Override
    public HokageServerDO doForward(HokageServerForm hokageServerForm) {
        HokageServerDO serverDO = new HokageServerDO();
        BeanUtils.copyProperties(hokageServerForm, serverDO);
        if (!CollectionUtils.isEmpty(hokageServerForm.getServerGroupList())) {
            String group = String.join(",", hokageServerForm.getServerGroupList());
            serverDO.setServerGroup(group);
        }
        serverDO.setCreatorId(hokageServerForm.getOperatorId());
        return serverDO;
    }

    @Override
    public HokageServerForm doBackward(HokageServerDO serverDO) {
        HokageServerForm form = new HokageServerForm();
        BeanUtils.copyProperties(serverDO, form);
        return form;
    }
}
