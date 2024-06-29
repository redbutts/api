package com.astuba.project.rpcimpl;

import cn.hutool.core.bean.BeanUtil;
import com.astuba.apirpc.rpc.UserRPC;
import com.astuba.project.mapper.UserMapper;
import com.astuba.project.model.entity.User;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.dubbo.config.annotation.DubboService;

import javax.annotation.Resource;

@DubboService
public class UserRPCImpl implements UserRPC {
    @Resource
    private UserMapper userMapper;

    @Override
    public com.astuba.apirpc.model.User getUser(String accessKey)
    {
        QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
        queryWrapper.eq("accessKey", accessKey);
        User user = userMapper.selectOne(queryWrapper);
        if(user == null)
        {
            return null;
        }
        com.astuba.apirpc.model.User user1 = new com.astuba.apirpc.model.User();
        BeanUtil.copyProperties(user, user1);
        return user1;
    }
}
