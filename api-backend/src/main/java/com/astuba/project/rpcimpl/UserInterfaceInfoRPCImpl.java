package com.astuba.project.rpcimpl;

import com.astuba.apirpc.rpc.UserInterfaceInfoRPC;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class UserInterfaceInfoRPCImpl implements UserInterfaceInfoRPC {


    @Override
    public boolean invokeCount(long interfaceInfoId, long userId) {
        System.out.println("interfaceInfoId:" + interfaceInfoId);
        System.out.println("userId:" + userId);
        return false;
    }
}
