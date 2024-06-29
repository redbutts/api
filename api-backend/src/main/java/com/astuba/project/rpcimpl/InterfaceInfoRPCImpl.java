package com.astuba.project.rpcimpl;

import cn.hutool.core.bean.BeanUtil;
import com.astuba.apirpc.rpc.InterfaceInfoRPC;
import com.astuba.project.mapper.InterfaceInfoMapper;
import com.astuba.project.model.entity.InterfaceInfo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class InterfaceInfoRPCImpl implements InterfaceInfoRPC {

    @Resource
    private InterfaceInfoMapper interfaceInfoMapper;

    @Override
    public com.astuba.apirpc.model.InterfaceInfo getInterfaceInfo(String url, String method)
    {
        QueryWrapper<InterfaceInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("url", url);
        queryWrapper.eq("method", method);
        InterfaceInfo interfaceInfo = interfaceInfoMapper.selectOne(queryWrapper);
        if(interfaceInfo == null)
        {return null;}
        com.astuba.apirpc.model.InterfaceInfo interfaceInfo1 = new com.astuba.apirpc.model.InterfaceInfo();
        BeanUtil.copyProperties(interfaceInfo, interfaceInfo1);
        return interfaceInfo1;
    }


}
