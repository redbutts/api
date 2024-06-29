package com.astuba.project.rpcimpl;

import com.astuba.apirpc.rpc.InterfaceInfoRPC;
import org.apache.dubbo.config.annotation.DubboService;

@DubboService
public class InterfaceInfoRPCImpl implements InterfaceInfoRPC {

    @Override
    public Object getById(long id) {
        return "调用成功："+ id;
    }


}
