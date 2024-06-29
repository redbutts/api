package com.astuba.project.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.astuba.project.model.dto.interfaceInfo.InterfaceInfoQueryRequest;
import com.astuba.project.model.entity.InterfaceInfo;

/**
* @author astuba
* @description 针对表【interface_info(接口信息)】的数据库操作Service
* @createDate 2024-06-21 14:28:34
*/
public interface InterfaceInfoService extends IService<InterfaceInfo> {

    /**
     * 校验
     *
     * @param interfaceInfo
     * @param add
     */
    void validInterfaceInfo(InterfaceInfo interfaceInfo, boolean add);

    /**
     * 获取查询条件
     *
     * @param interfaceInfoQueryRequest
     * @return
     */
    QueryWrapper<InterfaceInfo> getQueryWrapper(InterfaceInfoQueryRequest interfaceInfoQueryRequest);



//    /**
//     * 从 ES 查询
//     *
//     * @param interfaceInfoQueryRequest
//     * @return
//     */
//    Page<InterfaceInfo> searchFromEs(InterfaceInfoQueryRequest interfaceInfoQueryRequest);

//    /**
//     * 获取帖子封装
//     *
//     * @param interfaceInfo
//     * @param request
//     * @return
//     */
//    InterfaceInfo getInterfaceInfo(InterfaceInfo interfaceInfo, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param interfaceInfoPage
     * @param request
     * @return
     */
//    Page<InterfaceInfo> getInterfaceInfoPage(Page<InterfaceInfo> interfaceInfoPage, HttpServletRequest request);
}
