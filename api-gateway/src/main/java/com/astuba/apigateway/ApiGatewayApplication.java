package com.astuba.apigateway;

import com.astuba.apirpc.rpc.InterfaceInfoRPC;
import com.astuba.apirpc.rpc.UserInterfaceInfoRPC;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDubbo
public class ApiGatewayApplication {



    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayApplication.class, args);
    }

//    void doConsume()
//    {
//        interfaceInfoRPC.getById(1);
//        userInterfaceInfoRPC.invokeCount(1,1);
//    }

//    @Bean
//    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//        return builder.routes()
//                .route("toBaidu", r -> r.path("/baidu")
//                        .uri("https://www.baidu.com"))
//                .route("toastubaicu", r -> r.path("/astubaicu")
//                        .uri("http://astuba.icu"))
//                .build();
//    }

}
