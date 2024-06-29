package com.astuba.apiinterface.controller;

import com.astuba.apiclientsdk.model.User;
import com.astuba.apiclientsdk.utils.MD5Utils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 名称API
 * @author astuba
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String name, HttpServletRequest request)
    {
        System.out.println(request.getHeader("astuba"));
        return "GET 你的名字是：" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name)
    {
        return "POST 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request)
    {
//        String accessKey = request.getHeader("accessKey");
//        String body = request.getHeader("body");
//        String nonce = request.getHeader("nonce");
//        String timestamp = request.getHeader("timestamp");
//        String sign = request.getHeader("sign");
//        System.out.println("accessKey: " + accessKey + "  body: " + body + "  nonce: " + nonce + "  timestamp: " + timestamp + "  sign: " + sign);
//
//        // 实际情况是应该到数据库中去查
//        if(!"4e6b09cc0cfd9f9702ec9bd3df7a84dc".equals(accessKey))
//        {
//            throw new RuntimeException("无权限");
//        }
//        // 校验随机数，实际情况应当存缓存
//        if(Long.parseLong(nonce) < 10000)
//        {
//            throw new RuntimeException("无权限");
//        }
//        // 校验时间戳，不超过十分钟
//        long timestampLong = Long.parseLong(timestamp);
//        long now = System.currentTimeMillis();
//        long diff = (now - timestampLong) / 1000;
//        if(diff > 600)
//        {
//            throw new RuntimeException("无权限");
//        }
//        // 从数据库中查出此用户的secretKey
//        String secretKey = "aadaa9e3666cbfaba85e820efafdbf57";
//        // 校验sign
//        Map<String, String> hashMap = new HashMap<>();
//        hashMap.put("accessKey", accessKey);
//        hashMap.put("body", body);
//        hashMap.put("nonce", nonce);
//        hashMap.put("timestamp", timestamp);
//        String verifySign = MD5Utils.genSign(hashMap, secretKey);
//        if(!verifySign.equals(sign))
//        {
//            throw new RuntimeException("无权限");
//        }
        return "POST 你的名字是：" + user.getName();
    }
}
