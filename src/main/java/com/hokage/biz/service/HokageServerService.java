package com.hokage.biz.service;

import com.hokage.biz.form.server.ServerOperateForm;
import com.hokage.biz.request.server.AllServerQuery;
import com.hokage.biz.request.server.ServerQuery;
import com.hokage.biz.request.server.SubordinateServerQuery;
import com.hokage.biz.request.server.SupervisorServerQuery;
import com.hokage.biz.response.server.HokageServerVO;
import com.hokage.common.ServiceResponse;
import com.hokage.persistence.dataobject.HokageServerDO;

import java.util.List;

/**
 * @author linyimin
 * @date 2020/8/22 11:28 am
 * @email linyimin520812@gmail.com
 * @description server service interface
 */
public interface HokageServerService {

    /**
     * retrieve all server
     * @return server list include the subordinate account
     */
    ServiceResponse<List<HokageServerVO>> selectAll();

    /**
     * retrieve server information based on server ids
     * @param ids server primary id list
     * @return server list which meet the criteria
     */
    ServiceResponse<List<HokageServerDO>> selectByIds(List<Long> ids);


    /**
     * retrieve server information based on server type
     * @param type server type
     * @return server list which meet the criteria
     */
    ServiceResponse<List<HokageServerDO>> selectByType(String type);

    /**
     * retrieve server information based on server group
     * @param group server group
     * @return server list which meet the criteria
     */
    ServiceResponse<List<HokageServerDO>> selectByGroup(String group);

    /**
     * insert or update server info
     * @param serverDO server data object
     * @return
     */
    ServiceResponse<HokageServerDO> save(HokageServerDO serverDO);

    /**
     * delete server
     * @param id server primary id
     * @return delete success return true, otherwise false
     */
    ServiceResponse<Boolean> delete(Long id);

    /**
     * designate servers to a supervisor
     * @param form
     * @return
     */
    ServiceResponse<Boolean> designateSupervisor(ServerOperateForm form);

    /**
     * revoke a supervisor
     * @param form
     * @return
     */
    ServiceResponse<Boolean> revokeSupervisor(ServerOperateForm form);

    /**
     * designate servers to a subordinate
     * @param form
     * @return
     */
    ServiceResponse<Boolean> designateSubordinate(ServerOperateForm form);

    /**
     * revoke a subordinate
     * @param form
     * @return
     */
    ServiceResponse<Boolean> revokeSubordinate(ServerOperateForm form);

    /**
     * apply server
     * @param form
     * @return
     */
    ServiceResponse<Boolean> applyServer(ServerOperateForm form);

    /**
     * list server which have grant to the supervisor
     * @param supervisorId supervisor id
     * @return server list which meet the criteria
     */
    ServiceResponse<List<HokageServerVO>> listSupervisorGrantServer(Long supervisorId);

    /**
     * list server which not grant to the supervisor
     * @param supervisorId supervisor id
     * @return server list which meet the criteria
     */
    ServiceResponse<List<HokageServerVO>> listNotGrantServer(Long supervisorId);

    /**
     * search all server (operator server)
     * @param allServerQuery {@link AllServerQuery}
     * @return server list which meet the criteria
     */
    ServiceResponse<List<HokageServerVO>> searchAllServer(AllServerQuery allServerQuery);

    /**
     * search supervisor server
     * @param query {@link SupervisorServerQuery}
     * @return server list which meet the criteria
     */
    ServiceResponse<List<HokageServerVO>> searchSupervisorServer(SupervisorServerQuery query);

    /**
     * search subordinate server
     * @param query {@link SubordinateServerQuery}
     * @return {@link List<HokageServerVO>}
     */
    ServiceResponse<List<HokageServerVO>> searchSubordinateServer(SubordinateServerQuery query);

    /**
     * serach server
     * @param query {@link ServerQuery}
     * @return {@link List<HokageServerVO>}
     */
    ServiceResponse<List<HokageServerVO>> searchServer(ServerQuery query);
}
