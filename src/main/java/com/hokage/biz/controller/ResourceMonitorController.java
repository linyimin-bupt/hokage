package com.hokage.biz.controller;

import com.hokage.biz.form.monitor.MonitorOperateForm;
import com.hokage.biz.request.command.MonitorParam;
import com.hokage.biz.response.resource.general.BasicInfoVO;
import com.hokage.biz.response.resource.metric.MetricVO;
import com.hokage.biz.response.resource.network.NetworkInfoVO;
import com.hokage.biz.response.resource.system.SystemInfoVO;
import com.hokage.biz.service.HokageServerMetricService;
import com.hokage.biz.service.impl.HokageMonitorService;
import com.hokage.common.BaseController;
import com.hokage.common.ResultVO;
import com.hokage.common.ServiceResponse;
import com.hokage.ssh.command.handler.MonitorCommandHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author linyimin
 * @date 2020/8/23 01:52
 * @email linyimin520812@gmail.com
 * @description
 */
@RestController
public class ResourceMonitorController extends BaseController {
    private HokageMonitorService monitorService;
    private MonitorCommandHandler<MonitorParam> commandHandler;
    private HokageServerMetricService metricService;

    @Autowired
    public void setMonitorService(HokageMonitorService monitorService) {
        this.monitorService = monitorService;
    }

    @Autowired
    public void setCommandHandler(MonitorCommandHandler<MonitorParam> commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Autowired
    public void setMetricService(HokageServerMetricService metricService) {
        this.metricService = metricService;
    }

    @RequestMapping(value = "/server/monitor/basic", method = RequestMethod.POST)
    public ResultVO<BasicInfoVO> acquireBasicInfo(@RequestBody MonitorOperateForm form) {
        String serverKey = form.buildKey();
        ServiceResponse<BasicInfoVO> result = monitorService.acquireBasic(serverKey);

        return response(result);
    }

    @RequestMapping(value = "/server/monitor/system", method = RequestMethod.POST)
    public ResultVO<SystemInfoVO> acquireSystemInfo(@RequestBody MonitorOperateForm form) {
        String serverKey = form.buildKey();
        ServiceResponse<SystemInfoVO> result = monitorService.acquireSystem(serverKey);

        return response(result);
    }

    @RequestMapping(value = "/server/monitor/process/kill", method = RequestMethod.POST)
    public ResultVO<Boolean> killProcess(@RequestBody MonitorOperateForm form) {
        String serverKey = form.buildKey();
        MonitorParam param = new MonitorParam().setPid(form.getPid());
        ServiceResponse<Boolean> killResult = monitorService.execute(serverKey, param, commandHandler.killProcessHandler);
        return response(killResult);
    }

    @RequestMapping(value = "/server/monitor/network/basic", method = RequestMethod.POST)
    public ResultVO<NetworkInfoVO> acquireNetworkBasicInfo(@RequestBody MonitorOperateForm form) {
        String serverKey = form.buildKey();
        ServiceResponse<NetworkInfoVO> networkInfoResult = monitorService.acquireNetwork(serverKey);
        return response(networkInfoResult);
    }

    @RequestMapping(value = "/server/monitor/metric", method = RequestMethod.POST)
    public ResultVO<MetricVO> acquireMetric(@RequestBody MonitorOperateForm form) {
        ServiceResponse<MetricVO> response = metricService.acquireMetric(form.getIp(), form.getStart(), form.getEnd());
        return response(response);
    }
}
