package com.hokage.biz.converter.user;

import com.hokage.biz.converter.server.ConverterTypeEnum;
import com.hokage.biz.converter.server.ServerDOConverter;
import com.hokage.biz.form.user.HokageUserLoginForm;
import com.hokage.biz.form.user.HokageUserRegisterForm;
import com.hokage.biz.response.server.HokageServerVO;
import com.hokage.biz.response.user.HokageUserVO;
import com.hokage.persistence.dao.*;
import com.hokage.persistence.dataobject.HokageSubordinateServerDO;
import com.hokage.persistence.dataobject.HokageSupervisorServerDO;
import com.hokage.persistence.dataobject.HokageUserDO;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * @author linyimin
 * @date 2020/8/30 3:14 pm
 * @email linyimin520812@gmail.com
 * @description conversion between user VO and DO
 */
@Component
public class UserConverter {

    private static HokageSupervisorServerDao supervisorServerDao;
    private static HokageSubordinateServerDao subordinateServerDao;
    private static HokageServerDao hokageServerDao;
    private static HokageUserDao userDao;

    private static final ImmutableMap<ConverterTypeEnum, UserDO2VO> CONVERTER_MAP = ImmutableMap.<ConverterTypeEnum, UserDO2VO>builder()
        .put(ConverterTypeEnum.supervisor, new SupervisorUserDO2VO())
        .put(ConverterTypeEnum.subordinate, new SubordinateUserDO2VO())
        .build();

    @Autowired
    public void setSupervisorServerDao(HokageSupervisorServerDao hokageSupervisorServerDao) {
        UserConverter.supervisorServerDao = hokageSupervisorServerDao;
    }

    @Autowired
    public void setSubordinateServerDao(HokageSubordinateServerDao hokageSubordinateServerDao) {
        UserConverter.subordinateServerDao = hokageSubordinateServerDao;
    }

    @Autowired
    public void setHokageServerDao(HokageServerDao hokageServerDao) {
        UserConverter.hokageServerDao = hokageServerDao;
    }

    @Autowired
    public void setUserDao(HokageUserDao userDao) {
        UserConverter.userDao = userDao;
    }

    public static HokageUserDO registerFormToDO(HokageUserRegisterForm userRegisterForm) {
        HokageUserDO userDO = new HokageUserDO();
        BeanUtils.copyProperties(userRegisterForm, userDO);
        return userDO;
    }

    public static HokageUserRegisterForm DOToRegisterForm(HokageUserDO userDO) {
        HokageUserRegisterForm form = new HokageUserRegisterForm();
        BeanUtils.copyProperties(userDO, form);
        form.setPasswd("");
        return form;
    }

    public static HokageUserDO loginFormToDO(HokageUserLoginForm loginForm) {
        HokageUserDO userDO = new HokageUserDO();
        BeanUtils.copyProperties(loginForm, userDO);
        return userDO;
    }

    public static HokageUserLoginForm DOToLoginForm(HokageUserDO userDO) {
        HokageUserLoginForm loginForm = new HokageUserLoginForm();
        BeanUtils.copyProperties(loginForm, userDO);
        loginForm.setPasswd("");
        return loginForm;
    }

    public static HokageUserVO converter(HokageUserDO hokageUserDO, ConverterTypeEnum type) {
        UserDO2VO user2VO = CONVERTER_MAP.get(type);
        return user2VO.converter(hokageUserDO);
    }


    interface UserDO2VO {
        /**
         * userDO to userVO
         * @param hokageUserDO hokage user data object
         * @return
         */
        HokageUserVO converter(HokageUserDO hokageUserDO);
    }

    private static class SupervisorUserDO2VO implements UserDO2VO {

        @Override
        public HokageUserVO converter(HokageUserDO hokageUserDO) {

            HokageUserVO hokageUserVO = preConverter(hokageUserDO);
            // server information which managed by the supervisor
            List<Long> serverIds = supervisorServerDao.listByServerIds(Arrays.asList(hokageUserDO.getId()))
                    .stream()
                    .map(HokageSupervisorServerDO::getServerId)
                    .collect(Collectors.toList());

            List<HokageServerVO> serverVOList = hokageServerDao.selectByIds(serverIds).stream().map(serverDO -> {

                HokageServerVO serverVO = ServerDOConverter.converter2VO(serverDO, ConverterTypeEnum.supervisor);

                // supervisor info
                serverVO.setSupervisorList(Collections.singletonList(hokageUserDO.getUsername()));
                serverVO.setSupervisorIdList(Collections.singletonList(hokageUserDO.getId()));

                return serverVO;

            }).collect(Collectors.toList());

            List<String> serverLabels = serverVOList.stream()
                    .flatMap(serverVO -> serverVO.getServerGroupList().stream())
                    .distinct()
                    .collect(Collectors.toList());

            hokageUserVO.setServerGroupList(serverLabels);
            hokageUserVO.setServerNum(serverVOList.size());
            hokageUserVO.setServerVOList(serverVOList);
            return hokageUserVO;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    private static class SubordinateUserDO2VO implements UserDO2VO {

        @Override
        public HokageUserVO converter(HokageUserDO hokageUserDO) {

            HokageUserVO hokageUserVO = preConverter(hokageUserDO);

            // server information which managed by the supervisor
            List<Long> serverIds = subordinateServerDao.listByServerIds(
                    Collections.singletonList(hokageUserDO.getId())
            ).stream()
                    .map(HokageSubordinateServerDO::getServerId)
                    .collect(Collectors.toList());

            List<HokageServerVO> serverVOList = hokageServerDao.selectByIds(serverIds).stream()
                    .map(serverDO -> {

                        HokageServerVO serverVO = ServerDOConverter.converter2VO(serverDO, ConverterTypeEnum.supervisor);

                        // supervisor info
                        serverVO.setSubordinateName(Collections.singletonList(hokageUserDO.getUsername()));
                        serverVO.setSubordinateIdList(Collections.singletonList(hokageUserDO.getId()));

                        // number of users of the server
                        List<HokageSubordinateServerDO> subordinateServerDOList = subordinateServerDao.listByServerIds(
                            Collections.singletonList(serverDO.getId())
                        );
                        serverVO.setUserNum(subordinateServerDOList.size());

                        return serverVO;
                    }).collect(Collectors.toList());

            List<String> serverGroup = serverVOList.stream()
                    .filter(serverVO -> !CollectionUtils.isEmpty(serverVO.getServerGroupList()))
                    .flatMap(serverVO -> serverVO.getServerGroupList().stream())
                    .distinct()
                    .collect(Collectors.toList());

            hokageUserVO.setServerGroupList(serverGroup);
            hokageUserVO.setServerNum(serverVOList.size());
            hokageUserVO.setServerVOList(serverVOList);

            // 指定管理员
            HokageUserDO userDO = userDao.querySupervisorBySubordinateId(hokageUserDO.getId());
            if (Objects.nonNull(userDO)) {
                hokageUserVO.setSupervisorId(userDO.getId());
                hokageUserVO.setSupervisorName(userDO.getUsername());
            }

            return hokageUserVO;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    private static HokageUserVO preConverter(HokageUserDO hokageUserDO) {
        HokageUserVO hokageUserVO = new HokageUserVO();

        // subordinate info
        BeanUtils.copyProperties(hokageUserDO, hokageUserVO);

        return hokageUserVO;
    }
}
