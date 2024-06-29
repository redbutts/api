package com.astuba.project.rpcimpl;

import com.astuba.apirpc.rpc.UserInterfaceInfoRPC;
import com.astuba.project.mapper.UserInterfaceInfoMapper;
import com.astuba.project.model.entity.UserInterfaceInfo;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class UserInterfaceInfoRPCImpl implements UserInterfaceInfoRPC {
    @Resource
    private UserInterfaceInfoMapper userInterfaceInfoMapper;

    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        UserInterfaceInfo info = new UserInterfaceInfo();
        UpdateWrapper<UserInterfaceInfo> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("interfaceInfoId", interfaceInfoId);
        updateWrapper.eq("userId", userId);
        updateWrapper.setSql("totalNum = totalNum+1, leftNum = leftNum-1");
        int update = userInterfaceInfoMapper.update(info, updateWrapper);
        return update > 0;
    }
}
