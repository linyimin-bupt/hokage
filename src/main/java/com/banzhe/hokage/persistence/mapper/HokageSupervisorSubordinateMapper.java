package com.banzhe.hokage.persistence.mapper;

import com.banzhe.hokage.persistence.dataobject.HokageSupervisorSubordinateDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author linyimin
 * @date 2020/8/22 11:42 am
 * @email linyimin520812@gmail.com
 * @description supervisor and subordinate user relationship mapping table
 */
@Mapper
@Component
public interface HokageSupervisorSubordinateMapper {

    /**
     * insert a new record
     * @param supervisorSubordinateDO
     * @return
     */
    Long insert(HokageSupervisorSubordinateDO supervisorSubordinateDO);

    /**
     * insert a new record
     * @param supervisorSubordinateDOS
     * @return
     */
    Long insertBatch(List<HokageSupervisorSubordinateDO> supervisorSubordinateDO);

    /**
     * update a record
     * @param supervisorSubordinateDO
     * @return
     */
    Long update(HokageSupervisorSubordinateDO supervisorSubordinateDO);

    /**
     * retrieve the relationship mapping information between supervisor and subordinate user based on id
     * @param id
     * @return
     */
    HokageSupervisorSubordinateDO listById(Long id);

    /**
     * list all mapping information
     * @return
     */
    List<HokageSupervisorSubordinateDO> listAll();

    /**
     * retrieve mapping information based on supervisor id
     * @param id
     * @return
     */
    List<HokageSupervisorSubordinateDO> listBySupervisorId(Long id);

    /**
     * retrieve mapping information based on subordinate id
     * @param id
     * @return
     */
    List<HokageSupervisorSubordinateDO> listBySubordinateId(Long id);
}
