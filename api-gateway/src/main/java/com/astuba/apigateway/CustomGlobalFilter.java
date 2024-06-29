package com.astuba.apigateway;

import com.astuba.apiclientsdk.utils.MD5Utils;
import com.astuba.apirpc.rpc.InterfaceInfoRPC;
import com.astuba.apirpc.rpc.UserInterfaceInfoRPC;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 全局过滤
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

//    @DubboReference
//    private InterfaceInfoRPC interfaceInfoRPC;
//
//    @DubboReference
//    private UserInterfaceInfoRPC userInterfaceInfoRPC;

    private static final List<String> WHITE_LIST = Arrays.asList("127.0.0.1");
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //1. 全局过滤器
        ServerHttpRequest request = exchange.getRequest();
        log.info("请求唯一标识" + request.getId());
        log.info("请求方法" + request.getMethod());
        log.info("请求路径" + request.getPath().value());
        log.info("请求参数" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址" + sourceAddress);
        //2. 访问控制-黑白名单
        ServerHttpResponse response = exchange.getResponse();
        if(!WHITE_LIST.contains(sourceAddress))
        {
            handleNoAuth(response);
        }
        //3. 鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String body = headers.getFirst("body");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        System.out.println("accessKey: " + accessKey + "  body: " + body + "  nonce: " + nonce + "  timestamp: " + timestamp + "  sign: " + sign);

        // todo 实际情况是应该到数据库中去查
        if(!"4e6b09cc0cfd9f9702ec9bd3df7a84dc".equals(accessKey))
        {
            handleNoAuth(response);
        }
        // 校验随机数，实际情况应当存缓存
        if(Long.parseLong(nonce) < 10000)
        {
            handleNoAuth(response);
        }
        // 校验时间戳，不超过十分钟
        long timestampLong = Long.parseLong(timestamp);
        long now = System.currentTimeMillis();
        long diffMin = (now - timestampLong) / 1000;
        final long TEN_MINUTES = 60 * 10L;
        if(diffMin > TEN_MINUTES)
        {
            handleNoAuth(response);
        }
        // todo 从数据库中查出此用户的secretKey
        String secretKey = "aadaa9e3666cbfaba85e820efafdbf57";
        // 校验sign
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("accessKey", accessKey);
        hashMap.put("body", body);
        hashMap.put("nonce", nonce);
        hashMap.put("timestamp", timestamp);
        String verifySign = MD5Utils.genSign(hashMap, secretKey);
        if(!verifySign.equals(sign))
        {
            handleNoAuth(response);
        }

        //4. 请求的接口是否存在
        //todo 这里需要调用 api-backend 项目的接口(http请求或RPC)
//        Object interfaceInfo = interfaceInfoRPC.getById(1);
//        if(interfaceInfo == null)
//        {
//            handleInvokeError(response);
//        }

        //5. 请求转发，调用模拟接口
        // todo 这里有问题：chain.filter是个异步方法，它会等下面的步骤都走完之后，才做转发操作
        // todo 解决方法：装饰者设计模式
//        Mono<Void> filter = chain.filter(exchange);

        // 这个就是装饰者模式
        return handleResponseLog(exchange, chain);
//        //6. 响应日志
//        log.info("响应：" + response.getStatusCode());
//
//        //7. 调用成功，接口调用次数+1
//        // todo 调用api-backend 项目的方法 invokeCount
//        if (HttpStatus.OK == response.getStatusCode()){
//
//        }
//         else {
//            //8. 调用失败，返回一个规范的错误
//            return handleInvokeError(response);
//        }
//        return filter;
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response)
    {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response)
    {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }

    /**
     * 处理响应：在调用模拟接口之后
     */
    public Mono<Void> handleResponseLog(ServerWebExchange exchange, GatewayFilterChain chain)
    {
        try {
            // 原始的空响应
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 响应数据的缓存工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if(HttpStatus.OK == statusCode)
            {
                // 装饰
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body)
                    {
                        log.info("body instanceof Flux:{}", (body instanceof Flux));
                        if(body instanceof Flux)
                        {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据，拼接字符串
                            return super.writeWith(fluxBody.map(dataBuffer -> {
                                byte[] content = new byte[dataBuffer.readableByteCount()];
                                dataBuffer.read(content);
                                DataBufferUtils.release(dataBuffer); // 释放内存
                                // 构建日志
                                StringBuffer sb2 = new StringBuffer(200);
                                sb2.append("<--- {} {}  \n");
                                List<Object> resArgs = new ArrayList<>();
                                resArgs.add(originalResponse.getStatusCode());
                                String data = new String(content, StandardCharsets.UTF_8);
                                //6. 响应日志
                                log.info(sb2.toString(), resArgs.toArray(), data);

                                //7. 调用成功，接口调用次数+1
                                // todo 调用api-backend 项目的方法 invokeCount
//                                userInterfaceInfoRPC.invokeCount(1, 2);

                                return bufferFactory().wrap(content);
                                }));
                        } else {
                            //8. 调用失败，返回一个规范的错误
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            // 降级处理返回数据
            return chain.filter(exchange);

        } catch (Exception e)
        {
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }
}