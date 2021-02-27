package com.hokage.biz.service.impl;

import com.hokage.biz.enums.ResultCodeEnum;
import com.hokage.biz.enums.SequenceNameEnum;
import com.hokage.biz.form.server.ServerOperateForm;
import com.hokage.biz.response.HokageOptionVO;
import com.hokage.biz.service.HokageSequenceService;
import com.hokage.biz.service.HokageServerGroupService;
import com.hokage.common.ServiceResponse;
import com.hokage.persistence.dao.HokageServerGroupDao;
import com.hokage.persistence.dataobject.HokageServerGroupDO;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.hokage.biz.form.server.ServerOperateForm.ServerGroup;

/**
 * @author linyimin
 * @date 2021/2/27 18:16 pm
 * @email linyimin520812@gmail.com
 * @description server group service implementation
 */
@Service
public class HokageServerGroupServiceImpl extends HokageServiceResponse implements HokageServerGroupService {

    private HokageServerGroupDao serverGroupDao;

    private HokageSequenceService sequenceService;

    @Autowired
    public void setServerGroupDao(HokageServerGroupDao serverGroupDao) {
        this.serverGroupDao = serverGroupDao;
    }

    @Autowired
    public void setSequenceService(HokageSequenceService sequenceService) {
        this.sequenceService = sequenceService;
    }

    @Override
    public ServiceResponse<Boolean> insert(HokageServerGroupDO serverGroupDO) {
        if (serverGroupDao.insert(serverGroupDO) > 0) {
            return success(true);
        }
        return fail(ResultCodeEnum.SERVER_SYSTEM_ERROR.getCode(), ResultCodeEnum.SERVER_SYSTEM_ERROR.getMsg());
    }

    @Override
    public ServiceResponse<List<HokageServerGroupDO>> selectAll() {

        List<HokageServerGroupDO> serverGroupDOList = serverGroupDao.selectAll();
        if (CollectionUtils.isEmpty(serverGroupDOList)) {
            return success(Collections.emptyList());
        }
        return success(serverGroupDOList);
    }

    @Override
    public ServiceResponse<Boolean> update(HokageServerGroupDO serverGroupDO) {

        if (serverGroupDao.update(serverGroupDO) > 0) {
            return success(true);
        }
        return fail(ResultCodeEnum.SERVER_SYSTEM_ERROR.getCode(), ResultCodeEnum.SERVER_SYSTEM_ERROR.getMsg());
    }

    @Override
    public ServiceResponse<HokageServerGroupDO> upsert(HokageServerGroupDO serverGroupDO) {

        if (ObjectUtils.defaultIfNull(serverGroupDO.getId(), 0L) == 0) {
            ServiceResponse<Long> sequenceResult = sequenceService.nextValue(SequenceNameEnum.hokage_server_group.name());
            if (!sequenceResult.getSucceeded()) {
                throw new RuntimeException("server group upsert error. reason: " + sequenceResult.getMsg());
            }
            serverGroupDO.setId(sequenceResult.getData());
            Long result = serverGroupDao.insert(serverGroupDO);
            if (result > 0) {
                return success(serverGroupDO);
            }
            throw new RuntimeException("server group upsert error.");
        }

        Long result = serverGroupDao.update(serverGroupDO);
        if (result > 0) {
            return success(serverGroupDO);
        }
        throw new RuntimeException("server group upsert error.");
    }

    @Override
    public ServiceResponse<List<HokageServerGroupDO>> listByCreatorId(Long id) {
        if (ObjectUtils.defaultIfNull(id, 0L) == 0) {
            return success(Collections.emptyList());
        }
        List<HokageServerGroupDO> serverGroupDOList = serverGroupDao.listByCreatorId(id);

        if (CollectionUtils.isEmpty(serverGroupDOList)) {
            return success(Collections.emptyList());
        }
        return success(serverGroupDOList);
    }

    @Override
    public ServiceResponse<List<HokageOptionVO>> listGroup(Long id) {
        List<HokageServerGroupDO> serverGroupDOList = serverGroupDao.listByCreatorId(id);
        if (CollectionUtils.isEmpty(serverGroupDOList)) {
            return success(Collections.emptyList());
        }
        List<HokageOptionVO> optionVOList = serverGroupDOList.stream()
                .map(serverGroupDO -> new HokageOptionVO(serverGroupDO.getName(), String.valueOf(serverGroupDO.getId())))
                .collect(Collectors.toList());
        return success(optionVOList);
    }

    @Override
    public ServiceResponse<Boolean> addGroup(ServerOperateForm form) {
        Long operatorId = checkNotNull(form.getId(), "operator id can't be null");
        ServiceResponse<Boolean> serviceResponse = new ServiceResponse<>();

        ServerGroup serverGroup = form.getServerGroup();

        List<HokageServerGroupDO> serverGroupDOList = serverGroupDao.listByCreatorId(operatorId, serverGroup.getName());

        // group name has existed, return directly
        if (!CollectionUtils.isEmpty(serverGroupDOList)) {
            return success(true);
        }
        HokageServerGroupDO serverGroupDO = new HokageServerGroupDO();
        ServiceResponse<Long> sequenceResult = sequenceService.nextValue(SequenceNameEnum.hokage_server_group.name());
        if (!sequenceResult.getSucceeded()) {
            throw new RuntimeException("adding group get sequence key error. reason: " + sequenceResult.getMsg());
        }

        serverGroupDO.setId(sequenceResult.getData())
            .setCreatorId(operatorId)
            .setName(serverGroup.getName())
            .setDescription(serverGroup.getDescription());

        return success(serverGroupDao.insert(serverGroupDO) > 0);
    }
}