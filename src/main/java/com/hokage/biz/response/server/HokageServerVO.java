package com.hokage.biz.response.server;

import com.hokage.biz.response.HokageOperation;
import lombok.Data;

import java.util.List;

/**
 * @author linyimin
 * @date 2020/10/28 12:32 am
 * @email linyimin520812@gmail.com
 * @description detail of server
 */
@Data
public class HokageServerVO {
    /**
     * server id
     */
    private Long id;
    /**
     * server hostname
     */
    private String hostname;
    /**
     * server domain
     */
    private String domain;
    /**
     * server labels
     */
    private List<String> labels;
    /**
     * supervisor name
     */
    private List<String> supervisor;
    /**
     * supervisor id
     */
    private List<Long> supervisorId;

    /**
     * subordinate name
     */
    private List<String> subordinate;

    /**
     * subordinate id
     */
    private List<Long> subordinateId;

    /**
     * number of user
     */
    private Integer userNum;
    /**
     * status of server
     */
    private String status;
    /**
     * operations
     */
    private List<HokageOperation> operationList;
}