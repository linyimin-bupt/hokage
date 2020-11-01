package com.banzhe.hokage.biz.controller;

import com.banzhe.hokage.biz.form.server.HokageServerForm;
import com.banzhe.hokage.biz.form.server.ServerOperateForm;
import com.banzhe.hokage.biz.form.server.ServerSearchForm;
import com.banzhe.hokage.biz.response.server.HokageServerVO;
import com.banzhe.hokage.biz.response.user.HokageUserVO;
import com.banzhe.hokage.biz.service.HokageServerService;
import com.banzhe.hokage.common.BaseController;
import com.banzhe.hokage.common.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

/**
 * @author linyimin
 * @date 2020/8/23 1:50 上午
 * @email linyimin520812@gmail.com
 * @description 服务器controller
 */
@RestController
public class ServerController extends BaseController {
    private HokageServerService serverService;

    @Autowired
    public void setServerService(HokageServerService serverService) {
        this.serverService = serverService;
    }

    /**
     * 所有服务器
     */

    // TODO: 列举所有服务器信息
    @RequestMapping(value = "/server/list", method = RequestMethod.GET)
    public ResultVO<List<HokageServerVO>> listServer() {
        return success(Collections.emptyList());
    }

    // TODO: 搜索服务器信息
    @RequestMapping(value = "/server/search", method = RequestMethod.POST)
    public ResultVO<List<HokageUserVO>> searchServer(@RequestBody ServerSearchForm form) {
        return success(Collections.emptyList());
    }

    // TODO: 添加服务器
    @RequestMapping(value = "/server/add", method = RequestMethod.POST)
    public ResultVO<HokageServerVO> addServer(@RequestBody HokageServerForm form) {
        return success(new HokageServerVO());
    }

    // TODO: 删除服务器
    @RequestMapping(value = "/server/delete", method = RequestMethod.POST)
    public ResultVO<Boolean> delServer(@RequestBody ServerOperateForm form) {
        return success(Boolean.TRUE);
    }

    // TODO: 添加管理员
    @RequestMapping(value = "/server/supervisor/add", method = RequestMethod.POST)
    public ResultVO<Boolean> addServerSupervisor(@RequestBody ServerOperateForm form) {
        return success(Boolean.TRUE);
    }

    // TODO: 撤销管理员
    @RequestMapping(value = "/server/supervisor/delete", method = RequestMethod.POST)
    public ResultVO<Boolean> delServerSupervisor(@RequestBody ServerOperateForm form) {
        return success(Boolean.TRUE);
    }
}
