package com.hokage.persistence.dataobject;

import lombok.Data;

/**
 * @author linyimin
 * @date 2020/7/26 10:18 pm
 * @email linyimin520812@gmail.com
 * @description Manager and user relationship mapping table
 */

@Data
public class HokageSupervisorSubordinateDO extends HokageBaseDO {
    private Long id;
    /**
     * supervisor id
     */
    private Long supervisorId;
    /**
     * subordinate id
     */
    private Long subordinateId;
}
